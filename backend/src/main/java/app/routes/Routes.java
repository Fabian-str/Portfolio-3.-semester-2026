package app.routes;

import app.config.AppDependencies;
import app.entities.Role;
import io.javalin.Javalin;

public class Routes {

    public static void register(Javalin app, AppDependencies dependencies) {

        // Pokémon
        app.get("/api/pokemon/{id}", dependencies.pokemonController::getPokemon);

        // Authentication
        app.post("/api/auth/register", dependencies.authController::register);

        app.post("/api/auth/login", dependencies.authController::login);

        // Playthrough authentication
        app.before("/api/playthroughs", dependencies.authHandler::handle);

        app.before("/api/playthroughs/{id}", dependencies.authHandler::handle);

        // Playthroughs
        app.get("/api/playthroughs", dependencies.playthroughController::getAll);

        app.get("/api/playthroughs/{id}", dependencies.playthroughController::getById);

        app.post("/api/playthroughs", dependencies.playthroughController::create);

        app.put("/api/playthroughs/{id}", dependencies.playthroughController::update);

        app.delete("/api/playthroughs/{id}", dependencies.playthroughController::delete);

        // Emulator
        app.post("/api/game-state", dependencies.gameStateController::updateGameState);

        // WebSocket
        app.ws("/ws", ws -> {

            ws.onConnect(ctx -> {dependencies.gameStateWebSocketService.addClient(ctx);
                System.out.println("WebSocket connected");
            });

            ws.onMessage(ctx -> {if ("ping".equals(ctx.message())) {
                    ctx.send("pong");
                }
            });

            ws.onClose(ctx -> {dependencies.gameStateWebSocketService.removeClient(ctx);
                System.out.println("WebSocket disconnected");
            });
        });

        // Admin
        app.get("/api/admin/test", ctx -> {

            dependencies.authHandler.handle(ctx);

            dependencies.roleHandler.requireRole(ctx, Role.ADMIN);

            ctx.status(200).result("Admin access granted");
        });
    }
}