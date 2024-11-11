package com.wiley.realworldjava.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class VirtualThreadStatesTests {
  private final Map<String, Set<String>> carrierMappings
     = new ConcurrentHashMap<>();

  @Test
  public void getCarriers() {
    // launch a number of threads, get them to spin a million
    // times each, and collect the carriers in a map.
    // Then sleep for a bit to see the carrier unmount.
    // Do this a few times and capture the results.
    int threadCount = 4;

    try(ExecutorService executorService
           = Executors.newVirtualThreadPerTaskExecutor()) {
      for(int i = 0; i < threadCount; i++) {
        executorService.execute(() -> {
          Thread currentVirtualThread = Thread.currentThread();
          // Get the ID of this virtual thread for mapping
          String virtualID = ThreadUtils.getVirtualId(currentVirtualThread);
          // run and sleep, several times, each time capture the carrier ids
          for(int loopCount = 0; loopCount < 10; loopCount++) {
// uncomment below to synchronize, shows pinning
//            synchronized(this)
            {
              String carrierID = ThreadUtils.getCarrierId(currentVirtualThread);

              Set<String> carriers = carrierMappings.computeIfAbsent(virtualID, x -> new HashSet<>());
              carriers.add(carrierID);
              for(int j = 0; j < 1_000_000; j++) {
                //spin
              }
              try {
                // Briefly enter a WAITING state,
                // and see what happens when we awake
                Thread.sleep(10);
              } catch(InterruptedException e) {
                currentVirtualThread.interrupt();
              }
            }
          }
        });
      }
    }

    printSummary();
  }

  @Test
  public void testScheduledVirtualThreads() throws InterruptedException {
    ThreadFactory factory = Thread.ofVirtual().factory();
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2, factory);
    AtomicInteger counter = new AtomicInteger(0);
    executorService.scheduleAtFixedRate(() ->
    {
      Map.Entry<String, String> currentVirtualAndPlatform = ThreadUtils.getCurrentVirtualAndPlatform();
      log.info(counter.incrementAndGet() + " " + currentVirtualAndPlatform.toString());
    }, 0, 1, TimeUnit.SECONDS);
    Thread.sleep(10000);
    System.out.println();
  }

  private void printSummary() {
    // print a summary of all virtual threads and all carriers over time
    log.info("\n\nSummary");
    carrierMappings.forEach((key, value) -> {
      log.info(key + ": Carriers:");
      value.forEach(log::info);
      // put a blank line between the virtuals
      System.out.println();
    });
  }

  @Test
  public void testCoreCount() {
    int count = Runtime.getRuntime().availableProcessors();
    log.info(count + " processors");
  }
}