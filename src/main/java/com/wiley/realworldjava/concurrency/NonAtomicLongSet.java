package com.wiley.realworldjava.concurrency;

public class NonAtomicLongSet {
   private static long sharedLong = 0;


   public static void main(String[] args) throws InterruptedException {
      Runnable writer1 = () -> {
         while(!Thread.currentThread().isInterrupted()) {
            sharedLong = 0xFFFFFFFFFFFFFFFFL; // Set to a long with all bits set to 1
         }
      };

      Runnable writer2 = () -> {
         while(!Thread.currentThread().isInterrupted()) {
            sharedLong = 0x0000000000000000L; // Set to a long with all bits set to 0
         }
      };

      Runnable reader = () -> {
         while(!Thread.currentThread().isInterrupted()) {
            long value = sharedLong;
            if(value!=0xFFFFFFFFFFFFFFFFL && value!=0x0000000000000000L) {
               System.out.printf("Inconsistent value read: %016X\n", value);
                    // Optionally, terminate the program if an inconsistency is found
                    System.exit(1);
            }
         }
      };

      Thread thread1 = new Thread(writer1);
      Thread thread2 = new Thread(writer2);
      Thread thread3 = new Thread(reader);

      thread1.start();
      thread2.start();
      thread3.start();

        // Let the threads run for more time to increase the chance of observing an inconsistency
        Thread.sleep(120000); // 120 seconds

        // Interrupt threads to stop execution
      thread1.interrupt();
      thread2.interrupt();
      thread3.interrupt();

        // Wait for threads to finish
      thread1.join();
      thread2.join();
      thread3.join();
   }
}
