---
title: "Week 2 - JPA Relations"
date: 2026-09-05
draft: false
featureimage: "images/pokemon_1.webp"
---

## What I added

This week I continued working on PokéJourney by adding a second entity, `PokemonInstance`.

The purpose of `PokemonInstance` is to represent an individual Pokémon that belongs to a specific playthrough. This makes it possible to track more than just whether a species has been caught or not.

Each Pokémon instance can currently store:

- PokéAPI species ID
- nickname
- gender
- shiny status
- whether the Pokémon is currently owned
- when it was obtained
- which playthrough it belongs to

I also created the domain model for the project. I did not start working on it in week 1 because I find it difficult to create useful diagrams before I have a decent overview of the project and its structure.

This also means that the diagram is not yet final. It currently shows both what has already been implemented and some of the features and relationships that are planned for the immediate future (next weeks work).