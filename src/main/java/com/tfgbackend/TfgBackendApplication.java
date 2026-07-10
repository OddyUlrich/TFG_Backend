package com.tfgbackend;

import com.tfgbackend.llm.RuleProcessorAiService;
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
import java.time.Month;
import java.util.Collections;
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
    private final SolutionRepository sr;
    private final ExerciseFileRepository efr;
    private final RulesRepository rr;

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    private final RuleProcessorAiService ruleProcessorAiService;

    @Autowired
    public TfgBackendApplication(UserRepository ur, ExerciseRepository er, ExerciseBatteryRepository ebr, ExerciseService es, SolutionRepository sr, TagRepository tr, ExerciseFileRepository efr, RulesRepository rr, RuleProcessorAiService ruleProcessorAiService) {
        this.ur = ur;
        this.er = er;
        this.es = es;
        this.ebr = ebr;
        this.tr = tr;
        this.sr = sr;
        this.efr = efr;
        this.rr = rr;
        this.ruleProcessorAiService = ruleProcessorAiService;
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
        sr.deleteAll();
        rr.deleteAll();

        ObjectId batteryId1 = new ObjectId("635981f6e40f61599a000061");
        ObjectId batteryId2 = new ObjectId("635981f6e40f61599a000062");
        ObjectId batteryId3 = new ObjectId("635981f6e40f61599a000063");
        ObjectId batteryId4 = new ObjectId("635981f6e40f61599a000064");
        ObjectId batteryId5 = new ObjectId("635981f6e40f61599a000065");
        ObjectId batteryId6 = new ObjectId("635981f6e40f61599a000066");
        ObjectId batteryId7 = new ObjectId("635981f6e40f61599a000067");
        ObjectId batteryId8 = new ObjectId("635981f6e40f61599a000068");
        ObjectId batteryId9 = new ObjectId("635981f6e40f61599a000069");

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
        ObjectId exerciseId12 = new ObjectId("635981f6e40a61599e000072");
        ObjectId exerciseId13 = new ObjectId("635981f6e40a61599e000073");

        ObjectId profesorId = new ObjectId("635981f6e40b61599e000064");
        ObjectId estudianteID = new ObjectId("635981f6e40c61599e000064");
        ObjectId estudianteID2 = new ObjectId("635981f6e40c61599e000062");

        ObjectId tag1ID = new ObjectId();
        ObjectId tag2ID = new ObjectId();
        ObjectId tag3ID = new ObjectId();
        ObjectId tag4ID = new ObjectId();
        ObjectId tag5ID = new ObjectId();
        ObjectId tag6ID = new ObjectId();
        ObjectId tag7ID = new ObjectId();
        ObjectId tag8ID = new ObjectId();
        ObjectId tag9ID = new ObjectId();
        ObjectId tag10ID = new ObjectId();


        User profesor = new User(profesorId.toString(), "profesor", bcrypt.encode("password"), "profesor@hotmail.com", LocalDateTime.now(), List.of(Rol.TEACHER), List.of());
        User estudiante = new User(estudianteID.toString(), "estudiante", bcrypt.encode("password"), "estudiante@hotmail.com", LocalDateTime.now(), List.of(Rol.STUDENT), List.of());
        User estudiante2 = new User(estudianteID2.toString(), "estudiante2", bcrypt.encode("password"), "estudiante2@hotmail.com", LocalDateTime.now(), List.of(Rol.STUDENT), List.of());
        //TODO ACTUALIZAR LA LISTA DE ASIGNATURAS DEL ESTUDIANTE

        ur.save(estudiante);
        ur.save(estudiante2);
        ur.save(profesor);

        Tag herencia = new Tag(tag1ID.toString(), "Herencia");
        Tag getter = new Tag(tag2ID.toString(), "Getter");
        Tag setter = new Tag(tag3ID.toString(), "Setter");
        Tag poliformismo = new Tag(tag4ID.toString(), "Poliformismo");
        Tag clases = new Tag(tag5ID.toString(), "Clases");
        Tag bucles  = new Tag(tag6ID.toString(), "Bucles");
        Tag recursividad = new Tag(tag7ID.toString(), "Recursividad");
        Tag exepciones = new Tag(tag8ID.toString(), "Excepciones");
        Tag operadores = new Tag(tag9ID.toString(), "Operadores");
        Tag interfaces = new Tag(tag10ID.toString(), "Interfaces");

        tr.save(herencia);
        tr.save(getter);
        tr.save(setter);
        tr.save(poliformismo);
        tr.save(clases);
        tr.save(bucles);
        tr.save(recursividad);
        tr.save(exepciones);
        tr.save(operadores);
        tr.save(interfaces);

        ExerciseBattery bateria1 = new ExerciseBattery(batteryId1.toString(), "Bateria de Herencia");
        ExerciseBattery bateria2 = new ExerciseBattery(batteryId2.toString(), "Bateria de Poliformismo");
        ExerciseBattery bateria3 = new ExerciseBattery(batteryId3.toString(), "Bateria de Clases");
        ExerciseBattery bateria4 = new ExerciseBattery(batteryId4.toString(), "Bateria de Encapsulación");
        ExerciseBattery bateria5 = new ExerciseBattery(batteryId5.toString(), "Bateria de Abstracción");
        ExerciseBattery bateria6 = new ExerciseBattery(batteryId6.toString(), "Bateria de Interfaces");
        ExerciseBattery bateria7 = new ExerciseBattery(batteryId7.toString(), "Bateria de Ejemplo 1");
        ExerciseBattery bateria8 = new ExerciseBattery(batteryId8.toString(), "Bateria de Ejemplo 2");
        ExerciseBattery bateria9 = new ExerciseBattery(batteryId9.toString(), "Bateria de Ejemplo 3");

        ebr.save(bateria1);
        ebr.save(bateria2);
        ebr.save(bateria3);
        ebr.save(bateria4);
        ebr.save(bateria5);
        ebr.save(bateria6);
        ebr.save(bateria7);
        ebr.save(bateria8);
        ebr.save(bateria9);

        Exercise ejercicio1 = new Exercise(exerciseId1.toString(), "Ejercicio 1", "", bateria1, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.now());
        Exercise ejercicio3 = new Exercise(exerciseId3.toString(), "Ejercicio 3", "", bateria1, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2015,
                Month.MAY, 29, 19, 30, 40));
        Exercise ejercicio4 = new Exercise(exerciseId4.toString(), "Ejercicio 4", "", bateria1, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.now());
        Exercise ejercicio5 = new Exercise(exerciseId5.toString(), "Ejercicio 5", "", bateria1, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2025,
                Month.MAY, 29, 22, 49, 1));
        Exercise ejercicio6 = new Exercise(exerciseId6.toString(), "Ejercicio 6", "", bateria2, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2015,
                Month.MAY, 29, 19, 30, 40));
        Exercise ejercicio7 = new Exercise(exerciseId7.toString(), "Ejercicio 7", "", bateria4, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2015,
                Month.MAY, 29, 19, 30, 40));
        Exercise ejercicio8 = new Exercise(exerciseId8.toString(), "Ejercicio 8", "", bateria2, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.now());
        Exercise ejercicio9 = new Exercise(exerciseId9.toString(), "Ejercicio 9", "", bateria2, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.now());
        Exercise ejercicio10 = new Exercise(exerciseId10.toString(), "Ejercicio 10", "", bateria3, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.now());
        Exercise ejercicio11 = new Exercise(exerciseId11.toString(), "Ejercicio 11", "", bateria3, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2015,
                Month.MAY, 29, 19, 30, 40));
        Exercise ejercicio2 = new Exercise(exerciseId2.toString(), "Ejercicio 2", "", bateria2, Collections.emptyList(), List.of(herencia, getter), profesor, LocalDateTime.of(2015,
                Month.MAY, 29, 19, 30, 40));

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

        sr.save(solucion);
        sr.save(solucion2);
        sr.save(solucion3);
        sr.save(solucion4);
        sr.save(solucion5);
        sr.save(solucion6);
        sr.save(solucion7);
        sr.save(solucion8);

        EditableMethod metodosEjercicio1 = new EditableMethod("Pepe", -1, -1);

        //FILES
        try (Stream<Path> filePathStream = Files.walk(Paths.get("E:/Escritorio/A"))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    File archivo = new File(String.valueOf(filePath));
                    try {
                        ExerciseFile nuevoArchivo = switch (archivo.getName()) {
                            case "Solucion_alumno.java" ->
                                    new ExerciseFile(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion, List.of(metodosEjercicio1));
                            case "Solucion2_alumno.java" ->
                                    new ExerciseFile(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion2, List.of(metodosEjercicio1));
                            case "Solucion_alumno2.java" ->
                                    new ExerciseFile(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, solucion7, List.of(metodosEjercicio1));
                            case "Otro_ejercicio.java" ->
                                    new ExerciseFile(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio2, solucion8, List.of(metodosEjercicio1));
                            default ->
                                    new ExerciseFile(archivo.getName(), archivo.getPath().replace("\\","/"), Files.readAllBytes(archivo.toPath()), ejercicio1, null, null);
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

        List<ExerciseHomeDTO> lista = es.allExercisesWithLastSolutionsByUserId("estudiante@hotmail.com", null, null, null);

        for (ExerciseHomeDTO e : lista) {
            System.out.println("ATIENDE: EjercicioSolution: " + e.getName() + " Status: " + e.getStatusSolution()  + " Numero de errores:" + e.getNumberErrorsSolution());
        }

        /*DBREF -> Ejercicio.Bateria ----> lazy = true ----> NO SE PUEDE GUARDAR EJERCICIO
         * Ref.Manual -> Ejercicio.linkBateria -> EJERCICO SE GUARDA -------> EjercicioBateriaDTO -----> NO SE PUEDE GUARDAR*/

        // fetch all customers
        /*System.out.println("\nCustomers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : ur.findAll()) {
            System.out.println(user);
        }
        System.out.println();


        System.out.println("-------------------------------");
        // Llamada directa a Gemini. Devuelve el DTO automáticamente mapeado
        Result<ProcessedRulesDTO> result = ruleProcessorAiService.parseRules("""
                Quiero que utilicen un iterator para evitar errores de eliminación en una variable ArrayList llamada "aviones". Deben multiplicar "aviones" por "numero de viajes" para obtener 30 en "vuelosTotales. No quiero que bajo ningún concepto hagan bucles infinitos ni que tengan bucles anidados ni nada que supere O(n)"
                """);

        //ProcessedRulesDTO dto = result.content();
        List<Rule> requiredRules = result.content().requiredRules();
        List<Rule> forbiddenRules = result.content().forbiddenRules();

        System.out.println("Reglas requeridas: " + requiredRules);
        System.out.println("Reglas prohibidas: " + forbiddenRules);*/

    }
}
