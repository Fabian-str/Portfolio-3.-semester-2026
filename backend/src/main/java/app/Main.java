package app;

import app.dto.PokemonDTO;
import app.services.PokemonApiService;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        PokemonApiService pokemonApiService =
                new PokemonApiService();

        List<Integer> ids = List.of(25, 6, 9, 3, 94, 143);

        List<PokemonDTO> pokemon = pokemonApiService.getPokemonBatch(ids);

        pokemon.forEach(p -> System.out.println(p.getId() + " - " + p.getName() + " - " + p.getTypes()));
    }
}