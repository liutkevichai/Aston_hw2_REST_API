package ru.lai.hw2_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.lai.hw2_rest.controllers", "ru.lai.hw2_rest.services", "ru.lai.hw2_rest.repositories"})
public class SpringBootApp {

    public static void main(String[] args) {
        System.out.println("SPRING BOOT START");
        SpringApplication.run(SpringBootApp.class, args);
    }
}
