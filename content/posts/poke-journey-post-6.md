---
title: "Week 6 – REST testing and WebSockets"
date: 2026-10-17
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I added automated REST API tests using Rest Assured and Hamcrest. I tested the playthrough CRUD endpoints, including expected status codes such as `200`, `201`, `204` and `404`.

I also added WebSocket support to Javalin and created a small service for broadcasting live game state to connected clients.

The emulator proof of concept was then connected to the backend. The mGBA Lua script now sends live Pokémon Red state to `/api/game-state`, which is converted into a DTO and pushed to the browser through WebSockets.

I also added a Gen 1 adapter and data-driven JSON mappings so internal game IDs can be translated into National Pokédex IDs and location keys.

This completed the first end-to-end live tracking flow from Pokémon Red running in mGBA to the browser.