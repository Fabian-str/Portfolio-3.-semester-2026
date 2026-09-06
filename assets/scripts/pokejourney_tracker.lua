local PARTY_COUNT_ADDRESS = 0xD163
local PARTY_SPECIES_ADDRESS = 0xD164
local CURRENT_MAP_ADDRESS = 0xD35E

local frameCounter = 0
local lastState = ""

local function readParty()
    local count = emu:read8(PARTY_COUNT_ADDRESS)
    local species = {}

    for i = 0, count - 1 do
        local speciesId = emu:read8(PARTY_SPECIES_ADDRESS + i)
        table.insert(species, speciesId)
    end

    return count, species
end

local function readCurrentMap()
    return emu:read8(CURRENT_MAP_ADDRESS)
end

local function speciesToString(species)
    local result = ""

    for i, id in ipairs(species) do
        result = result .. id

        if i < #species then
            result = result .. ", "
        end
    end

    return result
end

local function buildStateString(mapId, partyCount, species)
    return
        "Map ID: " .. mapId ..
        " | Party count: " .. partyCount ..
        " | Species IDs: " .. speciesToString(species)
end

callbacks:add("frame", function()
    frameCounter = frameCounter + 1

    -- Check roughly twice per second
    if frameCounter % 30 ~= 0 then
        return
    end

    local mapId = readCurrentMap()
    local partyCount, species = readParty()

    local currentState = buildStateString(
        mapId,
        partyCount,
        species
    )

    if currentState ~= lastState then
        console:log(currentState)
        lastState = currentState
    end
end)