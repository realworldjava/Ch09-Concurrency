package com.wiley.realworldjava.concurrency;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadUtils {
   private final static Pattern threadMappingPattern = Pattern.compile("(.+]).*@(.+)");
   private final static Pattern virtualPattern = Pattern.compile("(.+])");
   private final static Pattern carrierPattern = Pattern.compile(".+]/runnable@(.*)");

   public static Map.Entry<String, String> getCurrentVirtualAndPlatform() {
      Thread thread = Thread.currentThread();
      return getVirtualAndPlatform(thread);
   }

   public static Map.Entry<String, String> getVirtualAndPlatform(Thread thread) {
      Map.Entry<String, String> virtual = getVirtualToCarrierMapping(thread);
      return virtual;
   }

   /**
    * Returns a Map Entry with key=virtual thread name and value = carrier thread name
    * @param thread
    * @return
    */
   private static Map.Entry<String, String> getVirtualToCarrierMapping(Thread thread) {
      String name = thread.toString();
      return getVirtualId(name);
   }

   public static Map.Entry<String, String> getVirtualId(String name) {
      Matcher matcher = threadMappingPattern.matcher(name);
      if(matcher.find()) {
         String virtual = matcher.group(1);
         String platform = matcher.group(2);
         return Map.entry(virtual, platform);
      }
      return null;
   }

   public static String getVirtualId(Thread thread) {
      String name = thread.toString();
      Matcher matcher = virtualPattern.matcher(name);
      if(matcher.find()) {
         String virtual = matcher.group(1);
         return virtual;
      }
      return null;
   }
   public static String getCarrierId(Thread thread) {
      String name = thread.toString();
      Matcher matcher = carrierPattern.matcher(name);
      if(matcher.find()) {
         String carrier = matcher.group(1);
         return carrier;
      }
      return null;
   }

}
