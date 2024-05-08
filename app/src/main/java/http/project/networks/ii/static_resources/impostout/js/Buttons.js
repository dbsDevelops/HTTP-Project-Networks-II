


class Button {
    constructor(name, sprite, posX, posY, width, height) {
        //Custom values
        this.name = name;
        this.image = new Image();
        this.image.src = sprite;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    render(){
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    }

   
}

class simpleButton extends Button{
    constructor(name, sprite, posX, posY, width, height) {
        super(name, sprite, posX, posY, width, height);
        
    }
    
    update(dt){
    
    }
      
    render(){
        super.render();
    }

    
    MouseOverButton(){
        return (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height);
    }

    ClickingButton(type){

        if(this.MouseOverButton() && isClick){
            switch(type){
                case "autoclicker":
                    if(score - autoClickerCost >= 0){
                        score -= autoClickerCost;
                        autoclicker.clicksPerSecondBase += votesIncrement;
                    }
                        break;
                    case "howtoplay":
                        // Go to the How to Play html
                        saveAllCookies();
                        window.location.href = "howtoplay.html";
                        break;
                    case "erase":
                        // Erase the game
                        deleteAllCookies();
                        break;
                    case "save":
                        // Save the game
                        saveAllCookies();
                        break;
                    case "gladiator":
                        if(score - hatCost >= 0 && !GladiatorUnloked){
                            score -= hatCost;
                            GladiatorUnloked = true;
                        }
                        if(GladiatorUnloked) ChangeHat("Gladiator");
                        break;
                    case "hat":
                        if(score - hatCost >= 0 && !HatUnloked){
                            score -= hatCost;
                            HatUnloked = true;
                        }
                        if(HatUnloked) ChangeHat("Hat");
                    break;
                    case "demon":
                        if(score - hatCost >= 0 && !DemonUnloked){
                            score -= hatCost;
                            DemonUnloked = true;
                        }
                        if(DemonUnloked) ChangeHat("Demon");
                    break;
                    case "archeologist":
                        if(score - costumeCost >= 0 && !ArcheologistUnloked){
                            score -= costumeCost
                            ArcheologistUnloked = true;
                        }
                        if(ArcheologistUnloked) ChangeCostume("Archeologist");
                    break;
                    case "suit":
                        if(score - costumeCost >= 0 && !SuitUnloked){
                            score -= costumeCost
                            SuitUnloked = true;
                        }
                        if(SuitUnloked) ChangeCostume("Suit");
                    break;
                    case "robot":
                        if(score - costumeCost >= 0 && !RobotUnloked){
                            score -= costumeCost
                            RobotUnloked = true;
                        }
                        if(RobotUnloked) ChangeCostume("Robot");
                    break;
                    case "cube":
                        if(score - petCost >= 0 && !CubeUnloked){
                            score -= petCost;
                            CubeUnloked = true;
                        }
                        if(CubeUnloked) ChangePet("Cube");
                    break;
                    case "clank":
                        if(score - petCost >= 0 && !ClankUnloked){
                            score -= petCost;
                            ClankUnloked = true;
                        }
                        if(ClankUnloked) ChangePet("ClankPet");
                    break;
                    case "spark":
                        if(score - petCost >= 0 && !SparkUnloked){
                            score -= petCost;
                            SparkUnloked = true;
                        }
                        if(SparkUnloked) ChangePet("343GuiltySpark");
                    break;

                        
            }
        }
    }
}

class upgradeButton extends Button{
    constructor(name, sprite, posX, posY, width, height) {
        super(name, sprite, posX, posY, width, height);
        
    }
    
    update(dt){
    
    }
      
    render(){
        super.render();
    }

    
    MouseOverButton(){
        return (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height);
    }


    ClickingButton(cost, type){
        if(this.MouseOverButton() && isClick){
            if(score-cost >= 0){
                score -= cost;
                this.image.src = "images/interface/ButtonPressed.png";
                
                switch(type){
                    case "admin":
                        SpawnCrewAdmin();
                        break;
                    case "medbay":
                        SpawnCrewMedBay();
                        break;
                    case "electrical":
                        SpawnCrewElectric();
                        break;
                    case "shields":
                        SpawnCrewShields();
                        break;
                }

                var self = this;
                setTimeout(function(){
                    console.log("Button pressed");
                    clickPower += 0.001;
                    self.image.src = "images/interface/Button.png";
                }, 200);
                return true;
            }
        }
    };

    
    
}