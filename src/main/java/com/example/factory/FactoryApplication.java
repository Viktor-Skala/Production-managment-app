package com.example.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactoryApplication.class, args);
        Thread thread1 = Thread.currentThread();
        System.out.println("Thread name " + thread1.getName());
    }

//    @Bean
//    public CommandLineRunner creationProduct(final ProductServiceImpl productServiceImpl) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Thread thread = new Thread(new ProductGenerationThread(productServiceImpl, ));
//                thread.setDaemon(true);
//                thread.start();
//                System.out.println();
//
//            }
//        };
//    }
}
