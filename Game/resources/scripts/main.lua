dofile "system/scripts/keyboard_keys.lua"

speed = fps:getDelta()*0.2f

xSpeed = 0
ySpeed = 0

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


this:getComponent("transform"):Move(xSpeed,ySpeed)

--this:getComponent("transform"):Rotate(1)