package ControlAPI;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A serializable POJO class representing a transformer (e.i. Preprocessor or Learner).
 */
public class TransformerPOJO {

    public String name; // The name of the transformer.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> hyperparameters; // The hyper parameters of the preprocessor.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> parameters; // The parameters of the preprocessor.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> data_structure; // The data structure of the preprocessor.

    public TransformerPOJO() {
    }

    public TransformerPOJO(String name,
                           Map<String, Object> hyperparameters,
                           Map<String, Object> parameters,
                           Map<String, Object> data_structure) {
        this.name = name;
        this.hyperparameters = hyperparameters;
        this.parameters = parameters;
        this.data_structure = data_structure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getHyperparameters() {
        return hyperparameters;
    }

    public void setHyperparameters(Map<String, Object> hyperparameters) {
        this.hyperparameters = hyperparameters;
    }

    public Map<String, Object> getData_structure() {
        return data_structure;
    }

    public void setData_structure(Map<String, Object> data_structure) {
        this.data_structure = data_structure;
    }
}