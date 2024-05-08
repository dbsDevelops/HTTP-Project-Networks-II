//Sound JS
//Path: js\sound.js

let isSoundBGTone = true;
let toneIterator = 0;

var sndTone1 = new Audio("./sounds/fastinvader1.wav");
sndTone1.volume = 0.3;
var sndTone2 = new Audio("./sounds/fastinvader2.wav");
sndTone2.volume = 0.3;
var sndTone3 = new Audio("./sounds/fastinvader3.wav");
sndTone3.volume = 0.3;
var sndTone4 = new Audio("./sounds/fastinvader4.wav");
sndTone4.volume = 0.3;

var sndEnemyKilled = new Audio("./sounds/invaderkilled.wav");
sndEnemyKilled.volume = 0.05;

var sndShoot = new Audio("./sounds/shoot.wav");
sndShoot.volume = 0.1;

var sndAmongUsSaucer = new Audio("./sounds/amongUsImpostor.mp3");
sndAmongUsSaucer.volume = 0.05;

var sndSaucer = new Audio("./sounds/ufo_lowpitch.wav");
sndSaucer.volume = 0.1;

class sound {
	constructor(src) {
		this.sound = document.createElement("audio");
		this.sound.src = src;
		this.sound.setAttribute("preload", "auto");
		this.sound.setAttribute("controls", "none");
		this.sound.style.display = "none";
		document.body.appendChild(this.sound);
	}
	play() {
		this.sound.play();
	}
	stop() {
		this.sound.pause();
	}
	volume(vol) {
		this.sound.volume = vol;
	}
}

