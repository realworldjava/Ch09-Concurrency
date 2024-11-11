package com.wiley.realworldjava.concurrency;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
public class CompletableFutureTests {
  @Test
  public void testCompletableFutureThenAccept() {
    try {
      // Create a CompletableFuture that completes with a result after a delay
      CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
        try {
          log.info("supplyAsync called");
          // Simulate a long-running computation
          Thread.sleep(5_000);
          log.info("supplyAsync complete");
          return "Hello";
        } catch(InterruptedException e) {
          throw new RuntimeException(e);
        }
      });

      // Attach a thenAccept callback to the CompletableFuture
      CompletableFuture<Void> future2 = future1.thenAccept(result -> {
           log.info("Computation complete. thenAccept sees result:" +
              " " + result);
           log.info("thenAccept performing another operation. " +
              "Result: " + (result + ", World"));
           try {
             log.info("thenAccept sleeping");
             Thread.sleep(5_000);
             log.info("thenAccept waking");
           } catch(InterruptedException e) {
             throw new RuntimeException(e);
           }
         }
      );

      // Perform other work while the CompletableFuture is running
      log.info("Main thread continuing with other work...");

      // wait for the CompletableFuture to complete
      String result1 = future1.get();
      log.info("CompletableFuture #1 is complete. Result:" + result1);
      future2.join();
      log.info("CompletableFuture #2 is complete.");
    } catch(InterruptedException e) {
      Thread.currentThread().interrupt();
    } catch(ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
