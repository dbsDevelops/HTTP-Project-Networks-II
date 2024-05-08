var canvas = document.createElement("canvas");
var ctx = canvas.getContext("2d");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
canvas.style.position = "absolute";
document.body.appendChild(canvas);

// This is the background, don't get scared
var bgPattern;
var tileReady = false;
var tileImage = new Image();
tileImage.onload = function () {
	tileReady = true;
	bgPattern = ctx.createPattern(tileImage,"repeat");
};
tileImage.src = "./images/bgPattern.png";

// ----------------------------------------------------------------------------------------------------> Game start
var start = function () {
	StartNewGame();
};

var StartNewGame = function(){
	SpawnImpostor();

}

// ----------------------------------------------------------------------------------------------------> Useful Small Functions
ResetClick = function(){
	isClick = false;
}

ResetCombo = function(){
	combo = 0;
}

SpawnImpostor = function(){
	var random = Math.floor(Math.random() * 4);
	switch(random){
		case 0:
			impostor = new ImpostorRed();
			break;
		case 1:
			impostor = new ImpostorBlack();
			break;
		case 2:
			impostor = new ImpostorWhite();
			break;
		case 3:
			impostor = new ImpostorBlack();
			break;
	}
	impostorSpawned = true;
};

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); // The maximum is exclusive and the minimum is inclusive
}

// ----------------------------------------------------------------------------------------------------> Event Listeners
var keysDown = {};

addEventListener("keydown", function(e) {
	keysDown[e.keyCode] = true;
}, false);

addEventListener("keyup", function(e) {
	delete keysDown[e.keyCode];
}, false);

addEventListener("click", function(e) {
	x = e.offsetX;
	y = e.offsetY;
	isClick = true;

	sndBackground.play();

	setTimeout(ResetClick, 100);

	this.clearTimeout(setTimeoutCombo);
	setTimeoutCombo = setTimeout(() => {
		combo = 0;
	}, 2000);
	
	ventAdmin.ClickingVent(x, y);
	ventElectric.ClickingVent(x, y);
	ventMedBay.ClickingVent(x, y);
	ventShields.ClickingVent(x, y);

	ClickingImpostor(x, y);

	AdminButton.ClickingButton( adminCost, "admin");
	ElectricButton.ClickingButton( electricalCost, "electrical");
	MedbayButton.ClickingButton( medBayCost, "medbay");
	ShieldsButton.ClickingButton( shieldsCost, "shields");
	AutoClickerButton.ClickingButton( "autoclicker");

	HowToPlayButton.ClickingButton( "howtoplay");
	EraseButton.ClickingButton( "erase");
	SaveButton.ClickingButton( "save");

	GladiatorShopButton.ClickingButton( "gladiator");
	HatShopButton.ClickingButton( "hat");
	DemonShopButton.ClickingButton( "demon");

	ArcheologistShopButton.ClickingButton( "archeologist");
	SuitShopButton.ClickingButton( "suit");
	RobotShopButton.ClickingButton( "robot");

	CubeShopButton.ClickingButton( "cube");
	ClankShopButton.ClickingButton( "clank");
	SparkShopButton.ClickingButton( "spark");
	
}, false);

addEventListener('mousemove', (e) => {
	mouseX = e.clientX;
	mouseY = e.clientY;
});


//-----------------------------------------------------------------------------------------------------> UPDATE
var update = function (dt) {
	if(impostorSpawned){
		impostor.update(dt);
	}
	UI.update(dt);

	for(var i = 0; i < phalaxArray.length; i++){
		phalaxArray[i].update(dt);
	}

	autoclicker.update(dt);

	//update all crewAdminArray objects
	for(var i = 0; i < crewAdminArray.length; i++){
		
		crewAdminArray[i].update(dt);
	}
	//update all crewElectricArray objects
	for(var i = 0; i < crewElectricArray.length; i++){
		crewElectricArray[i].update(dt);
	}
	//update all crewMedbayArray objects
	for(var i = 0; i < crewMedBayArray.length; i++){
		crewMedBayArray[i].update(dt);
	}
	//update all crewShieldsArray objects
	for(var i = 0; i < crewShieldsArray.length; i++){
		crewShieldsArray[i].update(dt);
	}
	var amonguscount = crewAdminArray.length + crewElectricArray.length + crewMedBayArray.length + crewShieldsArray.length;
	barometer.update(dt, amonguscount);

	ventElectric.update(dt);
	ventMedBay.update(dt);
	ventShields.update(dt);
	ventAdmin.update(dt);

	doubleAutoPwrUp.update(dt);


	//Update collisions
	checkCollisionBetweenCrewArrayAndVent(crewAdminArray, ventAdmin);
	checkCollisionBetweenCrewArrayAndVent(crewElectricArray, ventElectric);
	checkCollisionBetweenCrewArrayAndVent(crewMedBayArray, ventMedBay);
	checkCollisionBetweenCrewArrayAndVent(crewShieldsArray, ventShields);
};



//-----------------------------------------------------------------------------------------------------> RENDER
var render = function () {
	
	
	if (tileReady) {
		ctx.fillStyle = bgPattern;
		ctx.fillRect(0,0,canvas.width,canvas.height);
	}

	//paralax render
	if (phalaxArray.length > 0 && impostorSpawned){
		for (var i = 0; i < phalaxArray.length; i++) {
			phalaxArray[i].render();
		}
	}
	
	if (impostorSpawned) {
		impostor.render();
	}

	renderPlaces();

	AdminButton.render();
	ElectricButton.render();
	MedbayButton.render();
	ShieldsButton.render();

	AutoClickerButton.render();
	HowToPlayButton.render();

	EraseButton.render();
	SaveButton.render();

	GladiatorShopButton.render();
	HatShopButton.render();
	DemonShopButton.render();
	
	ArcheologistShopButton.render();
	SuitShopButton.render();
	RobotShopButton.render();

	CubeShopButton.render();
	ClankShopButton.render();
	SparkShopButton.render();

	barometer.render();

	//render all crewAdminArray objects
	if (crewAdminArray.length > 0)
	for(var i = crewAdminArray.length-1; i >= 0; i--){
		
		crewAdminArray[i].render();
	}

	if (crewElectricArray.length > 0)
	//render all crewElectricArray objects
	for(var i = crewElectricArray.length-1; i >= 0 ; i--){
		crewElectricArray[i].render();
	}

	if (crewMedBayArray.length > 0)
	//render all crewMedbayArray objects
	for(var i = crewMedBayArray.length-1; i >= 0 ; i--){
		crewMedBayArray[i].render();
	}

	if (crewShieldsArray.length > 0)
	//render all crewShieldsArray objects
	for(var i = crewShieldsArray.length-1; i >= 0; i--){
		crewShieldsArray[i].render();
	}

	if (deathAnimation.length > 0)
	//render all deathAnimation objects
	for(var i = deathAnimation.length-1; i >= 0; i--){
		deathAnimation[i].render();
	}

	for(var i = 0; i < exclamations.length; i++){
		exclamations[i].render();
	}

	customSus.render();

	if(UI)	UI.render();

	doubleAutoPwrUp.render();
	
};

//-----------------------------------------------------------------------------------------------------> MAIN
var main = function () {
	var now = Date.now();
	var delta = now - then;

	update(delta / 1000);
	render();

	then = now;

	// Request to do this again ASAP
	requestAnimationFrame(main);
};

// Cross-browser support for requestAnimationFrame
var w = window;
requestAnimationFrame = w.requestAnimationFrame ||
 w.webkitRequestAnimationFrame || w.msRequestAnimationFrame 
 || w.mozRequestAnimationFrame;



 //-----------------------------------------------------------------------------------------------------> Game variables and objects
 var impostorSpawned = false;
 var impostor = new ImpostorRed();
 var deathAnimation = [];
 var score = 0;
 var UI = new HUD();
 var miniCrews = [];
 var customSus = new CustomSus();

 //Paralax
 var phalaxArray = [];
 phalaxArray.push(new paralax("./images/interface/phalax1.png", canvas.width/2 -300, canvas.height/2 -300, 1));
 phalaxArray.push(new paralax("./images/interface/phalax2.png", canvas.width/2 -300, canvas.height/2 -300, -1.5));
 phalaxArray.push(new paralax("./images/interface/phalax3.png", canvas.width/2 -300, canvas.height/2 -300, 1.5));

 //Autoclicker
 var autoclicker = new AutoClkrSimple();

 //Combo
 var setTimeoutCombo = 0;
 var combo = 0;

 //Mouse variables
 var isClick = false;
 var mouseX = 0;
 var mouseY = 0;

//Rooms objects
var spaceFromBorder = 150;
var AdminRoom = new bg_room("Admin", "./images/Admin.png", spaceFromBorder, canvas.height - 180 - 1445/7,  2160/7, 1445/7 );
var ElectricalRoom = new bg_room("Electrical", "./images/Electrics.png", spaceFromBorder, 150,  1523/5, 913/5 );
var MedbayRoom = new bg_room("Medbay", "./images/MedBay.png", canvas.width - spaceFromBorder - 364/1.7, 140,  364/1.5, 318/1.7 );
var Shields = new bg_room("Shields", "./images/Shields.png", canvas.width - spaceFromBorder - 437/2, canvas.height - 180 - 477/2,  437/2, 477/2 );

//Cams on rooms
var AdminCam = new bg_room("AdminCam", "./images/AdminCam.png", spaceFromBorder - 20, canvas.height - 160 - 1676/7,  2340/7, 1676/7 );
var ElectricalCam = new bg_room("ElectricalCam", "./images/ElectricsCam.png", spaceFromBorder-15, 135,  1688/5, 1071/5 );
var MedbayCam = new bg_room("MedbayCam", "./images/MedBayCam.png", canvas.width - spaceFromBorder + 18 - 416/1.7, 140 - 10,  416/1.55, 357/1.75 );
var ShieldsCam = new bg_room("ShieldsCam", "./images/ShieldsCam.png", canvas.width - spaceFromBorder + 10 - 485/2, canvas.height - 180 + 10 - 524/2,  485/2, 524/2 );

//Supports
var AdminSupport = new bg_room("AdminSupport", "./images/interface/CameraSupport.png", 0, AdminRoom.posY + 80,  150, 50 );
var ElectricalSupport = new bg_room("ElectricSupport", "./images/interface/CameraSupport.png", 0, ElectricalRoom.posY + 70, 150, 50 );
var MedbaySupport = new bg_room("MedbaySupport", "./images/interface/CameraSupport.png", MedbayRoom.posX + 220, MedbayRoom.posY + 60,  150, 50 );
var ShieldsSupport = new bg_room("ShieldsSupport", "./images/interface/CameraSupport.png", Shields.posX + 220, Shields.posY + 90,  150, 50 );

//Upgrade buttons
var buttonSize = 100;
var AdminButton = new upgradeButton("ButtonAdmin", "./images/interface/Button.png", AdminRoom.posX, AdminRoom.posY +210, buttonSize, buttonSize );
var ElectricButton = new upgradeButton("ButtonElectric", "./images/interface/Button.png", ElectricalRoom.posX, ElectricalRoom.posY+210, buttonSize, buttonSize );
var MedbayButton = new upgradeButton("ButtonMedbay", "./images/interface/Button.png", MedbayRoom.posX, MedbayRoom.posY+190, buttonSize, buttonSize );
var ShieldsButton = new upgradeButton("ButtonShields", "./images/interface/Button.png", Shields.posX, Shields.posY+250, buttonSize, buttonSize );

//Other buttons
var AutoClickerButton = new simpleButton("ButtonAutoclicker", "./images/icons/Report.png", canvas.width/2 - 30, canvas.height - 200, buttonSize*1.25, buttonSize*1.25);
var HowToPlayButton = new simpleButton("ButtonHowtoPlay", "./images/interface/HowToPlay.png", canvas.width - 155, canvas.height - 50, 150, 40);
var EraseButton = new simpleButton("ButtonErase", "./images/interface/Erase.png", canvas.width - 80, canvas.height - 95, 75, 40);
var SaveButton = new simpleButton("ButtonSave", "./images/interface/Save.png", canvas.width - 235, canvas.height - 50, 75, 40);

//Shop Hat Buttons
var GladiatorShopButton = new simpleButton("ButtonGladiator", "./images/miniCustom/Gladiator.png", 130, 0, 120/1.5, 120/1.5);
var HatShopButton = new simpleButton("ButtonHat", "./images/miniCustom/Hat.png", 230, 0, 120/1.5, 120/1.5);
var DemonShopButton = new simpleButton("ButtonDemon", "./images/miniCustom/Demon.png", 330, 0, 120/1.5, 120/1.5);

//Shop Costume Button
var ArcheologistShopButton = new simpleButton("ButtonArcheologist", "./images/miniCustom/Archeologist.png", 180, 55, 165/3, 211/3);
var SuitShopButton = new simpleButton("ButtonSuit", "./images/miniCustom/Suit.png", 280, 55, 165/3, 211/3);
var RobotShopButton = new simpleButton("ButtonRobot", "./images/miniCustom/Robot.png", 380, 55, 165/3, 211/3);

//Shop Pet Button
var CubeShopButton = new simpleButton("ButtonCube", "./images/miniCustom/Cube.png", 20, 145, 120/4, 141/4);
var ClankShopButton = new simpleButton("ButtonClank", "./images/miniCustom/ClankPet.png", 60, 145, 120/4, 141/4);
var SparkShopButton = new simpleButton("ButtonSpark", "./images/miniCustom/343GuiltySpark.png", 100, 145, 120/4, 141/4);

//Crewmates
var crewAdminArray = [];
var crewElectricArray = [];
var crewMedBayArray = [];
var crewShieldsArray = [];

var AdminSpawn = 0;
var ElectricSpawn = 0;
var MedbaySpawn = 0;
var ShieldsSpawn = 0;

//Vents
var ventElectric = new Vent(20+spaceFromBorder,45 + 150);
var ventMedBay = new Vent(60 + canvas.width - spaceFromBorder - 364/1.7, 110 + 140);
var ventShields = new Vent(80 + canvas.width - spaceFromBorder - 437/2,95 + canvas.height - 180 - 477/2);
var ventAdmin = new Vent(150 + spaceFromBorder, 50+ canvas.height - 180 - 1445/7);

var doubleAutoPwrUp = new DoubleAutoPowerUp(-100,-100, 75, 75, "./images/icons/PowerUPx2.png");

var exclamations = [];

var barometer = new Barometer(canvas.width - spaceFromBorder - 150, 100);

// -----------------------------------------------------------------------------------------------------> Let's play this game!!


// Load the cookies
loadAllCookies();

// Set interval of cookie save
setInterval(saveAllCookies, 60000);

var then = Date.now();
start();
main();
