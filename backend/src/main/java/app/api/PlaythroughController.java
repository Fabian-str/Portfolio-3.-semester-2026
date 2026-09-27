package app.api;

import app.dao.PlaythroughDAO;
import app.dto.CreatePlaythroughDTO;
import app.dto.UpdatePlaythroughDTO;
import app.entities.Playthrough;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class PlaythroughController {

    private final PlaythroughDAO playthroughDAO;

    public PlaythroughController(PlaythroughDAO playthroughDAO) {
        this.playthroughDAO = playthroughDAO;
    }

    public void getAll(Context ctx) {
        List<Playthrough> playthroughs = playthroughDAO.findAll();

        ctx.json(playthroughs);
    }

    public void getById(Context ctx) {

        String idParam = ctx.pathParam("id");

        long id;

        try {id = Long.parseLong(idParam);

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of("error", "Playthrough ID must be a number"));
            return;
        }

        Playthrough playthrough = playthroughDAO.findById(id);

        if (playthrough == null) {
            ctx.status(404);
            ctx.json(Map.of("error", "Playthrough not found"));
            return;
        }

        ctx.json(playthrough);
    }

    public void create(Context ctx) {

        CreatePlaythroughDTO request = ctx.bodyAsClass(CreatePlaythroughDTO.class);

        if (request.getName() == null || request.getName().isBlank()) {
            ctx.status(400);
            ctx.json(Map.of("error", "Playthrough name is required"));
            return;
        }

        if (request.getGame() == null) {
            ctx.status(400);
            ctx.json(Map.of("error", "Game is required"));
            return;
        }

        Playthrough playthrough = Playthrough.builder()
            .name(request.getName())
            .game(request.getGame())
            .build();

        Playthrough created = playthroughDAO.create(playthrough);

        ctx.status(201);
        ctx.json(created);
    }

    public void update(Context ctx) {

        long id;

        try {id = Long.parseLong(ctx.pathParam("id"));

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of("error", "Playthrough ID must be a number"));
            return;
        }

        Playthrough existing = playthroughDAO.findById(id);

        if (existing == null) {
            ctx.status(404);
            ctx.json(Map.of("error", "Playthrough not found"));
            return;
        }

        UpdatePlaythroughDTO request = ctx.bodyAsClass(UpdatePlaythroughDTO.class);

        if (request.getName() == null || request.getName().isBlank()) {
            ctx.status(400);
            ctx.json(Map.of("error", "Playthrough name is required"));
            return;
        }

        if (request.getGame() == null) {
            ctx.status(400);
            ctx.json(Map.of("error", "Game is required"));
            return;
        }

        existing.setName(request.getName());
        existing.setGame(request.getGame());

        Playthrough updated = playthroughDAO.update(existing);

        ctx.json(updated);
    }

    public void delete(Context ctx) {

        long id;

        try {
            id = Long.parseLong(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(Map.of("error", "Playthrough ID must be a number"));
            return;
        }

        Playthrough existing = playthroughDAO.findById(id);

        if (existing == null) {
            ctx.status(404);
            ctx.json(Map.of("error", "Playthrough not found"));
            return;
        }

        playthroughDAO.delete(id);

        ctx.status(204);
    }
}