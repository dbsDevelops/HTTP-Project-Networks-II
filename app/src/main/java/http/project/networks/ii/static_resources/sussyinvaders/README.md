# Space Invaders
Small Javascript Space Invaders Game, made for the course of "Videojuegos" at the University San Jorge, Zaragoza, Spain. This project was made by [Daniel Sanchez Cebrian](https://danielsc.vercel.app) and [German Aguilar Bobadilla](mailto:alu.115090@usj.es).

/!\ NOW DEPLOYED ON [A WEB](https://sussynvaders.vercel.app) /!\


### Tasks
- [x] **(1 point)** Player moves when keys are pressed and collides with the borders of the game
- [x] **(2.0 point)** You must create an army of enemies similar to the original game.
You should use different images to represent the different enemies (search them on the internet and use the “original” ones).
- [x] **(2.0 point)** Enemies move horizontally until any of the enemies hit the wall, then the whole army goes down a “level”.
- [x] **(1 point )** The enemies should perform an animation when moving as in the original game (two-frame animations where you switch the image to the next one)
- [x] **(1.5 point)** Player can shoot at a controlled rate of fire; it is a cannon, not a machine gun, so there is some reload time after each shoot. Bullets from player can hit one invader and destroy it.
- [x] **(1.5 point)** Invaders in front line also shoot from time to time. Bullets from invader can hit the player and take a life from him. The player will have three lives, and if runs out of lives (or the invaders reach the surface of the earth) will die and the game will restart in the current level
- [x] **(1 point)** In the original game, some coverage was provided to the player through 4 small  buildings that could be destroyed. In the original game they could be destroyed by both (enemies and player) and was partially destroyed with each hit, *can you achieve this?*
- [x] **(1 point)** Difficulty will be introduced by the enemy’s speed and rate of fire and will be based on a control variable. Each time you kill an invader, difficulty will be increased. When you finish one level, next level will start with increased difficulty.
- [x] **(1 point)** **Extra features** present in the original game, ~~audio linked to the speed,~~ ~~flying saucer~~, ~~Hud for score/lives~~.

### Requirements
You will need to follow the folder structure we saw in class, that is a main folder, called *“Space Invaders”* and your name or similar, and inside it:
- An html called “index.html”, in which we will reference our js files.
- A folder called JS which will have all your js files inside.
- A folder called Images which will have all your images inside.
- You are free to implement the number of JS you deem necessary, the only ones that
are 100% required are “game.js” and “player.js”.
- game.js will have the logic of the game and the different instances of the different
gameObjects. It should also have the start(), main(), update() and render() functions.
- player.js will have the logic of the player, the spaceship. It should have the class
Player, with its constructor(), update() and render() functions.
- **Any deviation from any of these points (folder structure and/or the required js) will
be penalized.**
- Tip: Use our breakout game as a starting point.