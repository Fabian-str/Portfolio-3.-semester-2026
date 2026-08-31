package app;

import app.config.HibernateConfig;
import app.daos.PlaythroughDAO;
import app.daos.PlaythroughDAOImpl;
import app.entities.Playthrough;
import app.entities.PokemonGame;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                HibernateConfig.getEntityManagerFactory();

        PlaythroughDAO dao =
                new PlaythroughDAOImpl(emf);

        Playthrough playthrough = Playthrough.builder()
                .name("My Pokémon Red Run")
                .game(PokemonGame.RED)
                .build();

        dao.create(playthrough);

        System.out.println("Created:");
        System.out.println(playthrough.getId());

        System.out.println("Found:");
        System.out.println(
                dao.findById(playthrough.getId()).getName()
        );

        playthrough.setName("Updated Red Run");
        dao.update(playthrough);

        System.out.println("All:");
        dao.findAll().forEach(
                p -> System.out.println(p.getName())
        );

        dao.delete(playthrough.getId());

        emf.close();
    }
}