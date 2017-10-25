dofile "system/scripts/enables.lua"

this:glEnable(GL_BLEND)
this:glShadeModel(GL_SMOOTH)
this:glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

