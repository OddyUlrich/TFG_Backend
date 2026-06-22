package com.tfgbackend.llm;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.EditableMethod;
import com.tfgbackend.model.Rule;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService
public interface CorrectorAiService {

    @SystemMessage("""
        You are a Java code checker for programming exercises created by a teacher for a group of students.
        The program must run correctly and without errors, although you should allow minor typos such as 'atring' instead of 'string' by simply issuing a mild warning.
        Grammar or logic errors should be pointed out to the student briefly, clearly, and concisely, without the need to explain them. Just indicate them.
        If you receive a set of rules, the student’s code must check for and comply with them. If this is not the case, the student will be notified of this as just another error.
        Under no circumstances should you provide the answer to the exercise or suggest how it should be solved; simply perform a neutral correction.
        All of the student’s code must be written in Java and they can only write in the "Editable Methods" selected by the professor. You will only evaluate those methods.
        The language of the evaluation, feedback, and errors inside the EvaluationResponse must be Spanish.""")
    @UserMessage("""
        These are the files for the assignment to be evaluated: {{files}}
        These are the names of the methods, along with the names of the files they are located in, that the student can edit: {{methods}}
        These are the rules specifying what students must do or add: {{required}}
        These are the rules specifying which methods, variables, or concepts are strictly prohibited for the student: {{forbidden}}
        """)
    EvaluationResponse evaluate(@V("files") List<ExerciseFileDTO> files,
                                @V("methods")List<EditableMethod> methods,
                                @V("required") List<Rule> required,
                                @V("forbidden") List<Rule> forbidden);
}
