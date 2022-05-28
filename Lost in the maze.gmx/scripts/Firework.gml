var color=argument0;
var xC=argument1;
var yC=argument2;
var maxSpeed=argument3;

sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=0;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=45;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=90;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=135;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=180;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=225;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=270;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=315;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=337.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=22.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=67.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=112.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=157.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=202.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=247.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}
sp=instance_create(xC,yC,obj_skin_fireworks_head);
with(sp){direction=292.5;image_xscale=0.5;image_yscale=0.5;image_alpha=0.5;}

obj_skin_fireworks_head.image_index=color;
obj_skin_fireworks_head.maxSpeed=maxSpeed;
