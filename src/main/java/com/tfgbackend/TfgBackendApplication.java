package com.tfgbackend;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.Rol;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.UsersRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

        User profesor = new User(null, "Andres","andres@hotmail.com", LocalDateTime.now(), List.of(), Rol.STUDENT);
        Exercise ejercicio = new Exercise(null, "", profesor);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Exercise>> violaciones = validator.validate(ejercicio);
        System.out.println(violaciones.toString());

        repository.save(profesor);
        repository.save(new User(null, "Carlos","ulrich111@hotmail.com", LocalDateTime.now(), List.of(), Rol.ADMIN));

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
