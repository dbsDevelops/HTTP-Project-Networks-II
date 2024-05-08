class Saucer {
	constructor() {
        this.x = 0;
        this.y = 0;
        this.mode = 0;
        this.score = 0;
        this.width = 64;
        this.height = 32;
        this.active = false;
        this.speed = 250;
        this.amongus = false;
        this.image = new Image();
        this.image.src = "images/Saucer.png";
        this.image2 = new Image();
        this.image2.src = "images/AmoSaucer.png";
        this.image.onload = () => {
        };
        this.image2.onload = () => {
        };
    }

    possibleRequestChangeSkin() {
        if(getRandomIntInclusive(1,2) == 2) {
            console.log("amogus");
            this.amongus = true;
        }
        else{
            console.log("normal");
            this.amongus = false;
        }
    }

    render() {
        if (this.active && !this.amongus) {
            ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
        }
        else if (this.active && this.amongus) {
            ctx.drawImage(this.image2, this.x, this.y, this.width, this.height);
        }
    }

    update(dt, speedgame) {
        if (this.active)
        this.move(dt, speedgame);
    }

    move(dt, speedgame) {
        if (this.mode == 0) {
            this.x -= this.speed * dt * speedgame;
            if (this.x < -this.width ) {
                this.active = false;
                          
            }        
        }
        else {
            this.x += this.speed * dt * speedgame;
            if (this.x > canvas.width + this.width) {
                this.active = false;
            }
        }
    }

    setParameters (x, y, mode, score) {
        this.x = x;
        this.y = y;
        this.mode = mode;
        this.score = score;
        this.active = true;
    }

    SaucerUpdateAndInstantiate(dt, speedgame, saucerspawn) {
        if (!saucerspawn) {
            if(this.amongus) sndAmongUsSaucer.play();
            else sndSaucer.play();
            saucerspawned = true;
            var timer = getRandomIntInclusive(10000, 15000);
            setTimeout(() => {
                this.possibleRequestChangeSkin();
                if (getRandomBinary() == 0){
                    this.setParameters(canvas.width, this.height + 10, 0, getRandomIntInclusive(50, 500));
                }
                else{
                    this.setParameters(-this.width, this.height + 10, 1, getRandomIntInclusive(50, 500));
                }

                

            }, timer);

            setTimeout(() => {
                saucerspawned = false;
            }, timer);
        }
        if (saucerspawn && this.active == true) {
            this.update(dt, speedgame);
        }
    };
}

class SaucerExplosion {
	constructor(x, y) {
		this.x = x;
		this.y = y;
		this.width = 64;
		this.height = 32;

		this.image = new Image();
		this.image.src = "./images/Explosion.png";

		this.time = 0.5;
		this.active = true;


		setTimeout(function () {
			this.active = false;
		}
			.bind(this), 50);
	}

	render() {
		ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
	}
}