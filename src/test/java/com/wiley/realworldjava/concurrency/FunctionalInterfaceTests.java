package com.wiley.realworldjava.concurrency;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class FunctionalInterfaceTests {
   @Test
   public void testSupplierGet() {
      Random random = new Random();
//      Create a Supplier that produces a random integer between 100 and 200
      Supplier<Integer> random100To200 = () -> random.nextInt(100, 200);
      for(int i = 0; i < 10; i++) {
         Integer value = random100To200.get();
         System.out.println("Next random:" + value);
      }
   }

   @Test
   public void testConsumerAccept() {
      // Define a Consumer that accepts a mutable List of
      // ints and replaces each value with twice its value
      Consumer<List<Integer>> twoTimes = list -> list.replaceAll(value -> value * 2);

      List<Integer> ints = new ArrayList<>(List.of(10, 20, 30, 40));
      twoTimes.accept(ints);
      System.out.println(ints);
   }

   @Test
   public void testConsumerAndThen() {
      // printMessage Consumer prints the supplied value
      Consumer<String> printMessage = message -> System.out.println("Message: " + message);

      // upperCaseMessage Consumer converts the supplied value to uppercase
      Consumer<String> uppercaseMessage = message -> System.out.println("Uppercase: " + message.toUpperCase());

      // printAndUppercase uses andThen to chain two consumers to produce a new consumer
      Consumer<String> printAndUppercase = printMessage.andThen(uppercaseMessage);

      // Apply the chained consumer to a message
      printAndUppercase.accept("Hello, World!");
   }


   @Test
   public void testFunctionApply() {
      // SquareIt function squares the supplied value
      Function<Integer, Integer> SquareIt = value -> value * value;
      for(int i = 1; i <= 5; i++) {
         System.out.println("2^" + i + " = " + SquareIt.apply(i));
      }
   }

   @Test
   public void testFunctionAndThen() {
// Create a simple Person class. A record defines an
// immutable class from the constructor parameters.
      record Person(String firstName, String lastName) {
      }

      // Function takes Person as input and produces a String representing their full name
      Function<Person, String> personToFullName = (Person p) -> p.firstName + " " + p.lastName;

      // Combination Function, returns the person's full name as upper case
      Function<Person, String> personToUpperCaseFullName = personToFullName.andThen(String::toUpperCase);

      // Create a new Person object
      Person person = new Person("Mary", "Jones");

      // Apply the chained function
      String upperCaseFullName = personToUpperCaseFullName.apply(person);

      // Print the result
      System.out.println(upperCaseFullName);
   }

   @Test
   public void testFunctionCompose() {
      record Person(String firstName, String lastName) {
      }

      // Function takes Person as input and produces a String representing their full name
      Function<Person, String> personToFullName = (Person p) -> p.firstName + " " + p.lastName;

      // Combination Function, returns the person's full name as upper case
      Function<String, String> toUpperCase = String::toUpperCase;
      Function<Person, String> personToUpperCaseFullName = toUpperCase.compose(personToFullName);

      // Create a new Person object
      Person person = new Person("John", "Smith");

      // Apply the chained function
      String upperCaseFullName = personToUpperCaseFullName.apply(person);

      // Print the result
      System.out.println(upperCaseFullName);
   }

   @Test
   public void testBiFunctionApply() {
      BiFunction<Double, Double, Double> Hypotenuse = (a, b) -> Math.sqrt(a * a + b * b);
      Double c;
      c = Hypotenuse.apply(3d, 4d);
      System.out.println(c);
      c = Hypotenuse.apply(5d, 12d);
      System.out.println(c);
   }
}
