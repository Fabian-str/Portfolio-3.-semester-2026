---
title: "Week 7 – Authentication and Authorization"
date: 2026-10-18
---

This week I added authentication and authorization to the PokéJourney backend.

Users can now register and log in with passwords hashed using bcrypt. Successful login returns a JWT containing the user ID, username and role.

The playthrough endpoints are now protected and require a valid Bearer token. I also added role-based authorization with USER and ADMIN roles, including a protected admin endpoint.

Authentication and authorization were tested with REST Assured, covering successful login, missing tokens, forbidden access and valid admin access.

I also refactored the application setup because `Main` was becoming crowded. Javalin configuration, dependency creation and route registration were moved into separate classes, and the emulator game-state logic was moved into its own controller.

Finally, the JWT secret was moved out of the source code and into the local configuration.