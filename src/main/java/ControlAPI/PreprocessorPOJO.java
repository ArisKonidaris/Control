package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * A serializable POJO class representing a preprocessor (i.e. Polynomial Features).
 */
public class PreprocessorPOJO extends TransformerPOJO {

    public PreprocessorPOJO() {
        super();
    }

    public PreprocessorPOJO(String name,
                            Map<String, Object> hyperParameters,
                            Map<String, Object> parameters,
                            Map<String, Object> dataStructure) {
        super(name, hyperParameters, parameters, dataStructure);
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable preprocessor.";
        }
    }

    @JsonIgnore
    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}