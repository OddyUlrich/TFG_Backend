package com.tfgbackend;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Subject;
import com.tfgbackend.model.enumerators.Rol;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.ExerciseBatteryRepository;
import com.tfgbackend.repositories.ExerciseRepository;
import com.tfgbackend.repositories.SubjectRepository;
import com.tfgbackend.repositories.UserRepository;
import com.tfgbackend.service.ExerciseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.convert.LazyLoadingProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class TfgBackendApplication implements CommandLineRunner {

    private final UserRepository ur;
    private final SubjectRepository sr;
    private final ExerciseRepository er;
    private final ExerciseService es;
    private final ExerciseBatteryRepository ebr;

    @Autowired
    public TfgBackendApplication(UserRepository ur, SubjectRepository sr, ExerciseRepository er, ExerciseBatteryRepository ebr, ExerciseService es) {
        this.ur = ur;
        this.sr = sr;
        this.er = er;
        this.es = es;
        this.ebr = ebr;
    }

    public static void main(String[] args) {
        SpringApplication.run(TfgBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ur.deleteAll();
        er.deleteAll();
        ebr.deleteAll();
        sr.deleteAll();

        ObjectId subjectId = new ObjectId("635981f6e40f61599e000060");
        ObjectId batteryId = new ObjectId("635981f6e40f61599e000068");
        ObjectId exerciseId = new ObjectId("635981f6e40a61599e000064");
        ObjectId profesorId = new ObjectId("635981f6e40b61599e000064");
        ObjectId estudianteID = new ObjectId("635981f6e40c61599e000064");

        User profesor = new User(profesorId, "Andres","andres@hotmail.com", LocalDateTime.now(), List.of(), Rol.TEACHER);
        User estudiante = new User(estudianteID, "Estudiante","andres21@hotmail.com", LocalDateTime.now(), List.of(), Rol.STUDENT);
        Subject asignatura = new Subject(subjectId,"Mates", 2020, List.of(), List.of(estudiante));

        System.out.println("ALUMNOS: " + asignatura.studentsList().toString());

        ExerciseBattery bateria = new ExerciseBattery(batteryId, "Bateria_1", List.of(), asignatura);
        Exercise ejercicio = new Exercise(exerciseId, "Ejercicio_1", "", List.of(), "", List.of(), bateria, profesor);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Exercise>> violaciones = validator.validate(ejercicio);
        System.out.println("Violaciones: " + violaciones.size() + ", texto: " + violaciones.toString());

        ur.save(profesor);
        sr.save(asignatura);
        ebr.save(bateria);
        er.save(ejercicio);
        ur.save(new User(null, "Carlos","ulrich111@hotmail.com", LocalDateTime.now(), List.of(), Rol.ADMIN));

        List<Subject> lista = sr.findAllByStudentID(estudiante.id());

        for (Subject eb : lista){
            System.out.println("ATIENDE, Asignatura: " + eb.name() + ", Alumnos: " + eb.studentsList());
            if (eb.studentsList() instanceof LazyLoadingProxy lazy){
                List<User> estudiantes = (List<User>) lazy.getTarget();

                for (User e : estudiantes){
                    System.out.println("Estudiante: " + e.name());
                }
            }
        }

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : ur.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Carlos'):");
        System.out.println("--------------------------------");
        System.out.println(ur.findUserByName("Carlos"));

    }
}
