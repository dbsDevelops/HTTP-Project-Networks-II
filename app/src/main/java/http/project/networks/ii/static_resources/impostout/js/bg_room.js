class bg_room {
    constructor(name, sprite, posX, posY, width, height) {
        
        //Custom values
        this.name = name;
        this.image = new Image();
        this.image.src = sprite;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        
      }
    
      update(dt){
      }
      
      render(){
        ctx.drawImage(this.image, this.posX, this.posY, this.width, this.height);
      }
    
}

function renderPlaces(){
  if(AdminSupport) AdminSupport.render();
	if(ElectricalSupport) ElectricalSupport.render();
	if(MedbaySupport) MedbaySupport.render();
	if(ShieldsSupport) ShieldsSupport.render();
	
	if(AdminRoom) AdminRoom.render();
	if(ElectricalRoom) ElectricalRoom.render();
	if(MedbayRoom) MedbayRoom.render();
	if(Shields) Shields.render();

	if(AdminCam) AdminCam.render();
	if(ElectricalCam) ElectricalCam.render();
	if(MedbayCam) MedbayCam.render();
	if(ShieldsCam) ShieldsCam.render();

	ventElectric.render();
	ventMedBay.render();
	ventShields.render();
	ventAdmin.render();
}