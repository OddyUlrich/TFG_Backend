package com.tfgbackend;

import com.tfgbackend.model.*;
import com.tfgbackend.model.enumerator.Rol;
import com.tfgbackend.model.enumerator.StatusExercise;
import com.tfgbackend.repository.*;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.dto.ExerciseHomeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class TfgBackendApplication implements CommandLineRunner {

    private final UserRepository ur;
    private final ExerciseRepository er;
    private final ExerciseService es;
    private final ExerciseBatteryRepository ebr;
    private final TagRepository tr;
    private final SolutionRepository solutionRepository;
    private final ExerciseFileRepository efr;

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Autowired
    public TfgBackendApplication(UserRepository ur, ExerciseRepository er, ExerciseBatteryRepository ebr, ExerciseService es, SolutionRepository solutionRepository, TagRepository tr, ExerciseFileRepository efr) {
        this.ur = ur;
        this.er = er;
        this.es = es;
        this.ebr = ebr;
        this.tr = tr;
        this.solutionRepository = solutionRepository;
        this.efr = efr;
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
        efr.deleteAll();
        solutionRepository.deleteAll();

        ObjectId batteryId1 = new ObjectId("635981f6e40f61599e000068");
        ObjectId batteryId2 = new ObjectId("635981f6e40f61599a000068");
        ObjectId batteryId3 = new ObjectId("635981f6e40f61599a000067");
        ObjectId exerciseId1 = new ObjectId("635981f6e40a61599e000060");
        ObjectId exerciseId2 = new ObjectId("635981f6e40a61599e000061");
        ObjectId exerciseId3 = new ObjectId("635981f6e40a61599e000062");
        ObjectId exerciseId4 = new ObjectId("635981f6e40a61599e000063");
        ObjectId exerciseId5 = new ObjectId("635981f6e40a61599e000064");
        ObjectId exerciseId6 = new ObjectId("635981f6e40a61599e000065");
        ObjectId exerciseId7 = new ObjectId("635981f6e40a61599e000066");
        ObjectId exerciseId8 = new ObjectId("635981f6e40a61599e000067");
        ObjectId exerciseId9 = new ObjectId("635981f6e40a61599e000069");
        ObjectId exerciseId10 = new ObjectId("635981f6e40a61599e000070");
        ObjectId exerciseId11 = new ObjectId("635981f6e40a61599e000071");

        ObjectId profesorId = new ObjectId("635981f6e40b61599e000064");
        ObjectId estudianteID = new ObjectId("635981f6e40c61599e000064");
        ObjectId estudianteID2 = new ObjectId("635981f6e40c61599e000062");

        ObjectId tag1ID = new ObjectId();
        ObjectId tag2ID = new ObjectId();

        User profesor = new User(profesorId.toString(), "profesor", bcrypt.encode("password"), "profesor@hotmail.com", LocalDateTime.now(), List.of(Rol.TEACHER), List.of());
        User estudiante = new User(estudianteID.toString(), "estudiante", bcrypt.encode("password"), "estudiante@hotmail.com", LocalDateTime.now(), List.of(Rol.STUDENT), List.of());
        User estudiante2 = new User(estudianteID2.toString(), "estudiante2", bcrypt.encode("password"), "estudiante2@hotmail.com", LocalDateTime.now(), List.of(Rol.STUDENT), List.of());
        //TODO ACTUALIZAR LA LISTA DE ASIGNATURAS DEL ESTUDIANTE

        ur.save(estudiante);
        ur.save(estudiante2);
        ur.save(profesor);

        Tag herencia = new Tag(tag1ID.toString(), "Herencia");
        Tag getter = new Tag(tag2ID.toString(), "Getter");

        tr.save(herencia);
        tr.save(getter);

        ExerciseBattery bateria1 = new ExerciseBattery(batteryId1, "Bateria de Herencia");
        ExerciseBattery bateria2 = new ExerciseBattery(batteryId2, "Bateria de Poliformismo");
        ExerciseBattery bateria3 = new ExerciseBattery(batteryId3, "Bateria de Ejemplo");

        ebr.save(bateria1);
        ebr.save(bateria2);
        ebr.save(bateria3);

        Exercise ejercicio1 = new Exercise(exerciseId1.toString(), "Ejercicio 1", "", List.of(), "", List.of(herencia, getter), bateria1, profesor, LocalDateTime.now());
        Exercise ejercicio3 = new Exercise(exerciseId3.toString(), "Ejercicio 3", "", List.of(), "", List.of(herencia, getter), bateria1, profesor, LocalDateTime.now());
        Exercise ejercicio4 = new Exercise(exerciseId4.toString(), "Ejercicio 4", "", List.of(), "", List.of(herencia, getter), bateria1, profesor, LocalDateTime.now());
        Exercise ejercicio5 = new Exercise(exerciseId5.toString(), "Ejercicio 5", "", List.of(), "", List.of(herencia, getter), bateria1, profesor, LocalDateTime.now());

        Exercise ejercicio6 = new Exercise(exerciseId6.toString(), "Ejercicio 6", "", List.of(), "", List.of(herencia, getter), bateria2, profesor, LocalDateTime.now());
        Exercise ejercicio7 = new Exercise(exerciseId7.toString(), "Ejercicio 7", "", List.of(), "", List.of(herencia, getter), bateria2, profesor, LocalDateTime.now());
        Exercise ejercicio8 = new Exercise(exerciseId8.toString(), "Ejercicio 8", "", List.of(), "", List.of(herencia, getter), bateria2, profesor, LocalDateTime.now());
        Exercise ejercicio9 = new Exercise(exerciseId9.toString(), "Ejercicio 9", "", List.of(), "", List.of(herencia, getter), bateria2, profesor, LocalDateTime.now());
        Exercise ejercicio10 = new Exercise(exerciseId10.toString(), "Ejercicio 10", "", List.of(), "", List.of(herencia, getter), bateria3, profesor, LocalDateTime.now());
        Exercise ejercicio11 = new Exercise(exerciseId11.toString(), "Ejercicio 11", "", List.of(), "", List.of(herencia, getter), bateria3, profesor, LocalDateTime.now());
        Exercise ejercicio2 = new Exercise(exerciseId2.toString(), "Ejercicio 2", "", List.of(), "", List.of(herencia, getter), bateria2, profesor, LocalDateTime.now());

        er.save(ejercicio1);
        er.save(ejercicio2);
        er.save(ejercicio3);
        er.save(ejercicio4);
        er.save(ejercicio5);
        er.save(ejercicio6);
        er.save(ejercicio7);
        er.save(ejercicio8);
        er.save(ejercicio9);
        er.save(ejercicio10);
        er.save(ejercicio11);

        //ESTUDIANTE

        Solution solucion2 = new Solution(LocalDateTime.now(), "intento_2", StatusExercise.PENDING, estudiante, ejercicio1, 4);
        sleep(5);
        Solution solucion = new Solution(LocalDateTime.now(),"intento_1",StatusExercise.PENDING,estudiante, ejercicio1, 5);
        sleep(5);
        Solution solucion3 = new Solution(LocalDateTime.now(), "intento_3", StatusExercise.COMPLETED, estudiante, ejercicio1, 3);
        sleep(5);
        Solution solucion4 = new Solution(LocalDateTime.now(), "intento_1_2", StatusExercise.PENDING, estudiante, ejercicio2, 5);
        sleep(5);
        Solution solucion5 = new Solution(LocalDateTime.now(), "intento_2_2", StatusExercise.PENDING, estudiante, ejercicio2, 2);
        sleep(5);
        Solution solucion6 = new Solution(LocalDateTime.now(), "intento_3_2", StatusExercise.PENDING, estudiante, ejercicio2, 1);

        //ESTUDIANTE2
        sleep(5);
        Solution solucion7 = new Solution(LocalDateTime.now(), "intento_1_3", StatusExercise.COMPLETED, estudiante2, ejercicio1, 2);
        sleep(5);
        Solution solucion8 = new Solution(LocalDateTime.now(), "intento_2_4", StatusExercise.COMPLETED, estudiante2, ejercicio2, 2);

        solutionRepository.save(solucion);
        solutionRepository.save(solucion2);
        solutionRepository.save(solucion3);
        solutionRepository.save(solucion4);
        solutionRepository.save(solucion5);
        solutionRepository.save(solucion6);
        solutionRepository.save(solucion7);
        solutionRepository.save(solucion8);

        EditableMethod metodosEjercicio1 = new EditableMethod("Pepe", 18);

        //FILES
        try (Stream<Path> filePathStream = Files.walk(Paths.get("E:/Escritorio/A"))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    File archivo = new File(String.valueOf(filePath));
                    try {
                        ExerciseFiles nuevoArchivo = switch (archivo.getName()) {
                            case "Solucion_alumno.java" ->
                                    new ExerciseFiles(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion, List.of(metodosEjercicio1));
                            case "Solucion2_alumno.java" ->
                                    new ExerciseFiles(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion2, List.of(metodosEjercicio1));
                            case "Solucion_alumno2.java" ->
                                    new ExerciseFiles(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion7, List.of(metodosEjercicio1));
                            case "Otro_ejercicio.java" ->
                                    new ExerciseFiles(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio4, solucion8, List.of(metodosEjercicio1));
                            default ->
                                    new ExerciseFiles(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, null, null);
                        };
                        efr.save(nuevoArchivo);
                    } catch (IOException e) {
                        throw new RuntimeException("Error con los archivos: " + e);
                    }
                }
            });
        }

//        File archivo = new File("E:/Escritorio/Proyectos/");
//        ExerciseFiles nuevoArchivo = new ExerciseFiles(null, archivo.getName(), archivo.getPath(), Files.readAllBytes(archivo.toPath()), ejercicio1);
//
//        efr.save(nuevoArchivo);

//        System.out.println("AQUI: " + new String(nuevoArchivo.getContent(), StandardCharsets.UTF_8));


        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Exercise>> violaciones = validator.validate(ejercicio1);
        System.out.println("Violaciones: " + violaciones.size() + ", texto: " + violaciones);

        List<ExerciseHomeDTO> lista = es.allExercisesWithSolutionByUserId("estudiante@hotmail.com");

        for (ExerciseHomeDTO e : lista) {
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
