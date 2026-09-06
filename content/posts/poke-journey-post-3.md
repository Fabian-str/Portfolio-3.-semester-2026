---
title: "Week 3 - Data Integration"
date: 2026-09-06
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I integrated PokéJourney with PokéAPI using Java `HttpClient`.

The JSON response is converted into Java DTOs using Jackson, and then mapped into a smaller internal `PokemonDTO` containing only the data the application needs, such as name, sprite and types.

I ran into problems because PokéAPI returned many fields that were not included in my DTOs. I solved this using:

`@JsonIgnoreProperties(ignoreUnknown = true)`.

I also added concurrent fetching with `ExecutorService`, `Callable` and `Future`, so multiple Pokémon can be fetched at the same time.

Finally, I added unit tests for the DTO mapping without depending on a live internet connection.

This week gave me a much better understanding of external APIs, JSON, DTOs and concurrent tasks.