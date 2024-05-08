class Vent {
    constructor( posX, posY) {
        this.width = 165/4;
        this.height = 211/4;
        this.posX = posX;
        this.posY = posY;
        this.ventOpen = false;
        this.going = true;
        this.image = new Image();
        this.image.src = "images/vent/ventClosed.png";
        setTimeout(() => {
            var timerOuter = getRandomInt(2000, 5000);
            exclamations.push(new Exclamation(this.posX, this.posY, timerOuter));
            setTimeout(() => {
                switch (getRandomInt(0, 3)) {
                    case 0:
                        this.image.src = "images/vent/ventBLACK.png";
                        break;
                    case 1:
                        this.image.src = "images/vent/ventWHITE.png";
                        break;
                    case 2:
                        this.image.src = "images/vent/ventRED.png";
                        break;
                }
                this.ventOpen = true;
            }   , timerOuter);
        }   , getRandomInt(30000, 90000));
    }

    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }

    update(dt) {
    }

    MouseOverVent(){
        return (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height);
    }

    OverVent(){
        
    }

    ClickingVent(x, y){
        if(x > this.posX  && x < this.posX + this.width &&
         y > this.posY && y < this.posY + this.height && this.ventOpen){
            this.VentReset();
        }
    };

    VentReset(){
        if(this.ventOpen){
            this.ventOpen = false;
            this.image.src = "images/vent/ventClosed.png";
            setTimeout(() => {
                var timerOuter = getRandomInt(2000, 5000);
                exclamations.push(new Exclamation(this.posX, this.posY, timerOuter));
                setTimeout(() => {
                    switch (getRandomInt(0, 3)) {
                        case 0:
                            this.image.src = "images/vent/ventBLACK.png";
                            break;
                        case 1:
                            this.image.src = "images/vent/ventWHITE.png";
                            break;
                        case 2:
                            this.image.src = "images/vent/ventRED.png";
                            break;
                    }
                    this.ventOpen = true;
                }   , timerOuter);
            }   , getRandomInt(30000, 90000));
        }
    }

};

class Exclamation {
    constructor(posX, posY, timerOuter) {
        this.width = 20;
        this.height = 30;
        this.posX = posX+10;
        this.posY = posY;
        this.going = true;
        this.active = true;
        this.image = new Image();
        this.image.src = "images/interface/Exclamation.png";
        setTimeout(() => {
            clearInterval(interval);
            this.active = false;
        }, timerOuter);

        var interval = setInterval(() => {
            if (this.width < 35 && this.going){
                this.width += 1;
                this.height += 1;

                this.posX -= 0.5;
                this.posY -= 1;
            }
            else if (this.width >25){
                this.going = false;
                this.width -= 1;
                this.height -= 1;

                this.posX += 0.5;
                this.posY += 1;
            }
            else{
                this.going = true;
            }
            
        }, 10);
    }

    

    render(){
        if(this.active)
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}