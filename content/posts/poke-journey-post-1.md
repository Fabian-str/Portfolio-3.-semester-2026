---
title: "Week 1 - Starting PokéJourney"
date: 2026-08-31
draft: false
featureimage: "images/pokemon_1.webp"
---

## What I added

This week I started my portfolio backend project, PokéJourney.

The goal of the project is to build a Pokémon playthrough and collection tracker. The backend will eventually be able to track caught Pokémon, playthrough progress and Pokémon data from PokéAPI. A later experimental goal is to investigate live tracking from Pokémon games running in an emulator.

For the first version, I kept the scope small and created the first JPA entity: `Playthrough`.

I also implemented basic CRUD operations using a DAO interface and a DAO implementation.

## Technologies used

- Java
- Maven
- JPA
- Hibernate
- PostgreSQL
- Lombok

## Database setup

I connected the project to a local PostgreSQL database and configured Hibernate using an `EntityManagerFactory`.

The `Playthrough` entity currently contains:

- id
- name
- game
- createdAt

## DAO and CRUD

I created a `PlaythroughDAO` interface and a `PlaythroughDAOImpl` implementation.

The DAO currently supports:

- create
- findById
- findAll
- update
- delete

## Challenges

One problem I encountered was that Hibernate did not recognize the `Playthrough` class as an entity.

The error was:

`Unknown entity type 'app.entities.Playthrough'`

The database connection itself was working, but I had forgotten to register the annotated entity class in the Hibernate configuration.

After adding the entity to the Hibernate configuration, Hibernate was able to create the table and the CRUD operations worked correctly.