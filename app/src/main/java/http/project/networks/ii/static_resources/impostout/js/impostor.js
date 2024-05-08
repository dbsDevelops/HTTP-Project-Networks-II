

class Impostor {
    constructor(color, lifePoints, name, score) {
      
      this.width = 250;
      this.height = 300;
      this.posX = canvas.width / 2 -this.width / 2;
      this.posY = canvas.height / 2 - this.height / 2;
      this.image = new Image();
      this.forceDamage = 1;

      //Default values
      this.color = 'red';
      this.name = 'Impostor';
      
      this.lifePoints = 5;
      this.score = 1;

      //Custom values
      this.color = color;
      this.name = name;
      this.sprite = "images/" + this.color + "/Body.png";  
      this.lifePoints = lifePoints;
      this.score = score;
      this.image.src = this.sprite;

      
    }
  
    update(dt){
        this.OverImpostor();
    }
    
    render(){
        if (this.isImpostorAlive()) {
            ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
        }
    }
    
    isImpostorAlive(){
        return this.lifePoints > 0;
    }
    
    DamageImpostor(){
        this.lifePoints -= Math.floor(clickPower * (this.forceDamage + (combo/500)));
    } 

    MouseOverImpostor(){
        return (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height);
    }

    OverImpostor(){
        if(this.MouseOverImpostor()){
            if(!isClick){
                //Make the image bigger
                this.width = 350;
                this.height = 400;
                this.posX = canvas.width / 2 -this.width / 2;
                this.posY = canvas.height / 2 - this.height / 2;
            } 
            else {
                //Make the image bit smaller
                this.width = 325;
                this.height = 375;
                this.posX = canvas.width / 2 -this.width / 2;
                this.posY = canvas.height / 2 - this.height / 2;        
            }
        } else {
                //Make the image smaller
                this.width = 250;
                this.height = 300;
                this.posX = canvas.width / 2 -this.width / 2;
                this.posY = canvas.height / 2 - this.height / 2;
            }
        }
  }

  //To damage the impostor
  function ClickingImpostor(x,y){
	if(x > canvas.width / 2 - impostor.width / 2 && x < canvas.width / 2 + impostor.width / 2 &&
	 y > canvas.height / 2 - impostor.height / 2 && y < canvas.height / 2 + impostor.height / 2){
		
		if (impostor.isImpostorAlive()) {
		    combo++;
		    UI.randomAngleCombo = getRandomInt(-25, 25);
		    UI.randomSizeCombo = getRandomInt(45, 65);
            DamageImpostor();
		}
	}
};

  DamageImpostor = function(){
    score += Math.floor(clickPower*(1 + (combo/500)));
	impostor.DamageImpostor();

	//Kill the impostor
	if(!impostor.isImpostorAlive()){
        sndKilled.pause();
        sndKilled.currentTime = 0;
		sndKilled.play();

        UI.specialScore = "+" + impostor.score;
        setTimeout(() => {
            UI.specialScore = "";
        }, 1000);

		score += impostor.score;
		impostorSpawned = false;
		setTimeout(SpawnImpostor, 1000);

        //Spawn death animation
        console.log("Spawn death animation: " + impostor.color);
        deathAnimation.push(new ImpostorBodyDeath(impostor.color));
	}
};

class ImpostorBodyDeath { 
    constructor(color){
        this.width = 350;
        this.height = 400;
        this.posX = canvas.width / 2 -this.width / 2;
        this.posY = canvas.height / 2 - this.height / 2;
        this.image = new Image();
        this.color = color;
       
        this.image.src = "images/" + this.color + "/CuerpoMuerto.png";
                
        
        this.active = true;

        setTimeout(() => {
            this.active = false;
            deathAnimation.push(new ImpostorDeath(impostor.color));
        }, 200);
    }

    render(){
        if(this.active)
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}

class ImpostorDeath { 
    constructor(color){
        this.width = 350;
        this.height = 400;
        this.posX = canvas.width / 2 -this.width / 2 + 100;
        this.posY = canvas.height / 2 - this.height / 2 + 100;
        this.image = new Image();
        this.color = color;
        
        this.image.src = "images/" + this.color + "/Death.png";
                
        
        this.active = true;

        setTimeout(() => {
            this.active = false;
        }, 200);
    }

    render(){
        if(this.active)
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }
}