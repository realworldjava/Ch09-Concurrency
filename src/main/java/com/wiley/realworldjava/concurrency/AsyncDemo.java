package com.wiley.realworldjava.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Component
@EnableAsync
@Slf4j
public class AsyncDemo{

  @Autowired TaskExecutor taskExecutor;

  @Async
  public void performHeads(String message){
    IntStream.rangeClosed(1, 3).forEach(i -> {
      try{
        Thread.sleep(1000);
        log.info(message + " Heads");
      }catch(InterruptedException e){
        Thread.currentThread().interrupt();
      }
    });
  }

  @Async
  public void performTails(String message){
    IntStream.rangeClosed(1, 3).forEach(i -> {
      try{
        Thread.sleep(1000);
        log.info(message + " Tails");
      }catch(InterruptedException e){
        Thread.currentThread().interrupt();
      }
    });
  }

  /**
   * This method demonstrates an incorrect invocation of @Async methods. They must be called on another Spring managed class, and
   * not from the class itself
   */
  public void incorrectAsync(){
    performHeads("Incorrect");
    performTails("Incorrect");
  }

  /**
   * This useful method demonstrates how to schedule a job to be invoked repeatedly every N ms after an initial delay
   */
  @Scheduled(initialDelay = 20000, fixedRate = 1000)
  public void fixedRateTest(){
    log.info("Fixed rate");
  }

// Returns a CompletableFuture, that contains a results after a few seconds
  @Async
  public CompletableFuture<Long> getLongAfterDelay(){
    return CompletableFuture.supplyAsync(()-> {
      try{
        Thread.sleep(5_000);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
      return 5L;
    });
  }

  public void useTaskExecutor(){
    taskExecutor.execute(()->performHeads("using TaskExecutor"));
    taskExecutor.execute(()->performTails("using TaskExecutor"));
  }
}
