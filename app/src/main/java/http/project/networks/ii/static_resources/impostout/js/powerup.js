class PowerUp {
    constructor(posX, posY, width, height, sprite) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = new Image();
        this.image.src = sprite;
    }
    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}

var doubleAuto = false;

class DoubleAutoPowerUp extends PowerUp {
    constructor(posX, posY, width, height, sprite) {
        super(posX, posY, width, height, sprite);
        this.active = false;

        setInterval(() => {
            if (this.active == false) {
                this.active = true;
                this.posX = getRandomInt(100, canvas.width - 100);
            }
        }, getRandomInt(60000, 120000));
    }
    render() {
        if (this.active)
            super.render();
    }

    update(dt) {
        if(this.posY > canvas.height) this.active = false; 

        if (this.active) {

            //move down making a sinusoidal movement
            this.posX += 100 * Math.sin(this.posY / 10) * dt;
            this.posY += 20 * dt;

            //Check if pressed
            this.OverPowerAction();
        }
        else {
            this.posX = -this.width;
            this.posY = -this.height;
        }
        
        
    }

    MouseOverButton() {
        return (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height);
    }

    OverPowerAction() {
        if (this.MouseOverButton() && this.active && isClick ) {
            this.active = false;
            doubleAuto = true;
            setTimeout(() => {
                doubleAuto = false;
            }, 10000);
        }
    }
}