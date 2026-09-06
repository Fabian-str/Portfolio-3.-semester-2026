package app;

import app.concurrency.PokemonFetchLogger;
import app.concurrency.SafeCounter;
import app.concurrency.UnsafeCounter;
import app.dto.PokemonDTO;
import app.services.PokemonApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        SafeCounter counter = new SafeCounter();

        ExecutorService executor =
                Executors.newFixedThreadPool(8);

        for (int i = 0; i < 10000; i++) {
            executor.submit(counter::increment);
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(counter.getCount());
    }
}