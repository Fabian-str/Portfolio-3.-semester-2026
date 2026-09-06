local PARTY_COUNT_ADDRESS = 0xD163
local PARTY_SPECIES_ADDRESS = 0xD164

local frameCounter = 0
local lastParty = ""

local function readParty()
    local count = emu:read8(PARTY_COUNT_ADDRESS)
    local species = {}

    for i = 0, count - 1 do
        local speciesId = emu:read8(PARTY_SPECIES_ADDRESS + i)
        table.insert(species, speciesId)
    end

    return count, species
end

local function partyToString(count, species)
    local result = "Count: " .. count .. " | Species IDs: "

    for i, id in ipairs(species) do
        result = result .. id

        if i < #species then
            result = result .. ", "
        end
    end

    return result
end

callbacks:add("frame", function()
    frameCounter = frameCounter + 1

    if frameCounter % 30 ~= 0 then
        return
    end

    local count, species = readParty()
    local currentParty = partyToString(count, species)

    if currentParty ~= lastParty then
        console:log(currentParty)
        lastParty = currentParty
    end
end)