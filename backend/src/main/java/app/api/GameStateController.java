package app.api;

import app.adapters.GenOneAdapter;
import app.dto.GameStateDTO;
import app.dto.ResolvedGameStateDTO;
import app.services.GameStateWebSocketService;
import io.javalin.http.Context;

import java.util.List;

public class GameStateController {

    private final GenOneAdapter genOneAdapter;
    private final GameStateWebSocketService webSocketService;

    public GameStateController(
            GenOneAdapter genOneAdapter,
            GameStateWebSocketService webSocketService
    ) {
        this.genOneAdapter = genOneAdapter;
        this.webSocketService = webSocketService;
    }

    public void updateGameState(Context ctx) {

        GameStateDTO gameState = ctx.bodyAsClass(GameStateDTO.class);

        List<Integer> pokemonIds = gameState.getSpeciesIds()
            .stream()
            .map(genOneAdapter::toPokedexId)
            .toList();

        ResolvedGameStateDTO resolvedState = new ResolvedGameStateDTO(
            gameState.getMapId(), genOneAdapter.toLocationKey(gameState.getMapId()),
            gameState.getPartyCount(), pokemonIds
        );

        webSocketService.broadcast(resolvedState);

        ctx.status(204);
    }
}