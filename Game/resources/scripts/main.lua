dofile "system/scripts/keyboard_keys.lua"

speed = fps:getDelta()*0.2f

xSpeed = 0
ySpeed = 0
rot=0

if keyboard:isKeyDown(D) then
	xSpeed = speed
end

if keyboard:isKeyDown(A) then
	xSpeed = -speed
end

if keyboard:isKeyDown(W) then
	ySpeed = speed
end

if keyboard:isKeyDown(S) then
	ySpeed = -speed
end

if keyboard:isKeyDown(E) then
	rot=-0.1
end

if keyboard:isKeyDown(Q) then
	rot=0.1
end

this:getComponent("transform"):Move(xSpeed,ySpeed)
this:getComponent("transform"):Rotate(rot)

--this:getComponent("transform"):Rotate(1)