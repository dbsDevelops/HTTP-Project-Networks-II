//Space invaders game.js
var canvas = document.createElement("canvas"); //Create canvas element
var ctx = canvas.getContext("2d"); //Create 2d context
canvas.width = window.innerWidth; //Set canvas width and height
canvas.height = window.innerHeight;
canvas.style.position = "absolute"; //Set canvas position
document.body.appendChild(canvas); //Add canvas to body

//Game variables
var gameOver = false
var imgBackground = new Image();
imgBackground.src = "./images/background.png";
let level = 0;

paintBackground();

var update = function (dt) {

	//Update player

	player.update(dt, keysDown, speedgame);

	//Update bullets
	for (var i = 0; i < bullets.length; i++) {
		bullets[i].update(dt, speedgame);
		if (bullets[i].y < 0 || bullets[i].y > canvas.height) {
			bullets.splice(i, 1);
		}
	}

	//Update aliens using
	for (var i = 0; i < aliensMatrix.length; i++) {
		for (var j = 0; j < aliensMatrix[i].length; j++) {
			aliensMatrix[i][j].update(dt, speedgame);
		}
	}

	if (needToGoDown) {
		for (var i = 0; i < aliensMatrix.length; i++) {
			for (var j = 0; j < aliensMatrix[i].length; j++) {
				aliensMatrix[i][j].toGoDown();

			}
		}
		needToGoDown = false;
	}

	//Erase from the alienExplosion array the aliens that are not active
	for (var i = 0; i < alienExplosion.length; i++) {
		if (!alienExplosion[i].active) {
			alienExplosion.splice(i, 1);
		}
	}

	if (isSoundBGTone) {
		isSoundBGTone = false;

		//Play sound
		if (toneIterator == 0) {
			sndTone1.play();
		}
		else if (toneIterator == 1) {
			sndTone2.play();
		}
		else if (toneIterator == 2) {
			sndTone3.play();
		}
		else if (toneIterator == 3) {
			sndTone4.play();
		}
		else {
			sndTone1.play();
		}

		toneIterator++;
		if (toneIterator > 3) {
			toneIterator = 0;
		}


		setTimeout(() => {

			isSoundBGTone = true;
		}, 1000 / speedgame);
		
	}


    checkCollisionBetweenBulletsAndAliens(aliensMatrix, bullets);
    checkCollisionBetweenBulletsAndPlayer(player, bullets);
    checkCollisionBetweenBulletsAndCover(covers, bullets);
    checkCollisionBetweenPlayerAndAliens(player, aliensMatrix);
    checkCollisionBetweenAliensAndCover(covers, aliensMatrix);
	checkCollisionBetweenSaucerAndBullet(saucer, bullets);
    AlienShoot(aliensMatrix);
	saucer.SaucerUpdateAndInstantiate(dt , speedgame , saucerspawned);


};

function paintBackground() {
	//Use image as background
	ctx.drawImage(imgBackground, 0, 0, canvas.width, canvas.height);
};

function getRandomIntInclusive(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
}

function getRandomBinary() {
	return Math.round(Math.random());
}

var StartNewGame = function () {
	bullets = [];
	level += 1;
	if (player.lives<3)	player.lives++;
	player.x = 50;
	player.y = canvas.height - 120;
	createCovers(covers);
	createAliens();
};

//Start game
var start = function () {
	player.lives = 3;
	level = 0;
	player.score = 0;
	speedgame = 1;
	StartNewGame();
};

var keysDown = {};
var keysRecord = [];
var isKonamiCode = false;

addEventListener("keydown", function (e) {
	keysDown[e.keyCode] = true;
	
	//Konami code easter egg. Only start recording if the user pressed the up button. Erase the keysRecord if the combination is not correct to optimize.
	if (keysRecord.length == 0 && e.keyCode == 38) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 1 && e.keyCode == 38) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 2 && e.keyCode == 40) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 3 && e.keyCode == 40) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 4 && e.keyCode == 37) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 5 && e.keyCode == 39) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 6 && e.keyCode == 37) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 7 && e.keyCode == 39) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 8 && e.keyCode == 66) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 9 && e.keyCode == 65) {
		keysRecord.push(e.keyCode);
	}
	else if (keysRecord.length == 10 && e.keyCode == 13) {
		keysRecord.push(e.keyCode);
	}
	else {
		keysRecord = [];
		isKonamiCode = false;
	}

	if (keysRecord.length == 11) {
		keysRecord = [];
		isKonamiCode = true;

	}
}, false);

addEventListener("keyup", function (e) {
	delete keysDown[e.keyCode];
}, false);

//Render
var render = function () {

	//Clear canvas
	ctx.clearRect(0, 0, canvas.width, canvas.height);

	paintBackground();

	//Render player
	player.render();

	//Render bullets
	for (var i = 0; i < bullets.length; i++) {
		bullets[i].render();
	}
	//Render aliensMatrix
	for (var i = 0; i < aliensMatrix.length; i++) {
		for (var j = 0; j < aliensMatrix[i].length; j++) {
			if (aliensMatrix[i][j].active)
				aliensMatrix[i][j].render();
		}
	}

	//Render alienExplosion
	for (var i = 0; i < alienExplosion.length; i++) {
		alienExplosion[i].render();
	}

	//Render covers
	for (var i = 0; i < covers.length; i++) {
		covers[i].render();
	}

	//Draw score top left
	ctx.font = "30px Arial";
	ctx.fillStyle = "white";
	ctx.textAlign = "left";
	ctx.fillText("Score: " + player.score, 20, 30);

	//Draw lives top right
	ctx.font = "30px Arial";
	ctx.fillStyle = "white";
	ctx.textAlign = "right";
	ctx.fillText("Lives: " + player.lives, canvas.width - 20, 30);

	//Draw level in the middle
	ctx.font = "30px Arial";
	ctx.fillStyle = "white";
	ctx.textAlign = "center";
	ctx.fillText("Level: " + level, canvas.width / 2, 30);

	// Draw saucer
	saucer.render();
};

// The main game loop
var main = function () {
	var now = Date.now();
	var delta = now - then;

	if (!gameOver) {
		update(delta / 1000);
		render();

		//Game Win
		if (isAliensMatrixClear(aliensMatrix)) {
			StartNewGame();
			speedgame -= 0.4;
		}

		//Game Over
		if (player.lives <= 0 || isBellowCanvas) {
			clearAlienMatrix(aliensMatrix);
			bullets = [];
			gameOver = true;

			render();


			//Draw Game Over
			ctx.font = "80px Arial";
			ctx.fillStyle = "white";
			ctx.textAlign = "center";
			ctx.fillText("Game Over", canvas.width / 2, canvas.height / 2);

			//Show the player score
			ctx.font = "50px Arial";
			ctx.fillStyle = "white";
			ctx.textAlign = "center";
			ctx.fillText("Score: " + player.score, canvas.width / 2, canvas.height / 2 + 80);

			//Wait 10 seconds to restart the game
			setTimeout(function () {
				gameOver = false;
				isBellowCanvas = false;
				start();
			}, 10000);
		}

	}


	then = now;

	requestAnimationFrame(main);
};

// Cross-browser support for requestAnimationFrame
var w = window;
requestAnimationFrame = w.requestAnimationFrame ||
	w.webkitRequestAnimationFrame || w.msRequestAnimationFrame
	|| w.mozRequestAnimationFrame;

window.addEventListener('resize', function () {
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
});


//Game objects
var player = new Player(50, canvas.height - 120, 4);
var bullets = [];
var aliensMatrix = createMatrix(5, 11);
var AlienCanShoot = true;
var speedgame = 1;
var alienExplosion = [];
var covers = [];
var saucer = new Saucer();
var saucerspawned = false;


//Lets play
var then = Date.now();
start();
main();

