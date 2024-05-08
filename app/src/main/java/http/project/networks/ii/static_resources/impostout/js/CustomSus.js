class CustomSus {
    constructor() {
        this.posX = 60;
        this.posY = 50;
        this.width = 250/5;
        this.height = 300/5;
        this.color = "red";
        this.image = new Image();
        this.image.src = "./images/" + this.color + "/Body.png";
        this.costume = new CustomCostume(this.posX, this.posY);
        this.hat = new CustomHat(this.posX, this.posY);
        this.pet = new CustomPet(this.posX, this.posY);

    }

    render() {
        if(!this.hat.inFront)
        this.hat.render();
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
        this.costume.render();
        if(this.hat.inFront)
        this.hat.render();

        this.pet.render();
    }

};

function ChangeHat(hat){
    customSus.hat.hat = hat;
    customSus.hat.image.src = "./images/miniCustom/" + customSus.hat.hat + ".png";
}

function ChangeCostume(costume){
    customSus.costume.costume = costume;
    customSus.costume.image.src = "./images/miniCustom/" + customSus.costume.costume + ".png";
}

function ChangePet(pet){
    customSus.pet.pet = pet;
    customSus.pet.image.src = "./images/miniCustom/" + customSus.pet.pet + ".png";
}


class CustomCostume {
    constructor(posX, posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = 250/5;
        this.height = 300/5;
        this.image = new Image();
        this.costume = "noneCostume";
        
    }

    render() {
        this.image.src = "./images/miniCustom/" + this.costume + ".png";
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
};

class CustomHat {
    constructor(posX, posY) {
        this.posX = posX - 15;
        this.posY = posY-45;
        this.width = 120/1.5;
        this.height = 120/1.5;
        this.image = new Image();
        this.hat = "noneHat";
        this.inFront = true;
    }

    render() {
        this.image.src = "./images/miniCustom/" + this.hat + ".png";
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
};

class CustomPet {
    constructor(posX, posY) {
        this.posX = posX - 40;
        this.posY = posY+15 ;
        this.width = 120/3;
        this.height = 141/3;
        this.image = new Image();
        this.pet = "nonePet";
    }

    render() {
        this.image.src = "./images/miniCustom/" + this.pet + ".png";
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}
