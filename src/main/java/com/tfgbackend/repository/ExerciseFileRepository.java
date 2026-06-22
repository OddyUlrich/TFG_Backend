package com.tfgbackend.repository;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.ExerciseFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseFileRepository extends MongoRepository<ExerciseFile, Long> {


    /*
    - Explicación: Son los ficheros de la ultima solucion y templates del ejercicio.

    · Primero buscamos los ficheros relativos a un ejercicio concreto que pasamos por argumento
	· Luego hacemos un lookup para traer la información de las soluciones si las hay
	· Repetimos para, aquellos que tienen información de una solución, traigan a su vez la información del estudiante de dicha solución

	· Los arrays que traen siempre los pasamos a objetos con unwind y mantenemos los nulos y los empty (porque los templates nos interesan también)
	· Ahora buscamos las soluciones del alumno concreto y mantenemos los que no tienen solución como tal (vacíos mantenidos antes, los templates)

	· Hacemos 2 pipelines con facet:

        - Una para mantener lo de ahora Root
		- Otra para calcular el máximo valor de solution.updateTimestap, sabiendo así la última solución

	· Unwind para sacar las 2 pipelines de los arrays y pasarlos a objetos
	· Con project sacamos de ambos lo que nos interesa, uniendo el lastUpdate del máximo valor (última solución)
	· Si además solution no es un objeto lo convertimos a null para saber que es template fácilmente
	· Ahora solo dejamos aquellos que tengan la solución más reciente (últimos archivos de la última solución) y los nulos (templates)
    · Por último ordenamos la información y dejamos solo la información deseada

    */
    @Aggregation(pipeline = {
            "{$match: { $expr: {$eq: ['$exercise.$id',?0]}}}",

            "{$lookup: {" +
                    "from: 'solutions'," +
                    "localField: 'solution.$id'," +
                    "foreignField: '_id'," +
                    "as: 'solution'}}",

            "{$unwind: { path: '$solution', preserveNullAndEmptyArrays: true,}}",

            "{$lookup: {" +
            "      from: 'users', " +
            "      localField: 'solution.student.$id', " +
            "      foreignField: '_id'," +
            "      as: 'solution.student'}}",

            "{$unwind: {" +
            "      path: '$solution.student'," +
            "      preserveNullAndEmptyArrays: true}}",

            "{$match: {" +
            "        $or: [" +
            "          {" +
            "            $expr: {" +
            "              $eq: [" +
            "                '$solution.student._id'," +
            "                ?1]," +
            "            }," +
            "          }," +
            "          {" +
            "            $and: [" +
            "              {" +
            "                solution: {" +
            "                  $exists: true," +
            "                  $type: 'object'," +
            "                  $eq: {}," +
            "                }," +
            "              }," +
            "            ]," +
            "          }," +
            "        ]," +
            "      }," +
            "  }",
            "{$facet: {" +
            "      resultFromMainPipeline: [" +
            "        {" +
            "          $replaceRoot: {" +
            "            newRoot: \"$$ROOT\"," +
            "          }," +
            "        }," +
            "      ]," +
            "      maxUpdate: [" +
            "        {" +
            "          $group: {" +
            "            _id: null," +
            "            maxValor: {" +
            "              $max: '$solution.updateTimestamp'," +
            "            }," +
            "          }," +
            "        }," +
            "      ]," +
            "    }," +
            "  }",

            "{$unwind: { path: '$resultFromMainPipeline', },}",

            "{$unwind: { path: '$maxUpdate', },}",

            "{$project: {" +
            "      id: '$resultFromMainPipeline._id'," +
            "      name: '$resultFromMainPipeline.name'," +
            "      path: '$resultFromMainPipeline.path'," +
            "      binaryText: '$resultFromMainPipeline.binaryText'," +
            "      exercise:" +
            "        '$resultFromMainPipeline.exercise'," +
            "      solution: {" +
            "        $cond: {" +
            "          if: {" +
            "            $and: [" +
            "              {" +
            "                $eq: [" +
            "                  {" +
            "                    $type:" +
            "                      '$resultFromMainPipeline.solution'," +
            "                  }," +
            "                  'object'," +
            "                ]," +
            "              }," +
            "              {" +
            "                $ne: [" +
            "                  '$resultFromMainPipeline.solution'," +
            "                  {}," +
            "                ]," +
            "              }," +
            "            ]," +
            "          }," +
            "          then: '$resultFromMainPipeline.solution'," +
            "          else: null," +
            "        }," +
            "      }," +
            "      editableMethods:" +
            "        '$resultFromMainPipeline.editableMethods'," +
            "      lastUpdate: '$maxUpdate.maxValor'," +
            "    }," +
            "  }",

            "  {$match: {" +
            "      $or: [" +
            "        {" +
            "          $expr: {" +
            "            $eq: [" +
            "              '$solution.updateTimestamp'," +
            "              '$lastUpdate'," +
            "            ]," +
            "          }," +
            "        }," +
            "        {" +
            "          $and: [" +
            "            {" +
            "              solution: {" +
            "                $exists: true," +
            "                $eq: null," +
            "              }," +
            "            }," +
            "          ]," +
            "        }," +
            "      ]," +
            "    }," +
            "  }",

            "  {$project: {" +
            "      id: 1," +
            "      name: 1," +
            "      path: 1," +
            "      binaryText: 1," +
            "      idFromSolution: '$solution._id'," +
            "      editableMethods: 1," +
            "    }" +
            "  }"
})
    Optional<List<ExerciseFileDTO>> exerciseFilesAndLastSolutionByIdAndStudent(ObjectId studentId, ObjectId exerciseId);

    @Aggregation(pipeline = {
            "{ $match: {" +
                    "  $and: [" +
                    "    {" +
                    "      $expr: {" +
                    "        $eq: [" +
                    "          '$exercise.$id', ?0" +
                    "        ]" +
                    "      }" +
                    "    }," +
                    "    {" +
                    "      $and: [" +
                    "        {" +
                    "          solution: {" +
                    "            $exists: false" +
                    "          }" +
                    "        }" +
                    "      ]" +
                    "    }" +
                    "  ]" +
                    "} }"
    })
    Optional<List<ExerciseFileDTO>> templateFilesByExerciseId(ObjectId exerciseId);

    @Aggregation(pipeline = {
            "{$match: {" +
                    "$and: [{" +
                    "          'name': ?0" +
                    "        }," +
                    "        {" +
                    "          'solution.$id': ?1" +
                    "        }" +
                    "]" +
                    "}}",
    })
    ExerciseFile findByNameAndSolutionId(String name, ObjectId solutionId);
}
