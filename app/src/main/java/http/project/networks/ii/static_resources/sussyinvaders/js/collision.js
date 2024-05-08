//Collision JS
//Path: js\collision.js
//Space invaders collision.js
function collision(first, second) {
    return !(first.x > second.x + second.width ||
        first.x + first.width < second.x ||
        first.y > second.y + second.height ||
        first.y + first.height < second.y);
}

function checkCollisionBetweenBulletsAndAliens(aliensMatrix, bullets) {
    //Check collision between bullets and aliens
    for (let i = 0; i < aliensMatrix.length; i++) {
        for (let j = 0; j < aliensMatrix[i].length; j++) {
            if (aliensMatrix[i][j].active) {
                for (let k = 0; k < bullets.length; k++) {
                    if (collision(aliensMatrix[i][j], bullets[k]) && bullets[k].mode) {
                        aliensMatrix[i][j].active = false;
                        bullets.splice(k, 1);
                        speedgame += 0.01;
                        player.score += aliensMatrix[i][j].scoreWhenDestroyed;
                        alienExplosion.push(new AlienExplosion(aliensMatrix[i][j].x, aliensMatrix[i][j].y));
                        sndEnemyKilled.play();

                    }
                }
            }
        }
    }
}

function checkCollisionBetweenBulletsAndPlayer(player, bullets) {
    //Check collision between bullets and player
    for (let i = 0; i < bullets.length; i++) {
        if (collision(player, bullets[i]) && !bullets[i].mode) {
            bullets.splice(i, 1);
            player.lives--;
            if (player.lives == 0) {
                player.active = false;
            }
        }
    }
}

function checkCollisionBetweenBulletsAndCover(cover, bullets) {
    //Check collision between bullets and cover

    for (let i = 0; i < cover.length; i++) {
        for (let j = 0; j < cover[i].coverFragments.length; j++) {
            for (let k = 0; k < cover[i].coverFragments[j].length; k++) {
                if (cover[i].coverFragments[j][k] != null && cover[i].coverFragments[j][k].active) {
                        for (let l = 0; l < bullets.length; l++) {
                            if (collision(cover[i].coverFragments[j][k], bullets[l])) {
                                destroyNearbyCovers(cover, i, j, k);
                                bullets.splice(l, 1);
                            }
                        }
                    
                }
            }
        }
    }
}

function destroyNearbyCovers(cover, i, x, y) {
    cover[i].coverFragments[x][y].active = false;
    if (x-3 >= 0 && y - 4 >= 0) {
        if (cover[i].coverFragments[x - 3][y - 4] != null) {
            cover[i].coverFragments[x - 3][y - 4].active = false;
        }
    }
    if (x >= 0 && y - 4 >= 0) {
        if (cover[i].coverFragments[x][y - 4] != null) {
            cover[i].coverFragments[x][y - 4].active = false;
        }
    }
    if (x+1 < 18 && y - 4 >= 0) {
        if (cover[i].coverFragments[x+1][y - 4] != null) {
            cover[i].coverFragments[x+1][y - 4].active = false;
        }
    }
    if (x+4 < 18 && y - 4 >= 0) {
        if (cover[i].coverFragments[x+4][y - 4] != null) {
            cover[i].coverFragments[x+4][y - 4].active = false;
        }
    }

    if (x-1 >= 0 && y - 3 >= 0) {
        if (cover[i].coverFragments[x - 1][y - 3] != null) {
            cover[i].coverFragments[x - 1][y - 3].active = false;
        }
    }
    if (x >= 0 && y - 3 >= 0) {
        if (cover[i].coverFragments[x][y - 3] != null) {
            cover[i].coverFragments[x][y - 3].active = false;
        }
    }
    if (x+1 < 18 && y - 3 >= 0) {
        if (cover[i].coverFragments[x+1][y - 3] != null) {
            cover[i].coverFragments[x+1][y - 3].active = false;
        }
    }
    if (x+2 < 18 && y - 3 >= 0) {
        if (cover[i].coverFragments[x+2][y - 3] != null) {
            cover[i].coverFragments[x+2][y - 3].active = false;
        }
    }

    if (x-2 >= 0 && y - 2 >= 0) {
        if (cover[i].coverFragments[x - 2][y - 2] != null) {
            cover[i].coverFragments[x - 2][y - 2].active = false;
        }
    }
    if (x-1 >= 0 && y - 2 >= 0) {
        if (cover[i].coverFragments[x - 1][y - 2] != null) {
            cover[i].coverFragments[x - 1][y - 2].active = false;
        }
    }
    if (x >= 0 && y - 2 >= 0) {
        if (cover[i].coverFragments[x][y - 2] != null) {
            cover[i].coverFragments[x][y - 2].active = false;
        }
    }
    if (x+1 < 18 && y - 2 >= 0) {
        if (cover[i].coverFragments[x+1][y - 2] != null) {
            cover[i].coverFragments[x+1][y - 2].active = false;
        }
    }
    if (x+2 < 18 && y - 2 >= 0) {
        if (cover[i].coverFragments[x+2][y - 2] != null) {
            cover[i].coverFragments[x+2][y - 2].active = false;
        }
    }
    if (x+3 < 18 && y - 2 >= 0) {
        if (cover[i].coverFragments[x+3][y - 2] != null) {
            cover[i].coverFragments[x+3][y - 2].active = false;
        }
    }

    if (x-3 >= 0 && y - 1 >= 0) {
        if (cover[i].coverFragments[x - 3][y - 1] != null) {
            cover[i].coverFragments[x - 3][y - 1].active = false;
        }
    }
    if (x-1 >= 0 && y - 1 >= 0) {
        if (cover[i].coverFragments[x - 1][y - 1] != null) {
            cover[i].coverFragments[x - 1][y - 1].active = false;
        }
    }
    if (x >= 0 && y - 3 >= 0) {
        if (cover[i].coverFragments[x][y - 1] != null) {
            cover[i].coverFragments[x][y - 1].active = false;
        }
    }
    if (x+1 < 18 && y - 3 >= 0) {
        if (cover[i].coverFragments[x+1][y - 1] != null) {
            cover[i].coverFragments[x+1][y - 1].active = false;
        }
    }
    if (x+2 < 18 && y - 3 >= 0) {
        if (cover[i].coverFragments[x+2][y - 1] != null) {
            cover[i].coverFragments[x+2][y - 1].active = false;
        }
    }

    if (x-1 >= 0 && y >= 0) {
        if (cover[i].coverFragments[x - 1][y] != null) {
            cover[i].coverFragments[x - 1][y].active = false;
        }
    }
    if (x >= 0 && y >= 0) {
        if (cover[i].coverFragments[x][y] != null) {
            cover[i].coverFragments[x][y].active = false;
        }
    }
    if (x+1 < 18 && y >= 0) {
        if (cover[i].coverFragments[x+1][y] != null) {
            cover[i].coverFragments[x+1][y].active = false;
        }
    }
    if (x+2 < 18 && y>= 0) {
        if (cover[i].coverFragments[x+2][y] != null) {
            cover[i].coverFragments[x+2][y].active = false;
        }
    }
    if (x+4 < 18 && y>= 0) {
        if (cover[i].coverFragments[x+4][y] != null) {
            cover[i].coverFragments[x+4][y].active = false;
        }
    }

    if (x-2 >= 0 && y + 1 < 13) {
        if (cover[i].coverFragments[x - 2][y + 1] != null) {
            cover[i].coverFragments[x - 2][y + 1].active = false;
        }
    }
    if (x-1 >= 0 && y + 1 < 13) {
        if (cover[i].coverFragments[x - 1][y + 1] != null) {
            cover[i].coverFragments[x - 1][y + 1].active = false;
        }
    }
    if (x >= 0 && y + 1 < 13) {
        if (cover[i].coverFragments[x][y + 1] != null) {
            cover[i].coverFragments[x][y + 1].active = false;
        }
    }
    if (x+1 < 18 && y + 1 < 13) {
        if (cover[i].coverFragments[x+1][y + 1] != null) {
            cover[i].coverFragments[x+1][y + 1].active = false;
        }
    }
    if (x+2 < 18 && y + 1 < 13) {
        if (cover[i].coverFragments[x+2][y + 1] != null) {
            cover[i].coverFragments[x+2][y + 1].active = false;
        }
    }
    
    if (x-1 >= 0 && y + 2 < 13) {
        if (cover[i].coverFragments[x - 1][y + 2] != null) {
            cover[i].coverFragments[x - 1][y + 2].active = false;
        }
    }
    if (x >= 0 && y + 2 < 13) {
        if (cover[i].coverFragments[x][y + 2] != null) {
            cover[i].coverFragments[x][y + 2].active = false;
        }
    }
    if (x+1 < 18 && y + 2 < 13) {
        if (cover[i].coverFragments[x+1][y + 2] != null) {
            cover[i].coverFragments[x+1][y + 2].active = false;
        }
    }
    if (x+2 < 18 && y + 2 < 13) {
        if (cover[i].coverFragments[x+2][y + 2] != null) {
            cover[i].coverFragments[x+2][y + 2].active = false;
        }
    }
    if (x+3 < 18 && y + 2 < 13) {
        if (cover[i].coverFragments[x+3][y + 2] != null) {
            cover[i].coverFragments[x+3][y + 2].active = false;
        }
    }

    if (x-3 >= 0 && y + 3 < 13) {
        if (cover[i].coverFragments[x - 3][y + 3] != null) {
            cover[i].coverFragments[x - 3][y + 3].active = false;
        }
    }
    if (x >= 0 && y + 3 < 13) {
        if (cover[i].coverFragments[x][y + 3] != null) {
            cover[i].coverFragments[x][y + 3].active = false;
        }
    }
    if (x+1 < 18 && y + 3 < 13) {
        if (cover[i].coverFragments[x+1][y + 3] != null) {
            cover[i].coverFragments[x+1][y + 3].active = false;
        }
    }
    if (x+4 < 18 && y + 3 < 13) {
        if (cover[i].coverFragments[x+4][y + 3] != null) {
            cover[i].coverFragments[x+4][y + 3].active = false;
        }
    }
}

function checkCollisionBetweenPlayerAndAliens(player, aliensMatrix) {
    //Check collision between player and aliens
    for (let i = 0; i < aliensMatrix.length; i++) {
        for (let j = 0; j < aliensMatrix[i].length; j++) {
            if (collision(player, aliensMatrix[i][j])) {
                player.lives = 0;
            }
        }
    }
}

function checkCollisionBetweenAliensAndCover(cover, aliensMatrix) {
    //Check collision between aliens and cover
    for (let i = 0; i < cover.length; i++) {
        for (let j = 0; j < cover[i].coverFragments.length; j++) {
            for (let k = 0; k < cover[i].coverFragments[j].length; k++) {
                if (cover[i].coverFragments[j][k] != null) {
                    if (cover[i].coverFragments[j][k].active) {
                        for (let l = 0; l < aliensMatrix.length; l++) {
                            for (let m = 0; m < aliensMatrix[l].length; m++) {
                                if (collision(cover[i].coverFragments[j][k], aliensMatrix[l][m])) {
                                    cover[i].coverFragments[j][k].active = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

function checkCollisionBetweenSaucerAndBullet(saucer, bullets) {
    //Check collision between saucer and bullet
    if(saucer.active){
        for (let i = 0; i < bullets.length; i++) {
            if (collision(saucer, bullets[i])) {
                bullets.splice(i, 1);
                saucer.active = false;
                player.score += saucer.score;
                alienExplosion.push(new SaucerExplosion(saucer.x, saucer.y));
                console.log ("Saucer destroyed: " + saucer.score + " points");
                sndEnemyKilled.play();
            }
        }
    }
}