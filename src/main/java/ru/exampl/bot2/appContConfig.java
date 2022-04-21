package ru.exampl.bot2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appContConfig {
      @Bean
      public Dog getDog() {
            return new Dog("Singleton");
      }

      @Bean
      public MyPerson getMP() {
            return new MyPerson(getDog());
      }
}

class Dog {
      public String name;
      public Dog(String name) {
            this.name = name;
      }
}

class MyPerson {
      public Dog pet;

      public MyPerson(Dog dog) {
            this.pet=dog;
      }
}
