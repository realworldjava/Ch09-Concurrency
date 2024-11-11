package com.wiley.realworldjava.concurrency;

import java.util.function.Function;

public class FunctionAndThen {
   public static void main(String[] args) {
      // Function produces Person's full name
      Function<Person, String> personToFullName = (Person p) -> p.firstName + " " + p.lastName;

      // Function produces upper case
      Function<String, String> toUpperCase = String::toUpperCase;

      // Combination Function, returns the person's full name as upper case
      Function<Person, String> personToUpperCaseFullName = personToFullName.andThen(toUpperCase);

      // Create a new Person object
      Person person = new Person("John", "Jones");

      // Apply the composed function
      String upperCaseFullName = personToUpperCaseFullName.apply(person);

      // Print the result
      System.out.println(upperCaseFullName); // Output: JOHN DOE
   }
   record Person(String firstName, String lastName){};
}

