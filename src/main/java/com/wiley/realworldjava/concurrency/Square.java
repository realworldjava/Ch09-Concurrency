package com.wiley.realworldjava.concurrency;

import java.util.function.Function;

public class Square implements Function<Integer, Integer> {
   @Override
   public Integer apply(Integer integer) {
      return integer * integer;
   }

   public static void main(String[] args) {
      squareUsingClass();
      squareUsingAnonymousClass();
      newMethodWithAndThen();
   }

   private static void squareUsingClass() {
      Integer result = new Square().apply(3);
      System.out.println(result);
   }

   private static void squareUsingAnonymousClass() {
      Function<Integer, Integer> squareFn = i -> i * i;
      System.out.println(squareFn.apply(4));
   }

   private static void newMethodWithAndThen() {
      Function<Integer, Integer> squareTimesTwo = ((Function<Integer, Integer>) i -> i * i).andThen(j -> j * 2);
      System.out.println(squareTimesTwo.apply(3));
   }
}
