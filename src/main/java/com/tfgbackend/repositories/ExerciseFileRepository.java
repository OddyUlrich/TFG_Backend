package com.tfgbackend.repositories;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.ExerciseFiles;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseFileRepository extends MongoRepository<ExerciseFiles, Long> {

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
            "      content: '$resultFromMainPipeline.content'," +
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
            "      contentBinary: '$content'," +
            "      idFromSolution: '$solution._id'," +
            "      editableMethods: 1," +
            "    }" +
            "  }"
})
    Optional<List<ExerciseFileDTO>> exerciseFilesAndSolutionByIdAndStudent(ObjectId studentId, ObjectId exerciseId);

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
    ExerciseFiles findByNameAndSolutionId(String name, ObjectId solutionId);
}
