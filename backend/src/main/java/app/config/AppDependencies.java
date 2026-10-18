package app.config;

import app.adapters.GenOneAdapter;
import app.api.AuthController;
import app.api.GameStateController;
import app.api.PlaythroughController;
import app.api.PokemonController;
import app.dao.PlaythroughDAO;
import app.dao.PlaythroughDAOImpl;
import app.dao.UserDAO;
import app.dao.UserDAOImpl;
import app.security.AuthHandler;
import app.security.RoleHandler;
import app.services.AuthService;
import app.services.GameStateWebSocketService;
import app.services.JwtService;
import app.services.PasswordService;
import app.services.PokemonApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

public class AppDependencies {

    public final PlaythroughController playthroughController;
    public final PokemonController pokemonController;
    public final AuthController authController;
    public final GameStateController gameStateController;

    public final AuthHandler authHandler;
    public final RoleHandler roleHandler;

    public final GameStateWebSocketService gameStateWebSocketService;

    public AppDependencies() {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        PlaythroughDAO playthroughDAO = new PlaythroughDAOImpl(emf);

        UserDAO userDAO = new UserDAOImpl(emf);

        PokemonApiService pokemonApiService = new PokemonApiService();

        PasswordService passwordService = new PasswordService();

        AuthService authService = new AuthService(userDAO, passwordService);

        JwtService jwtService = new JwtService();

        GenOneAdapter genOneAdapter = new GenOneAdapter();

        gameStateWebSocketService = new GameStateWebSocketService(new ObjectMapper());

        playthroughController = new PlaythroughController(playthroughDAO);

        pokemonController = new PokemonController(pokemonApiService);

        authController = new AuthController(authService, jwtService);

        gameStateController = new GameStateController(genOneAdapter, gameStateWebSocketService);

        authHandler = new AuthHandler(jwtService);

        roleHandler = new RoleHandler();
    }
}