local PARTY_COUNT_ADDRESS = 0xD163
local lastPartyCount = -1
local frameCounter = 0

callbacks:add("frame", function()
    frameCounter = frameCounter + 1

    if frameCounter % 30 ~= 0 then
        return
    end

    local partyCount = emu:read8(PARTY_COUNT_ADDRESS)

    if partyCount ~= lastPartyCount then
        console:log("Party count changed: " .. partyCount)
        lastPartyCount = partyCount
    end
end)