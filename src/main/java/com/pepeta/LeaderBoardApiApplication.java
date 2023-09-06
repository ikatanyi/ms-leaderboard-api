package com.pepeta;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaderBoardApiApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(LeaderBoardApiApplication.class, args);
    }

//    @Profile("demo")
//    @Bean
//    CommandLineRunner initDatabase(OrderRepository repository) {
//        return args -> {
////            repository.save(new Book("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41")));
////            repository.save(new Book("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69")));
////            repository.save(new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99")));
//        };
//    }
}