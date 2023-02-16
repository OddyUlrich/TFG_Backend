package com.tfgbackend;

import com.tfgbackend.model.Rol;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class TfgBackendApplication implements CommandLineRunner {

    private final UsersRepository repository;

    @Autowired
    public TfgBackendApplication(UsersRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TfgBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        User profesor = new User("Andres","andres@hotmail.com", LocalDateTime.now(), Arrays.asList("foo", "bar"), Rol.TEACHER);

        repository.save(profesor);
        repository.save(new User("Carlos","ulrich111@hotmail.com", LocalDateTime.now(), Arrays.asList("foo", "bar"), Rol.ADMIN));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Carlos'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findUserByName("Carlos"));

    }
}
