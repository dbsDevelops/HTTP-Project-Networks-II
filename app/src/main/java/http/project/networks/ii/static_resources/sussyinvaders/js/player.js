//Player JS

//Path: js\player.js
//Space invaders player.js
class Player {
	constructor(x, y, scale) {
		this.x = x;
		this.y = y;
		this.canshoot = true;
		this.speed = 500;
		this.fireSpeed = 500;
		this.lives = 3;
		this.image = new Image();
		this.image.src = "./images/Player.png";
		this.image.onload = () => {
			this.width = this.image.width * scale;
			this.height = this.image.height * scale;
		};

		this.imageDead = new Image();
		this.imageDead.src = "./images/PlayerDead.png";
		this.imageDead.onload = () => {
			this.width = this.imageDead.width * scale;
			this.height = this.imageDead.height * scale;
		};
		this.score = 0;
	}

	render() {

		if (gameOver && this.imageDead) {
			ctx.drawImage(this.imageDead, this.x, this.y, this.width, this.height);
		}
		else if (this.image) {
			ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
		}
	}

	update(dt, keysDown, speedgame) {

		if(isKonamiCode){
			this.fireSpeed = 50;
		}

		//movement
		if (37 in keysDown || 65 in keysDown) {
			this.x -= this.speed * dt * speedgame;
			if (this.x < 0) {
				this.x = 0;
			}
		}
		if (39 in keysDown || 68 in keysDown) {
			this.x += this.speed * dt * speedgame;
			if (this.x + this.width > canvas.width) {
				this.x = canvas.width - this.width;
			}
		}
		//Shooting
		if (32 in keysDown && this.canshoot) {
			this.canshoot = false;
			setTimeout(() => {
				this.canshoot = true;
			}, this.fireSpeed / speedgame);
			bullets.push(new Bullet(this.x + this.width / 2, this.y, true));
			sndShoot.play();
		}
	}
}
