var hatCost = 1000;
var costumeCost = 10000;
var petCost = 50000; 

var GladiatorUnloked = false;
var HatUnloked = false;
var DemonUnloked = false;

var ArcheologistUnloked = false;
var SuitUnloked = false;
var RobotUnloked = false;

var CubeUnloked = false;
var ClankUnloked = false;
var SparkUnloked = false;

var lockImage = new Image();
lockImage.src = "./images/icons/Lock.png";


class HUD{

    constructor() {
        this.scoreText = "Score: 0";
        this.movableTexts = [];

        this.imgCrewAnnounce = new Image();
        this.imgCrewAnnounce.src = "./images/icons/Announce.png";

        this.specialScore = "";

        this.randomAngleCombo = 0;
        this.randomSizeCombo = 50;

        this.createMovableText("Welcome to the ImpostOut Web Game", "grey", -75);
        this.newMovableTextFromFile();

    }

    update(dt) {
        this.scoreText = 'Crew Votes: ' + Math.floor(score);

        //Movable Texts
        for (var i = 0; i < this.movableTexts.length ; i++) {
            this.movableTexts[i].update(dt);

            //Remove if out of screen
            if(this.movableTexts[i].x > canvas.width+1 || this.movableTexts[i].x < -401){
                this.movableTexts.splice(i, 1);
            }
        }
    }

    render() {
        //Black line at the top
        ctx.fillStyle = 'black';
        ctx.fillRect(0, 0, canvas.width, 25);

        //Crew Votes
        ctx.font = '80px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText(this.scoreText, 
            canvas.width/2 - 120, 
            canvas.height/2 - 360);

        //Votes per second
        ctx.font = '30px AmongUsFont';
        if(doubleAuto) ctx.fillStyle = "red";
        else ctx.fillStyle = "#e0e0e0";
        ctx.fillText('Votes per second: ' + Math.floor(autoclicker.clicksPerSecond*10)/10,
            canvas.width/2 - 55,
            canvas.height/2 - 325);

        //Upgrade Cost
        ctx.font = '40px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText('Upgrade Cost: ' + electricalCost,
        ElectricalRoom.posX+buttonSize, ElectricalRoom.posY +290);

        ctx.font = '40px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText('Upgrade Cost: ' + medBayCost,
        MedbayRoom.posX+buttonSize, MedbayRoom.posY +260);

        ctx.font = '40px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText('Upgrade Cost: ' + adminCost,
        AdminRoom.posX+buttonSize, AdminRoom.posY +290);
        
        ctx.font = '40px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText('Upgrade Cost: ' + shieldsCost,
        Shields.posX+buttonSize, Shields.posY +320);

        ctx.font = '40px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText('Upgrade Cost: ' + Math.floor(autoClickerCost),
        canvas.width/2- 60, canvas.height -35);

        //Shop Cost

        //Gladiator
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Gladiator: " + hatCost,
        GladiatorShopButton.posX + 10, GladiatorShopButton.posY + 77);
        if(!GladiatorUnloked) ctx.drawImage(lockImage, GladiatorShopButton.posX + 45, GladiatorShopButton.posY + 40, 20, 20);
        //Hat
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Hat: " + hatCost,
        HatShopButton.posX + 20, HatShopButton.posY + 77);
        if(!HatUnloked) ctx.drawImage(lockImage, HatShopButton.posX + 45, HatShopButton.posY + 40, 20, 20);
        //Demon
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Demon: " + hatCost,
        DemonShopButton.posX + 10, DemonShopButton.posY + 77);
        if(!DemonUnloked) ctx.drawImage(lockImage, DemonShopButton.posX + 45, DemonShopButton.posY + 40, 20, 20);

        //Archeologist
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Archeologist: " + costumeCost,
        ArcheologistShopButton.posX - 20, ArcheologistShopButton.posY + 77);
        if(!ArcheologistUnloked) ctx.drawImage(lockImage, ArcheologistShopButton.posX + 40, ArcheologistShopButton.posY + 40, 20, 20);
        //Suit
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Suit: " + costumeCost,
        SuitShopButton.posX +5, SuitShopButton.posY + 77);
        if(!SuitUnloked) ctx.drawImage(lockImage, SuitShopButton.posX + 40, SuitShopButton.posY + 40, 20, 20);
        //Robot
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("Robot: " + costumeCost,
        RobotShopButton.posX+5, RobotShopButton.posY + 77);
        if(!RobotUnloked) ctx.drawImage(lockImage, RobotShopButton.posX + 40, RobotShopButton.posY + 40, 20, 20);

        //Pets
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = "white";
        ctx.fillText("All pets: " + petCost,
        ClankShopButton.posX -20, ClankShopButton.posY + 60);

        if(!CubeUnloked) ctx.drawImage(lockImage, CubeShopButton.posX + 15, CubeShopButton.posY + 20, 15, 15);
        if(!ClankUnloked) ctx.drawImage(lockImage, ClankShopButton.posX + 15, ClankShopButton.posY + 20, 15, 15);
        if(!SparkUnloked) ctx.drawImage(lockImage, SparkShopButton.posX + 15, SparkShopButton.posY + 20, 15, 15);

        //Shop Unlocks


        // Special Score
        ctx.font = '60px AmongUsFont';
        ctx.fillStyle = "grey";
        ctx.save(); // Save the current state
            ctx.translate(canvas.width/2 + 180, canvas.height/2 - 300); 
            ctx.rotate(-15 * Math.PI / 180); 
            ctx.fillText(this.specialScore, 0, 0); 
            ctx.restore(); 

        //Combo
        if(combo > 9){
             
            ctx.font = this.randomSizeCombo + 'px AmongUsFont';
            ctx.fillStyle = "rgba(255, 0, 0, 0.7)";
            ctx.save(); // Save the current state
            ctx.translate(canvas.width/2, canvas.height/2 + 175 ); 
            ctx.rotate(this.randomAngleCombo * Math.PI / 180); 
            ctx.fillText('Combo x' + combo + '!', -50, 0); 
            ctx.restore(); 
        }          

        //Movable Texts
        for (var i = 0; i < this.movableTexts.length ; i++) {
            this.movableTexts[i].render();
        }

        //Image at the top right
        ctx.drawImage(this.imgCrewAnnounce, canvas.width - 30, - 5, 40, 40);

        
        // Anti Cease and desist
        ctx.font = '20px AmongUsFont';
        ctx.fillStyle = 'grey';
        ctx.fillText('Sprites property of @. Only for class project porpuses and sus amogus.', 10, canvas.height - 10);

    }
    
    newMovableTextFromFile() {
        setInterval(() => {
            this.createMovableText(textPrompt[getRandomInt(0, textPrompt.length)], "grey", -75);
        }, 17500);

    }
        

    createMovableText(text, color, speed) {
        var movableText = new MovableText(text, color, speed);
        this.movableTexts.push(movableText);
    }

}

class MovableText{
    constructor(text, color, speed) {
        this.text = "Default Text"
        
        this.font = '30px AmongUsFont';
        this.speed = 0;
        
        this.text = text;
        this.color = color;
        this.speed = speed;

        this.y = 25;

        if(this.speed <= 0){
            this.x = canvas.width;
        }
        else{
            this.x = -400;
        }
    }

    update(dt) {
        this.x += this.speed * dt;
    }

    render() {
        ctx.font = this.font;
        ctx.fillStyle = this.color;
        ctx.fillText(this.text, this.x, this.y);
    }
}

