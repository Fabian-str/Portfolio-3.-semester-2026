package app.adapters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;

public class GenOneAdapter {

    private final Map<Integer, Integer> speciesMap;
    private final Map<Integer, String> mapMap;

    public GenOneAdapter() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            InputStream speciesStream = getClass().getResourceAsStream("/gen1/species-map.json");

            InputStream mapStream = getClass().getResourceAsStream("/gen1/map-map.json");

            speciesMap = objectMapper.readValue(
                speciesStream,
                new TypeReference<Map<Integer, Integer>>() {}
            );

            mapMap = objectMapper.readValue(
                mapStream,
                new TypeReference<Map<Integer, String>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException("Could not load Gen 1 mappings", e);
        }
    }

    public int toPokedexId(int internalSpeciesId) {
        Integer pokedexId = speciesMap.get(internalSpeciesId);

        if (pokedexId == null) {throw new IllegalArgumentException("Unknown Gen 1 species ID: " + internalSpeciesId);
        }

        return pokedexId;
    }

    public String toLocationKey(int mapId) {

        return mapMap.getOrDefault(mapId, "unknown");
    }
}