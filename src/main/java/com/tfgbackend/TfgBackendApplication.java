package com.tfgbackend;

import com.tfgbackend.model.*;
import com.tfgbackend.model.enumerators.Rol;
import com.tfgbackend.model.enumerators.StatusExercise;
import com.tfgbackend.repositories.*;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class TfgBackendApplication implements CommandLineRunner {

    private final UserRepository ur;
    private final ExerciseRepository er;
    private final ExerciseService es;
    private final ExerciseBatteryRepository ebr;
    private final TagRepository tr;
    private final SolutionRepository solutionRepository;

    @Autowired
    public TfgBackendApplication(UserRepository ur, ExerciseRepository er, ExerciseBatteryRepository ebr, ExerciseService es, SolutionRepository solutionRepository, TagRepository tr) {
        this.ur = ur;
        this.er = er;
        this.es = es;
        this.ebr = ebr;
        this.tr = tr;
        this.solutionRepository = solutionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TfgBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        tr.deleteAll();
        ur.deleteAll();
        er.deleteAll();
        ebr.deleteAll();
        solutionRepository.deleteAll();

        ObjectId batteryId1 = new ObjectId("635981f6e40f61599e000068");
        ObjectId batteryId2 = new ObjectId("635981f6e40f61599a000068");
        ObjectId exerciseId1 = new ObjectId("635981f6e40a61599e000063");
        ObjectId exerciseId3 = new ObjectId("635981f6e40a61599e000062");
        ObjectId exerciseId4 = new ObjectId("635981f6e40a61599e000061");
        ObjectId exerciseId5 = new ObjectId("635981f6e40a61599e000060");
        ObjectId exerciseId6 = new ObjectId("635981f6e40a61599e000064");
        ObjectId exerciseId2 = new ObjectId("635981f6e40a61599e000068");
        ObjectId profesorId = new ObjectId("635981f6e40b61599e000064");
        ObjectId estudianteID = new ObjectId("635981f6e40c61599e000064");
        ObjectId tag1ID = new ObjectId();
        ObjectId tag2ID = new ObjectId();

        User profesor = new User(profesorId.toString(), "profesor","profesor@hotmail.com", LocalDateTime.now(), Rol.TEACHER, List.of());
        User estudiante = new User(estudianteID.toString(), "estudiante","estudiante@hotmail.com", LocalDateTime.now(), Rol.STUDENT, List.of());
        //TODO ACTUALIZAR LA LISTA DE ASIGNATURAS DEL ESTUDIANTE

        Tag herencia = new Tag(tag1ID.toString(),"Herencia");
        Tag getter = new Tag(tag2ID.toString(),"Getter");

        ExerciseBattery bateria1 = new ExerciseBattery(batteryId1, "Bateria de Herencia", List.of());
        ExerciseBattery bateria2 = new ExerciseBattery(batteryId2, "Bateria de Poliformismo", List.of());
        Exercise ejercicio1 = new Exercise(exerciseId1.toString(), "Ejercicio 1", "", List.of(), "", List.of(herencia, getter), bateria1, profesor);
        Exercise ejercicio3 = new Exercise(exerciseId3.toString(), "Ejercicio 3", "", List.of(), "", List.of(herencia, getter), bateria1, profesor);
        Exercise ejercicio4 = new Exercise(exerciseId4.toString(), "Ejercicio 4", "", List.of(), "", List.of(herencia, getter), bateria1, profesor);
        Exercise ejercicio5 = new Exercise(exerciseId5.toString(), "Ejercicio 5", "", List.of(), "", List.of(herencia, getter), bateria1, profesor);

        Exercise ejercicio6 = new Exercise(exerciseId6.toString(), "Ejercicio 6", "", List.of(), "", List.of(herencia, getter), bateria2, profesor);
        Exercise ejercicio2 = new Exercise(exerciseId2.toString(), "Ejercicio 2", "", List.of(), "", List.of(herencia, getter), bateria2, profesor);

        Solution solucion = new Solution(null, LocalDateTime.now(), StatusExercise.COMPLETED, estudiante, ejercicio1,5);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Exercise>> violaciones = validator.validate(ejercicio1);
        System.out.println("Violaciones: " + violaciones.size() + ", texto: " + violaciones);

        ur.save(profesor);
        tr.save(herencia);
        tr.save(getter);
        solutionRepository.save(solucion);
        ebr.save(bateria1);
        ebr.save(bateria2);
        er.save(ejercicio1);
        er.save(ejercicio2);
        er.save(ejercicio3);
        er.save(ejercicio4);
        er.save(ejercicio5);
        er.save(ejercicio6);

        ur.save(estudiante);


        List<ExerciseSolutionDTO> lista = es.allExercisesSolutionsByStudent("635981f6e40c61599e000064");

        for (ExerciseSolutionDTO e : lista){
            System.out.println("ATIENDE: EjercicioSolution: " + e.getName() + " Numero de errores:" + e.getNumberErrorsSolution());
        }

        /*DBREF -> Ejercicio.Bateria ----> lazy = true ----> NO SE PUEDE GUARDAR EJERCICIO
        * Ref.Manual -> Ejercicio.linkBateria -> EJERCICO SE GUARDA -------> EjercicioBateriaDTO -----> NO SE PUEDE GUARDAR*/

        // fetch all customers
        System.out.println("\nCustomers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : ur.findAll()) {
            System.out.println(user);
        }
        System.out.println();

    }
}
