package app;

import app.config.HibernateConfig;
import app.daos.PlaythroughDAO;
import app.daos.PlaythroughDAOImpl;
import app.daos.PokemonInstanceDAO;
import app.daos.PokemonInstanceDAOImpl;
import app.entities.Gender;
import app.entities.Playthrough;
import app.entities.PokemonGame;
import app.entities.PokemonInstance;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                HibernateConfig.getEntityManagerFactory();

        PlaythroughDAO playthroughDAO =
                new PlaythroughDAOImpl(emf);

        PokemonInstanceDAO pokemonDAO =
                new PokemonInstanceDAOImpl(emf);

        Playthrough playthrough = Playthrough.builder()
                .name("My Pokémon Red Run")
                .game(PokemonGame.RED)
                .build();

        playthroughDAO.create(playthrough);

        PokemonInstance pikachuMale = PokemonInstance.builder()
                .pokemonId(25)
                .nickname("Sparky")
                .gender(Gender.MALE)
                .shiny(false)
                .currentlyOwned(true)
                .playthrough(playthrough)
                .build();

        PokemonInstance pikachuFemale = PokemonInstance.builder()
                .pokemonId(25)
                .gender(Gender.FEMALE)
                .shiny(false)
                .currentlyOwned(true)
                .playthrough(playthrough)
                .build();

        PokemonInstance shinyPikachuFemale = PokemonInstance.builder()
                .pokemonId(25)
                .gender(Gender.FEMALE)
                .shiny(true)
                .currentlyOwned(true)
                .playthrough(playthrough)
                .build();

        pokemonDAO.create(pikachuMale);
        pokemonDAO.create(pikachuFemale);
        pokemonDAO.create(shinyPikachuFemale);

        System.out.println(
                "Total Pikachu: " +
                        pokemonDAO.countBySpecies(playthrough.getId(), 25)
        );

        System.out.println(
                "Male Pikachu: " +
                        pokemonDAO.countBySpeciesAndGender(
                                playthrough.getId(),
                                25,
                                Gender.MALE
                        )
        );

        System.out.println(
                "Female Pikachu: " +
                        pokemonDAO.countBySpeciesAndGender(
                                playthrough.getId(),
                                25,
                                Gender.FEMALE
                        )
        );

        System.out.println(
                "Shiny Pokémon: " +
                        pokemonDAO
                                .findShinyByPlaythroughId(playthrough.getId())
                                .size()
        );

        emf.close();

        System.out.println(
                "Female shiny Pikachu: " +
                        pokemonDAO.countBySpeciesGenderAndShiny(
                                playthrough.getId(),
                                25,
                                Gender.FEMALE,
                                true
                        )
        );
    }
}