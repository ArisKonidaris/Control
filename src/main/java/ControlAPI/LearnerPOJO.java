package ControlAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * A serializable POJO class representing a machine learning algorithm (e.i. Passive Aggressive Classifier).
 */
public class LearnerPOJO extends TransformerPOJO {

    public LearnerPOJO() {
        super();
    }

    public LearnerPOJO(String name,
                       Map<String, Object> hyperparameters,
                       Map<String, Object> parameters,
                       Map<String, Object> data_structure) {
        super(name, hyperparameters, parameters, data_structure);
    }

    @Override
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable learner.";
        }
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}