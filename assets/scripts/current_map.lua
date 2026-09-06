local CURRENT_MAP_ADDRESS = 0xD35E

local lastMap = -1
local frameCounter = 0

callbacks:add("frame", function()
    frameCounter = frameCounter + 1

    -- Tjek kun cirka 2 gange i sekundet
    if frameCounter % 30 ~= 0 then
        return
    end

    local currentMap = emu:read8(CURRENT_MAP_ADDRESS)

    if currentMap ~= lastMap then
        console:log("Current map ID: " .. currentMap)
        lastMap = currentMap
    end
end)