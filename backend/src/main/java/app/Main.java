package app;

import app.api.PlaythroughController;
import app.api.PokemonController;
import app.config.HibernateConfig;
import app.dao.PlaythroughDAO;
import app.dao.PlaythroughDAOImpl;
import app.services.PokemonApiService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        // Hibernate / database
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        PlaythroughDAO playthroughDAO = new PlaythroughDAOImpl(emf);

        // Services
        PokemonApiService pokemonApiService = new PokemonApiService();

        // Controllers
        PlaythroughController playthroughController = new PlaythroughController(playthroughDAO);

        PokemonController pokemonController = new PokemonController(pokemonApiService);

        // Jackson configuration
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Javalin
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(
                new JavalinJackson().updateMapper(mapper -> {
                    mapper.registerModule(new JavaTimeModule());
                    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                })
            );
        }).start(7071);

        // Pokémon routes
        app.get(
            "/api/pokemon/{id}",
            pokemonController::getPokemon
        );

        // Playthrough routes
        app.get(
            "/api/playthroughs",
            playthroughController::getAll
        );

        app.get(
            "/api/playthroughs/{id}",
            playthroughController::getById
        );

        app.post(
            "/api/playthroughs",
            playthroughController::create
        );

        app.put(
            "/api/playthroughs/{id}",
            playthroughController::update
        );

        app.delete(
            "/api/playthroughs/{id}",
            playthroughController::delete
        );
    }
}