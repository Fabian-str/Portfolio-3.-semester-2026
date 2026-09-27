---
title: "Week 5 – REST API with Javalin"
date: 2026-09-27
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I added the first REST API endpoints to PokéJourney using Javalin.

I created an endpoint for retrieving Pokémon data and added validation and HTTP status handling for invalid IDs and missing Pokémon.

I also implemented full CRUD functionality for playthroughs using `GET`, `POST`, `PUT` and `DELETE`, connected directly to the existing DAO layer and PostgreSQL database.

During the implementation I also configured Jackson to correctly serialize `LocalDateTime` values and added validation for incoming playthrough data.

Finally, I created an `API.md` file and a `requests.http` file to document and manually test the available endpoints.