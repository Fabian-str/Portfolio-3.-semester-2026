package app;

import app.adapters.GenOneAdapter;
import app.api.PlaythroughController;
import app.api.PokemonController;
import app.config.HibernateConfig;
import app.dao.PlaythroughDAO;
import app.dao.PlaythroughDAOImpl;
import app.dto.GameStateDTO;
import app.dto.ResolvedGameStateDTO;
import app.services.GameStateWebSocketService;
import app.services.PokemonApiService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Hibernate / database
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        PlaythroughDAO playthroughDAO = new PlaythroughDAOImpl(emf);

        // Services
        PokemonApiService pokemonApiService = new PokemonApiService();

        GameStateWebSocketService gameStateWebSocketService = new GameStateWebSocketService(new ObjectMapper());

        // Controllers
        PlaythroughController playthroughController = new PlaythroughController(playthroughDAO);

        PokemonController pokemonController = new PokemonController(pokemonApiService);

        // Adapters
        GenOneAdapter genOneAdapter = new GenOneAdapter();

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

        app.ws("/ws", ws -> {

            ws.onConnect(ctx -> {
                gameStateWebSocketService.addClient(ctx);

                System.out.println("WebSocket connected");
            });

            ws.onMessage(ctx -> {
                if ("ping".equals(ctx.message())) {
                    ctx.send("pong");
                }
            });

            ws.onClose(ctx -> {
                gameStateWebSocketService.removeClient(ctx);

                System.out.println("WebSocket disconnected");
            });
        });

        app.post("/api/game-state", ctx -> {

            GameStateDTO gameState = ctx.bodyAsClass(GameStateDTO.class);

            List<Integer> pokemonIds = gameState.getSpeciesIds()
                .stream()
                .map(genOneAdapter::toPokedexId)
                .toList();

            ResolvedGameStateDTO resolvedState = new ResolvedGameStateDTO(
                gameState.getMapId(),
                genOneAdapter.toLocationKey(gameState.getMapId()),
                gameState.getPartyCount(),
                pokemonIds);

            gameStateWebSocketService.broadcast(resolvedState);

            ctx.status(204);
        });
    }
}