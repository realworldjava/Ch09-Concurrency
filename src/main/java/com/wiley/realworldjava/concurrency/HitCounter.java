package com.wiley.realworldjava.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class HitCounter {
  public static final int HITS = 10_000;
  private static int hitCounter = 0;
  private static AtomicInteger atomicHitCounter = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    Runnable incrementTask = () -> {
      for(int i = 0; i < HITS; i++) {
        incrementHitCounter(i);
      }
    };
    Thread thread1 = Thread.ofPlatform().start(incrementTask);
    Thread thread2 = Thread.ofPlatform().start(incrementTask);
    thread1.join();
    thread2.join();

    System.out.println("Hit counter value was: " + hitCounter + ". Expected " + (2 * HITS));
    System.out.println("Atomic hit counter value was: " + atomicHitCounter + ". Expected " + (2 * HITS));
  }

  private static void incrementHitCounter(int i) {
    int temp = hitCounter;
    if(i % 1000==0) {
      System.out.println(Thread.currentThread() + " incrementing " + temp + ". Atomic hitCounter:" + atomicHitCounter);
    }
    hitCounter = temp + 1;
    atomicHitCounter.incrementAndGet();
  }
}