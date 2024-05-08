var clickPower = 1;

var adminCost = 2; //5
var adminCostBase = 2;
var adminPow = 0.6;
var adminPowTotal = 1.0;

var medBayCost = 3; //10
var medBayCostBase = 3;
var medBayPow = 0.4;
var medBayPowTotal = 1.0;

var electricalCost = 5; //15
var electricalCostBase = 5;
var electricalPow = 0.6;
var electricalPowTotal = 1.0;

var shieldsCost = 10; //20
var shieldsCostBase = 10;
var shieldsPow = 0.3;
var shieldsPowTotal = 1.0;

var autoClickerCost = 10;
var votesIncrement = 0.1;

class AutoClicker{
    constructor(){
        this.active = false;
        this.clicksPerSecond = 0.0;
        this.clicksPerSecondBase = 0.0;
    } 

}

class AutoClkrSimple extends AutoClicker{
    
    constructor(){
        super();
    }

    update(dt){
        
        //Upgrade costs
        adminCost = Math.floor(Math.pow(adminCostBase, adminPowTotal + adminPow * crewAdminArray.length));
        medBayCost = Math.floor(Math.pow(medBayCostBase, medBayPowTotal + medBayPow * crewMedBayArray.length));
        electricalCost = Math.floor(Math.pow(electricalCostBase, electricalPowTotal + electricalPow * crewElectricArray.length));
        shieldsCost = Math.floor(Math.pow(shieldsCostBase, shieldsPowTotal + shieldsPow * crewShieldsArray.length));
        autoClickerCost = doubleAuto ? this.clicksPerSecond * 50 : this.clicksPerSecond * 100;
        
        this.clicksPerSecond = doubleAuto ? this.clicksPerSecondBase *2  : this.clicksPerSecondBase; 

        if(!this.active && this.clicksPerSecond >= 0.1){
            this.active = true;
            setTimeout(() => {
                if(impostor.isImpostorAlive()){
                    if(doubleAuto){
                        DamageImpostor();
                        DamageImpostor();
                    }
                    else{
                        DamageImpostor();
                    }
                    
                }
                this.active = false;
            }, 1000/this.clicksPerSecond);
        }
    }
}