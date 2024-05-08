class paralax {
  constructor(image, posX, posY, speed) {
    this.posX = posX;
    this.posY = posY;
    this.width = 600;
    this.height = 600;
    this.image = new Image();
    this.image.src = image;
    this.angle = 0;
    this.speed = speed;
  }
  render() {
    
    ctx.save();
    //rotate the image from the center
    ctx.translate(this.posX + this.width / 2, this.posY + this.height / 2);
    ctx.rotate(this.angle);
    ctx.translate(-(this.posX + this.width / 2), -(this.posY + this.height / 2));



    ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
    ctx.restore();
  }

    update(dt) {
        //rotate the image
        this.angle +=this.speed*dt;
    }
}