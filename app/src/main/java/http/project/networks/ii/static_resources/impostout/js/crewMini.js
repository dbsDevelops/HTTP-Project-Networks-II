

class CrewMiniMedic { //canvas.width - spaceFromBorder - 364/1.7, 140
    constructor(color) {
        this.width = 40;
        this.height = 40;
        this.Taskscore = 50;
        this.posX =             canvas.width - 160 - 364/1.7;
        this.posY = 100         + 140;
        this.going = true;
        this.image = new Image();
        this.image.src = "./images/" + color + "/step/step00.png";
        this.imageIterator = 0;
        this.timer = false;
        this.directionchange = false;
        this.color = color;
        
        this.intervalstep = setInterval(() => {
            if (this.imageIterator < 10) {
                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else if (this.imageIterator <= 11) {
                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else {
                this.imageIterator = 1;
                this.image.src = "./images/" + this.color + "/step/step00.png";
            }
        } , 100);
        
    }

    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }

    update(dt) {
        if (this.going){
            if(this.posX < canvas.width + 10 - 364/1.7)
            this.posX += 1;
            else {
                if (!this.timer){
                    this.timer = true;
                    clearInterval(this.intervalstep);
                    this.image.src = "./images/" + this.color + "/Body.png";
                    this.width = 30;
                    this.height = 30;
                    this.posX += 5;
                    this.posY += 5;
                    setTimeout(() => {
                        this.posX-= 5;
                        this.posY-= 5;
                        this.width = 40;
                        this.height = 40;
                        this.imageIterator = 12; 
                        this.intervalstep = setInterval(() => {
                            if (this.imageIterator <= 23) {
                                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else {
                                this.imageIterator = 13;
                                this.image.src = "./images/" + this.color + "/step/step12.png";
                            }
                        } , 100);
                        this.timer = false;
                        score += this.Taskscore;
                        this.going = false;
                    }, 1000);
                }
            }
        }
        else{
            if(this.posX > canvas.width - 160 - 364/1.7)
            this.posX -= 1;
            else{
                
                clearInterval(this.intervalstep);
                 //Erase this from the array
                 this.posX =             canvas.width - 160 - 364/1.7;
                 this.posY = 100         + 140;
                 this.going = true;
                 this.timer = false;
                 this.imageIterator = 0;
                 this.directionchange = false;
                 this.image.src = "./images/" + this.color + "/step/step00.png";

                 this.intervalstep = setInterval(() => {
                    if (this.imageIterator < 10) {
                        this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else if (this.imageIterator <= 11) {
                        this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else {
                        this.imageIterator = 1;
                        this.image.src = "./images/" + this.color + "/step/step00.png";
                    }
                } , 100);
            }
        }
        
    }
}

class CrewMiniShields {  //canvas.width - spaceFromBorder - 437/2, canvas.height - 180 - 477/2
    constructor(color) {
        this.width = 40;
        this.height = 40;
        this.Taskscore = 100;
        this.posX =             canvas.width - 160 - 437/2;
        this.posY = 90 +       canvas.height - 180 - 477/2;
        this.going = true;
        this.image = new Image();
        this.image.src = "./images/" + color + "/step/step00.png";
        this.imageIterator = 0;
        this.timer = false;
        this.directionchange = true;
        this.color = color;
        
        this.intervalstep = setInterval(() => {
            if (this.imageIterator < 10) {
                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else if (this.imageIterator <= 11) {
                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else {
                this.imageIterator = 1;
                this.image.src = "./images/" + this.color + "/step/step00.png";
            }
        } , 100);
    }

    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
    update(dt) {
        if (this.going){
        if(this.posX < canvas.width - 40 - 437/2  && this.posY == canvas.height - 90 - 477/2)
        this.posX += 50*dt;
        else if(this.posY < canvas.height + 0 - 477/2)
        this.posY += 50*dt;
        else if (this.posX > canvas.width -120 - 437/2){
            this.posX -= 50*dt;
            if (this.directionchange){
                this.directionchange = false;
                this.imageIterator = 12;
                clearInterval(this.intervalstep);
                this.intervalstep = setInterval(() => {
                    if (this.imageIterator <= 23) {
                        this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else {
                        this.imageIterator = 13;
                        this.image.src = "./images/" + this.color + "/step/step12.png";
                    }
                } , 100);
            }
        }
        
        else {
            if (!this.timer){
                this.timer = true;
                clearInterval(this.intervalstep);
                    this.image.src = "./images/" + this.color + "/Body.png";
                    this.width = 30;
                    this.height = 30;
                    this.posX += 5;
                    this.posY += 5;
                    setTimeout(() => {
                        this.posX-= 5;
                        this.posY-= 5;
                        this.width = 40;
                        this.height = 40;
                        this.imageIterator = 12; 
                        this.intervalstep = setInterval(() => {
                            if (this.imageIterator < 10) {
                                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else if (this.imageIterator <= 11) {
                                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else {
                                this.imageIterator = 1;
                                this.image.src = "./images/" + this.color + "/step/step00.png";
                            }
                        } , 100);
                        this.timer = false;
                        score += this.Taskscore;
                        this.going = false;
                    }, 1000);
            }
            
        }
        }
        else{
            
        //inversed path
        if (this.posX < canvas.width - 40 - 437/2 && this.posY >= canvas.height + 0 - 477/2){
            this.posX += 50*dt;
            this.directionchange = true;
        }
        
        else if (this.posY > canvas.height - 90 - 477/2)
        this.posY -= 50*dt;
        else if (this.posX > canvas.width -160 - 437/2){
            this.posX -= 50*dt;
            if (this.directionchange){
                this.directionchange = false;
                this.imageIterator = 12;
                clearInterval(this.intervalstep);
                this.intervalstep = setInterval(() => {
                    if (this.imageIterator <= 23) {
                        this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else {
                        this.imageIterator = 13;
                        this.image.src = "./images/" + this.color + "/step/step12.png";
                    }
                } , 100);
            }
        }

        
        else{
            //Erase this from the array
            clearInterval(this.intervalstep);

            this.posX =             canvas.width - 160 - 437/2;
            this.posY = 90 +       canvas.height - 180 - 477/2;
            this.going = true;
            this.image.src = "./images/" + this.color + "/step/step00.png";
            this.imageIterator = 0;
            this.timer = false;
            this.directionchange = true;
            
            this.intervalstep = setInterval(() => {
                if (this.imageIterator < 10) {
                    this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                    this.imageIterator += 1;
                }
                else if (this.imageIterator <= 11) {
                    this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                    this.imageIterator += 1;
                }
                else {
                    this.imageIterator = 1;
                    this.image.src = "./images/" + this.color + "/step/step00.png";
                }
            } , 100);           
            
        }
        
        }
    }
}

class CrewMiniElectro { //spaceFromBorder, 150
    constructor(color) {
        this.width = 40;
        this.height = 40;
        this.Taskscore = 75;
        this.posX = 160 +       160;
        this.posY = 150 +       150;
        this.going = true;
        this.image = new Image();
        this.image.src = "./images/" + color + "/step/step00.png";
        this.timer = false;   
        this.imageIterator = 12;
        this.timer = false;
        this.directionchange = false;
        this.color = color;
        
        this.intervalstep = setInterval(() => {
            if (this.imageIterator <= 23) {
                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else {
                this.imageIterator = 13;
                this.image.src = "./images/" + this.color + "/step/step12.png";
            }
        } , 100);
    }

    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
        }

    update(dt) {
         if (this.going){
             if(this.posY > 240)
             this.posY -= 50 * dt;
             else if(this.posX > 165){
                this.posX -= 50 * dt;
                this.posY -= 20 * dt;
             }
             else {
                if (!this.timer){
                    this.timer = true;
                    clearInterval(this.intervalstep);
                    this.image.src = "./images/" + this.color + "/Body.png";
                    this.width = 30;
                    this.height = 30;
                    this.posX += 5;
                    this.posY += 5;
                    setTimeout(() => {
                        this.posX-= 5;
                        this.posY-= 5;
                        this.width = 40;
                        this.height = 40;
                        this.imageIterator = 12; 
                        this.intervalstep = setInterval(() => {
                            if (this.imageIterator < 10) {
                                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else if (this.imageIterator <= 11) {
                                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else {
                                this.imageIterator = 1;
                                this.image.src = "./images/" + this.color + "/step/step00.png";
                            }
                        } , 100);
                        this.timer = false;
                        score += this.Taskscore;
                        this.going = false;
                    }, 1000);
                }
             }
         }
         else{
             if(this.posX < 320){
                this.posX += 50 * dt;
                this.posY += 20 * dt;
             }
            else if(this.posY < 300){
                this.posY += 50 * dt;
            }
            else{
                //Erase this from the array
                clearInterval(this.intervalstep);

                this.posX = 160 +       160;
                this.posY = 150 +       150;
                this.going = true;
                this.image.src = "./images/" + this.color + "/step/step00.png";
                this.timer = false;   
                this.imageIterator = 12;
                this.timer = false;
                this.directionchange = false;
                
                this.intervalstep = setInterval(() => {
                    if (this.imageIterator <= 23) {
                        this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else {
                        this.imageIterator = 13;
                        this.image.src = "./images/" + this.color + "/step/step12.png";
                    }
                } , 100);         
                
            }

         }
        
    }
}

class CrewMiniAdmin {   //spaceFromBorder, canvas.height - 180 - 1445/7
    constructor(color) {
        this.width = 40;
        this.height = 40;
        this.Taskscore = 25;
        this.posX = - 20        +160;
        this.posY = 45          + canvas.height - 180 - 1445/7;
        this.going = true;
        this.image = new Image();
        this.image.src = "./images/" + color + "/step/step00.png";
        this.timer = false;   
        this.imageIterator = 0;
        this.timer = false;
        this.directionchange = false;
        this.color = color;
        
        this.intervalstep = setInterval(() => {
            if (this.imageIterator < 10) {
                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else if (this.imageIterator <= 11) {
                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                this.imageIterator += 1;
            }
            else {
                this.imageIterator = 1;
                this.image.src = "./images/" + this.color + "/step/step00.png";
            }
        } , 100);
    }

    render() {
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
        }
    update(dt) {
        if (this.going){
            if(this.posX < 370 && this.posY == 45 + canvas.height - 180 - 1445/7)
            this.posX += 50 * dt;
            else if(this.posY < 110 + canvas.height - 180 - 1445/7) {
                this.posY += 50 * dt;
            }
            else {
                if (!this.timer){
                    this.timer = true;
                    clearInterval(this.intervalstep);
                    this.image.src = "./images/" + this.color + "/Body.png";
                    this.width = 30;
                    this.height = 30;
                    this.posX += 5;
                    this.posY += 5;
                    setTimeout(() => {
                        this.posX-= 5;
                        this.posY-= 5;
                        this.width = 40;
                        this.height = 40;
                        this.imageIterator = 12; 
                        this.intervalstep = setInterval(() => {
                            if (this.imageIterator < 10) {
                                this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else if (this.imageIterator <= 11) {
                                this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                                this.imageIterator += 1;
                            }
                            else {
                                this.imageIterator = 1;
                                this.image.src = "./images/" + this.color + "/step/step00.png";
                            }
                        } , 100);
                        this.timer = false;
                        score += this.Taskscore;
                        this.going = false;
                    }, 1000);
                }
            }
        }
        else{
            if(this.posX < 430)
            this.posX += 50 * dt;
            else{
                //Erase this from the array
                clearInterval(this.intervalstep);

                this.posX = - 20        +160;
                this.posY = 45          + canvas.height - 180 - 1445/7;
                this.going = true;
                this.image.src = "./images/" + this.color + "/step/step00.png";
                this.timer = false;   
                this.imageIterator = 0;
                this.timer = false;
                this.directionchange = false;

                this.intervalstep = setInterval(() => {
                    if (this.imageIterator < 10) {
                        this.image.src = "./images/" + this.color + "/step/step0" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else if (this.imageIterator <= 11) {
                        this.image.src = "./images/" + this.color + "/step/step" + this.imageIterator + ".png";
                        this.imageIterator += 1;
                    }
                    else {
                        this.imageIterator = 1;
                        this.image.src = "./images/" + this.color + "/step/step00.png";
                    }
                } , 100);
                
            }
        }        
    }
}

class CrewBodyDeath { 
    constructor(color, posX, posY) {
        this.width = 40;
        this.height = 40;
        this.posX = posX;
        this.posY = posY;
        this.image = new Image();
        this.color = "red";
        this.color = color;
        
        this.image.src = "images/" + this.color +"/CuerpoMuerto.png";
        
        
        this.active = true;

        setTimeout(() => {
            this.active = false;
            deathAnimation.push(new CrewDeath(this.color, this.posX, this.posY));
        }, 200);
    }

    render(){
        if(this.active)
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}

class CrewDeath { 
    constructor(color, posX, posY) {
        this.width = 40;
        this.height = 30;
        this.posX = posX+10;
        this.posY = posY+15;
        this.image = new Image();
        this.color = "red";
        this.color = color;
        
        this.image.src = "images/"+ this.color +"/Death.png";
               
        this.active = true;

        setTimeout(() => {
            this.active = false;
        }, 400);
    }

    render(){
        if(this.active)
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}



function SpawnCrewAdmin(){
	switch (getRandomInt(0, 4)){
		case 0:
			crewAdminArray.push(new CrewMiniAdmin("red"));
			break;
		case 1:
			crewAdminArray.push(new CrewMiniAdmin("white"));
			break;
		case 2:
			crewAdminArray.push(new CrewMiniAdmin("black"));
			break;
	}
};

function SpawnCrewElectric(){
	switch (getRandomInt(0, 4)){
		case 0:
			crewElectricArray.push(new CrewMiniElectro("red"));
			break;
		case 1:
			crewElectricArray.push(new CrewMiniElectro("white"));
			break;
		case 2:
			crewElectricArray.push(new CrewMiniElectro("black"));
			break;
	}
};

function SpawnCrewMedBay(){
	switch (getRandomInt(0, 4)){
		case 0:
			crewMedBayArray.push(new CrewMiniMedic("red"));
			break;
		case 1:
			crewMedBayArray.push(new CrewMiniMedic("white"));
			break;
		case 2:
			crewMedBayArray.push(new CrewMiniMedic("black"));
			break;
	}
};

function SpawnCrewShields(){
	switch (getRandomInt(0, 4)){
		case 0:
			crewShieldsArray.push(new CrewMiniShields("red"));
			break;
		case 1:
			crewShieldsArray.push(new CrewMiniShields("white"));
			break;
		case 2:
			crewShieldsArray.push(new CrewMiniShields("black"));
			break;
	}
};
