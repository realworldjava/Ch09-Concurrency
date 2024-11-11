package com.wiley.realworldjava.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class PingNetPong{
  public static void main(String[] args){
    new PingNetPong().playPingPong(3);
  }

  public void playPingPong(int volleys){
    ReentrantLock lock = new ReentrantLock();
    Condition ping = lock.newCondition();
    Condition overTheNet = lock.newCondition();
    Condition pong = lock.newCondition();
    boolean[] pingOrPong = {true};
    try(ExecutorService executor
          = Executors.newVirtualThreadPerTaskExecutor()){
      Phaser phaser = new Phaser(3);

      executor.submit(() -> {
          lock.lock();
          IntStream.rangeClosed(1, volleys).forEach((i) -> {
            try{
              phaser.arrive();
              ping.await();
              System.out.println(i + " Ping");
              overTheNet.signal();
            }catch(InterruptedException e){
              e.printStackTrace();
            }
          });
          lock.unlock();
        });

      executor.submit(() -> {
          lock.lock();
          IntStream.rangeClosed(1, volleys * 2)
            .forEach((i) -> {
              try{
                phaser.arrive();
                overTheNet.await();
                System.out.println((i + 1) / 2 + " Over the net");
                pingOrPong[0] = !pingOrPong[0];
                if(pingOrPong[0]){
                  ping.signal();
                }else{
                  pong.signal();
                }
              }catch(InterruptedException e){
                e.printStackTrace();
              }
            });
          lock.unlock();
        });

      executor.submit(() -> {
          lock.lock();
          IntStream.rangeClosed(1, volleys)
            .forEach((i) -> {
              try{
                phaser.arrive();
                pong.await();
                System.out.println(i + " Pong");
                overTheNet.signal();
              }catch(InterruptedException e){
                e.printStackTrace();
              }
            });
          lock.unlock();
        });

      phaser.awaitAdvance(0);
      lock.lock();
      ping.signal();
      lock.unlock();
    }
  }
}
