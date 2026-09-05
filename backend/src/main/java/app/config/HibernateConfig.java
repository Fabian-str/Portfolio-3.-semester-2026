package app.config;

import app.entities.Playthrough;
import app.entities.PokemonInstance;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateConfig {

    private static EntityManagerFactory emf;

    private HibernateConfig() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = buildEntityManagerFactory();
        }

        return emf;
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        Properties properties = loadProperties();

        String dbName = properties.getProperty("DB_NAME");
        String dbUsername = properties.getProperty("DB_USERNAME");
        String dbPassword = properties.getProperty("DB_PASSWORD");

        Configuration configuration = new Configuration();

        configuration.setProperty(
                "hibernate.connection.url",
                "jdbc:postgresql://localhost:5432/" + dbName
        );

        configuration.setProperty(
                "hibernate.connection.username",
                dbUsername
        );

        configuration.setProperty(
                "hibernate.connection.password",
                dbPassword
        );

        configuration.setProperty(
                "hibernate.connection.driver_class",
                "org.postgresql.Driver"
        );

        configuration.setProperty(
                "hibernate.hbm2ddl.auto",
                "update"
        );

        configuration.setProperty(
                "hibernate.show_sql",
                "true"
        );

        configuration.addAnnotatedClass(Playthrough.class);
        configuration.addAnnotatedClass(PokemonInstance.class);

        return configuration.buildSessionFactory();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream input = HibernateConfig.class
            .getClassLoader()
            .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }

        return properties;
    }
}