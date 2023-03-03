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
    private final SubjectRepository subjectRepository;
    private final ExerciseRepository er;
    private final ExerciseService es;
    private final ExerciseBatteryRepository ebr;
    private final SolutionRepository solutionRepository;

    @Autowired
    public TfgBackendApplication(UserRepository ur, SubjectRepository subjectRepository, ExerciseRepository er, ExerciseBatteryRepository ebr, ExerciseService es, SolutionRepository solutionRepository) {
        this.ur = ur;
        this.subjectRepository = subjectRepository;
        this.er = er;
        this.es = es;
        this.ebr = ebr;
        this.solutionRepository = solutionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TfgBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ur.deleteAll();
        er.deleteAll();
        ebr.deleteAll();
        subjectRepository.deleteAll();
        solutionRepository.deleteAll();

        ObjectId subjectId1 = new ObjectId("635981f6e40f61599e000060");
        ObjectId subjectId2 = new ObjectId("635981f6e40f61599e000056");
        ObjectId batteryId1 = new ObjectId("635981f6e40f61599e000068");
        ObjectId batteryId2 = new ObjectId("635981f6e40f61599a000068");
        ObjectId exerciseId1 = new ObjectId("635981f6e40a61599e000064");
        ObjectId exerciseId2 = new ObjectId("635981f6e40a61599e000068");
        ObjectId profesorId = new ObjectId("635981f6e40b61599e000064");
        ObjectId estudianteID = new ObjectId("635981f6e40c61599e000064");

        User profesor = new User(profesorId, "profesor","profesor@hotmail.com", LocalDateTime.now(), List.of(), Rol.TEACHER);
        User estudiante = new User(estudianteID, "estudiante","estudiante@hotmail.com", LocalDateTime.now(), List.of(), Rol.STUDENT);
        Subject asignatura1 = new Subject(subjectId1,"Mates", 2020, List.of(), List.of(estudiante));
        Subject asignatura2 = new Subject(subjectId2,"Lengua", 2020, List.of(), List.of(profesor));
        //TODO ACTUALIZAR LA LISTA DE ASIGNATURAS DEL ESTUDIANTE

        ExerciseBattery bateria1 = new ExerciseBattery(batteryId1, "Bateria_1", List.of(), asignatura1);
        ExerciseBattery bateria2 = new ExerciseBattery(batteryId2, "Bateria_2", List.of(), asignatura2);
        Exercise ejercicio1 = new Exercise(exerciseId1, "Ejercicio_1", "", List.of(), "", List.of(), bateria1, profesor);
        Exercise ejercicio2 = new Exercise(exerciseId2, "Ejercicio_2", "", List.of(), "", List.of(), bateria2, profesor);

        Solution solucion = new Solution(null, LocalDateTime.now(), StatusExercise.PENDING, estudiante, ejercicio1,0);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Exercise>> violaciones = validator.validate(ejercicio1);
        System.out.println("Violaciones: " + violaciones.size() + ", texto: " + violaciones);

        ur.save(profesor);
        subjectRepository.save(asignatura1);
        subjectRepository.save(asignatura2);
        solutionRepository.save(solucion);
        ebr.save(bateria1);
        ebr.save(bateria2);
        er.save(ejercicio1);
        er.save(ejercicio2);

        ur.save(estudiante);

        List<ExerciseSolutionDTO> lista = es.allExercisesByStudent(estudiante.getId());

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
