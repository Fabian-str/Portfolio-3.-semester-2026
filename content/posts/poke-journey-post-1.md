---
title: "Week 1 - Starting PokéJourney"
date: 2026-08-31
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I started working on my semester portfolio project, PokéJourney. The idea behind the project is to create a backend for tracking Pokémon playthroughs and collection progress. The long-term goal is to let a user create playthroughs for different Pokémon games, keep track of individual Pokémon they have obtained, and eventually combine this information with data from PokéAPI. I also want to explore whether the tracker can later read game state directly from supported emulators, although I currently considered this an experimental extension rather than a core requirement.

Because the project is supposed to grow throughout the semester, I deliberately kept the first version very small. I started with a single JPA entity called `Playthrough`. A playthrough currently contains an ID, a name, the Pokémon game being played, and the date and time it was created. I used an enum for the game instead of storing arbitrary strings, which gives the application a fixed set of supported games and avoids inconsistent values in the database.

The project uses Maven, Hibernate/JPA and a local PostgreSQL database. I configured Hibernate through an `EntityManagerFactory` and created a DAO interface and implementation for `Playthrough`. The DAO supports the basic CRUD operations: creating, finding, updating and deleting playthroughs. This also gave me an opportunity to work with transactions and become more familiar with when JPA uses methods such as `persist`, `find`, `merge` and `remove`.

During the setup I ran into a problem where the database connection worked, but Hibernate refused to persist a `Playthrough`. The error said that `Playthrough` was an unknown entity type. The problem turned out to be that I had created and annotated the entity, but had not registered the annotated class in my Hibernate configuration. After adding it, Hibernate was able to create the table and all CRUD operations worked correctly.

This helped clarify the relationship between JPA and Hibernate for me. JPA provides the API and annotations used to describe persistence, while Hibernate is the implementation that actually handles the mapping and database communication in this project. It also made the purpose of the DAO pattern more concrete, because the database operations are now separated from the rest of the application instead of being placed directly in `Main`.

The first version of PokéJourney is intentionally simple, but it gives the project a working persistence layer that can be expanded during the following weeks. The next step is to add individual Pokémon to a playthrough and begin working with JPA relationships.