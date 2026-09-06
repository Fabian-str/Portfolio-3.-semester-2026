---
title: "Week 2 - JPA Relations"
date: 2026-09-05
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I added `PokemonInstance` and connected it to `Playthrough` using a unidirectional `@ManyToOne` relationship.

Each Pokémon is stored as an individual instance with information such as gender, shiny status, nickname and whether it is currently owned. This makes it possible to count male, female and shiny Pokémon separately.

I also added a `PokemonInstanceDAO` with CRUD operations and several JPQL queries for filtering and counting Pokémon.

I chose not to use cascade types yet and kept the relationship unidirectional to avoid unnecessary complexity.

I also added JUnit tests for the DAO layer, and all tests are currently passing.


I also created the domain model for the project. I did not start working on it in week 1 because I find it difficult to create useful diagrams before I have a decent overview of the project and its structure.

This also means that the diagram is not yet final. It currently shows both what has already been implemented and some of the features and relationships that are planned for the immediate future (next weeks work).