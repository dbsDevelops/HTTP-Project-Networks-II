class Barometer{
        constructor(posX, posY){
        this.posX = posX;
        this.posY = posY;
        this.width = 1490/12;
        this.height = 158/12;
        this.limit = 30;
        this.image = new Image();
        this.image.src = "images/interface/Needle.png";
        this.baroBody = new BaroBody(this.posX - 1920/24 + this.width/2, this.posY-1344/24 + this.height/2 - 25);
    }

    render(){
        this.baroBody.render();
        ctx.save();
        //rotate the image from the center
        ctx.translate(this.posX + this.width / 2, this.posY + this.height / 2);
        ctx.rotate(this.angle);
        ctx.translate(-(this.posX + this.width / 2), -(this.posY + this.height / 2));
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
        ctx.restore();
    }

    update(dt, CrewsActive){
        //rotate the needle based on the quantity of crew members
        this.angle = (CrewsActive/this.limit) * Math.PI/2;

        //limit the rotation
        if(this.angle > Math.PI){
            this.angle = Math.PI;
        }
        if(this.angle < 0){
            this.angle = 0;
        }

        if (this.angle < Math.PI/2){
            impostor.forceDamage = 1;
        }

        if (this.angle > Math.PI/2){
            impostor.forceDamage = 2;
        }

        if (this.angle > Math.PI/2 + Math.PI/4){
            impostor.forceDamage = 3;
        }

        if (this.angle > Math.PI){
            impostor.forceDamage = 4;
        }
    }
};

class BaroBody {
    constructor(posX, posY){
        this.posX = posX;
        this.posY = posY;
        this.width = 1920/12;
        this.height = 1344/12;
        this.image = new Image();
        this.image.src = "images/interface/Counter.png";
    }

    render(){
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}