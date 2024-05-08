function collision(first, second) {
    return !(first.posX > second.posX + second.width ||
        first.posX + first.width < second.posX ||
        first.posY > second.posY + second.height ||
        first.posY + first.height < second.posY);
}

class ventCollision {
    constructor(posX, posY, width, height) {
        this.posX = posX + 40;
        this.posY = posY;
        this.width = width - 80;
        this.height = height;
    }
}

function checkCollisionBetweenCrewArrayAndVent(crewArray, vent) {
    var arraylength = 0;
    var ventColl = new ventCollision(vent.posX, vent.posY, vent.width, vent.height);
    arraylength = crewArray.length;
    if (arraylength > 0) {
        for (let i = 0; i < arraylength; i++) {
            if (collision(crewArray[i], ventColl) && vent.ventOpen) {
                deathAnimation.push(new CrewBodyDeath(crewArray[i].color, crewArray[i].posX, crewArray[i].posY));
                crewArray.splice(i, 1); 
                setTimeout(function(){ vent.VentReset(); }, 1500);
                break;
            }
        }
    }
    
}
