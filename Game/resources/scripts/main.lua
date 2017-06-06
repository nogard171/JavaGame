dofile "system/scripts/keyboard_keys.lua"

speed = fps:getDelta()*0.2f

xSpeed = 0
ySpeed = 0
rot=0
walking=false
if texX == nil then
   texX=0
end
texSpeed=0.01
if keyboard:isKeyDown(SHIFT) then
	speed=speed*2;
	texSpeed=0.02
end

if keyboard:isKeyDown(D) then
	xSpeed = speed
	texY=2
	walking=true
end

if keyboard:isKeyDown(A) then
	xSpeed = -speed
	texY=3
	walking=true
end

if keyboard:isKeyDown(W) then
	ySpeed = speed
	texY=1
	walking=true
end

if keyboard:isKeyDown(S) then
	ySpeed = -speed
	texY=0
	walking=true
end


texX= texX + texSpeed

if texX>4 then
	texX=0
end

if not walking then
	this:getComponent("audio"):stop()
	texX=0
else	
	this:getComponent("audio"):play()
end

this:getComponent("animator"):setFrameY(texY)
this:getComponent("animator"):setFrameX(texX)
this:getComponent("transform"):Move(xSpeed,ySpeed)
this:getComponent("transform"):Rotate(rot)