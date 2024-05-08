class CoverFragment {
    constructor(x, y, active) {
        this.x = x;
        this.y = y;
        this.width = 5;
        this.height = 5;
        this.active = active;
    }

    render() {
        if (this.active) {
            ctx.fillStyle = "#00ff00";
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    }
}

class Cover {
    constructor() {
        this.coverFragments = [];
    }

    render() {
        for (let i = 0; i < this.coverFragments.length; i++) {
            for (let j = 0; j < this.coverFragments[i].length; j++) {
                if (this.coverFragments[i][j] != null)
                this.coverFragments[i][j].render();
            }
        }
    }
}


function createCovers(coversArray) {
    coversArray.push(new Cover());
    coversArray.push(new Cover());
    coversArray.push(new Cover());
    coversArray.push(new Cover());

    var square = 5;
    var posy = 200;
    var posx = canvas.width / 8;
    coversArray[0].coverFragments = createCoverFragment(posx, posy, square);
    var posx = canvas.width / 3;
    coversArray[1].coverFragments = createCoverFragment(posx, posy, square);
    var posx = 2 * canvas.width / 3 - square * 18;
    coversArray[2].coverFragments = createCoverFragment(posx, posy, square);
    var posx = 7 * canvas.width / 8 - square * 18;
    coversArray[3].coverFragments = createCoverFragment(posx, posy, square);
}

function createCoverFragment(posx, posy, square) {
    var fragmentMatrix = [];
    fragmentMatrix = createMatrix(18, 13);

    for (let i = 0; i < 10; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square)+ square*4, canvas.height - posy, true), i, 1);

    }
    for (let i = 0; i < 12; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square)+ square*3, canvas.height - posy + square, true), i, 2);
    }
    
    for (let i = 0; i < 14; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square)+ square*2, canvas.height - posy + square*2, true), i, 3);
    }
    for (let i = 0; i < 16; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square)+ square, canvas.height - posy + square*3, true), i, 4);
    }
    for (let i = 0; i < 18; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*4, true), i, 5);    
    }
    for (let i = 0; i < 18; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*5, true), i, 6);
    }
    for (let i = 0; i < 18; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*6, true), i, 7);
    }
    for (let i = 0; i < 18; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*7, true), i, 8);
    }
    for (let i = 0; i < 18; i++) {
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*8, true), i, 9);
    }
    for (let i = 0; i < 18; i++) {
        if(i != 7 && i != 8 && i != 9 && i != 10)
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*9, true), i, 10);
    }
    for (let i = 0; i < 18; i++) {
        if(i!=6 && i != 7 && i != 8 && i != 9 && i != 10 && i != 11)
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*10, true), i, 11);
    }
    for (let i = 0; i < 18; i++) {
        if(i!=5 && i!=6 && i != 7 && i != 8 && i != 9 && i != 10 && i != 11 && i != 12)
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*11, true), i, 12);
    }
    for (let i = 0; i < 18; i++) {
        if(i!=5 && i!=6 && i != 7 && i != 8 && i != 9 && i != 10 && i != 11 && i != 12)
        matrixInsertObjectHorizontaly(fragmentMatrix, new CoverFragment((posx + i * square), canvas.height - posy + square*12, true), i, 13);
    }


    return fragmentMatrix;
}