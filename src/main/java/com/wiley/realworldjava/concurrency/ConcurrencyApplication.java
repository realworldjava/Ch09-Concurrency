package com.wiley.realworldjava.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class ConcurrencyApplication implements CommandLineRunner {


  private AsyncDemo asyncDemo;


  public ConcurrencyApplication(AsyncDemo asyncDemo
  ) {
    this.asyncDemo = asyncDemo;
  }

  public static void main(String[] args) {
    SpringApplication.run(ConcurrencyApplication.class, args);
  }


  @Override
  public void run(String... args) throws ExecutionException, InterruptedException {
    // You can run all the methods below, or comment out the ones you don't want to see
    callIncorrect();
    callCorrect();
    callGetLongAfterDelay();
    launchTaskExecutor();
  }

  private void callIncorrect() {
    asyncDemo.incorrectAsync();
    log.info("Called incorrect");
  }

  private void callCorrect() {
    log.info("Starting perform heads and perform tails");
    asyncDemo.performHeads("Correct");
    asyncDemo.performTails("Correct");
    log.info("Called correct");
  }

  private void callGetLongAfterDelay() throws InterruptedException, ExecutionException {
    CompletableFuture<Long> aLong = asyncDemo.getLongAfterDelay();
    log.info("Getting result");
    log.info("Got long:" + aLong.get());
  }

  /**
   * Launches the task executor
   */
  private void launchTaskExecutor() {
    log.info("Launching task executor");
    asyncDemo.useTaskExecutor();
  }
}
