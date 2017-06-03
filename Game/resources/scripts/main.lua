dofile "system/scripts/keyboard_keys.lua"

speed = fps:getDelta()*0.2f

xSpeed = 0
ySpeed = 0
rot=0
walking=false
if texX == nil then
   texX=0
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

texX= texX + 0.01

if texX>4 then
	texX=0
end

this:getComponent("animator"):setFrameY(texY)
if walking then
	this:getComponent("animator"):setFrameX(texX)
else
	this:getComponent("animator"):setFrameX(0)
end

this:getComponent("transform"):Move(xSpeed,ySpeed)
this:getComponent("transform"):Rotate(rot)

--this:getComponent("transform"):Rotate(1)