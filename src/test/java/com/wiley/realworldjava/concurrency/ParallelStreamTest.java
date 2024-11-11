package com.wiley.realworldjava.concurrency;

import org.junit.Test;

import java.util.List;

public class ParallelStreamTest {
  @Test
  public void testParallelStreams() {
    List<String> words = List.of("It", "was", "the", "best", "of", "times",
       "it", "was", "the", "worst", "of", "times");
    List<String> collect = words.parallelStream().peek(name -> {
      try {
        System.out.println("Thread: " + Thread.currentThread().getName() + " processing: " + name);
        Thread.sleep(100);
      } catch(InterruptedException e) {
        throw new RuntimeException(e);
      }
    }).map(String::toUpperCase).toList();

    System.out.println(collect);
  }
}

