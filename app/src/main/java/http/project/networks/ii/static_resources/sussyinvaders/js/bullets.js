//Bullets JS
//Path: js\bullets.js
//Space invaders bullets.js
class Bullet {
    constructor(x, y, mode) {
        this.x = x;
        this.y = y;
        this.width = 3;
        this.height = 10;
        this.mode = mode;
        this.speed = 400;
    }
    render() {
        ctx.fillStyle = "white";
        ctx.fillRect(this.x, this.y, this.width, this.height);
    }
    update(dt, speedgame) {
        if (this.mode) this.y -= this.speed * dt * speedgame * 2;
        else this.y += this.speed * dt * speedgame;
    }
}

