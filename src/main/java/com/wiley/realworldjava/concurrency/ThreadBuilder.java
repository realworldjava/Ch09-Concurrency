package com.wiley.realworldjava.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ThreadBuilder {

   private static Map<String, Set<String>> virtualToPlatform = new ConcurrentHashMap<>();

   public static void main(String[] args) throws InterruptedException {
//      launchBasicPlatformThread();
//      launchMoreDetailedPlatformThread();
//      launchBasicVirtualThread();
      List<Thread> threads = new ArrayList<>();
      for(int i = 0; i < 3; i++) {
         Thread thread = launchMoreDetailedVirtualThread();
         threads.add(thread);
      }
      threads.forEach(thread -> {
         try {
            thread.join();
         } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
         }
      });
      virtualToPlatform.forEach((key, value) -> {
         System.out.println(key);
         value.forEach(System.out::println);
      });
   }

   private static void launchBasicPlatformThread() {
      Thread thread = Thread.ofPlatform()
         .start(() -> logDetails(Thread.currentThread(), 0));
   }

   private static void launchBasicVirtualThread() {
      Thread thread = Thread.ofVirtual()
         .start(() -> logDetails(Thread.currentThread(), 0));
   }

   private static void launchMoreDetailedPlatformThread() {
      Thread thread = Thread.ofPlatform()
         .name("Some meaningful name")
         .priority(Thread.NORM_PRIORITY + 1)
         .group(new ThreadGroup("Some thread group"))
         .daemon(false)
         .start(() -> logDetails(Thread.currentThread(), 100));
//      Future
   }

   private static Thread launchMoreDetailedVirtualThread() {
      Thread thread = Thread.ofVirtual()
//         .name("Some meaningful name")
////         .priority(Thread.NORM_PRIORITY+1)
////         .group(new ThreadGroup("Some thread group"))
////         .daemon(false)
         .start(() -> logDetails(Thread.currentThread(), 100));
//      Future
      return thread;
   }

   private static synchronized void logDetails(Thread thread, long delay) {
      try {
         for(int i = 0; i < 10; i++) {
            capturePlatformThreadInfo();
            String s = "Priority:\t" + thread.getPriority();
//            log(s);
//            log("Name:\t" + thread.getName());
//            log("ThreadGroup:\t" + thread.getThreadGroup());
            Thread.sleep(delay);
         }
      } catch(InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }

   private static void log(String s) {
      log.info("Thread:" + Thread.currentThread() + "\t:" + s);
   }

   private static void capturePlatformThreadInfo() {
      Map.Entry<String, String> virtualAndPlatform = ThreadUtils.getCurrentVirtualAndPlatform();
      if(virtualAndPlatform!=null) {
         virtualToPlatform.computeIfAbsent(virtualAndPlatform.getKey(), k -> new HashSet<>()).add(virtualAndPlatform.getValue());
      }
   }
}
