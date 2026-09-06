---
title: "Uge 4 – Concurrency and emulator proof-of-concept"
date: 2026-09-06
draft: false
featureimage: "images/pokemon_1.webp"
---

This week I worked with concurrency in Java using `Runnable`, `Callable`, `ExecutorService` and `Future`, including proper shutdown of thread pools.

I also created a small race condition example using a normal integer counter. When 10,000 increments were executed across multiple threads, the final result was sometimes lower than expected. I then fixed the problem using `AtomicInteger`, which consistently produced the correct result.

Finally, I created a proof of concept for the planned emulator integration. Using mGBA and Lua, I was able to read live memory from Pokémon Red, including the current map, party size and the internal species IDs of Pokémon in the party.

This confirmed that live game tracking through the emulator is technically possible and can be integrated with the Java backend later in the project.

The source files used for this week's experiments are available here:

### Concurrency examples

- [Unsafe counter example](/scripts/UnsafeCounter.java)
- [Thread-safe counter example](/scripts/SafeCounter.java)

### Emulator scripts

- [Current map script](/scripts/current_map.lua)
- [Party count script](/scripts/party_count.lua)
- [Party species script](/scripts/party_species.lua)
- [Combined PokéJourney tracker](/scripts/pokejourney_tracker.lua)