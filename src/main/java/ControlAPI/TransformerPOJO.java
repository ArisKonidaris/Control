package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;

/**
 * A serializable POJO class representing a transformer (e.i. Preprocessor or Learner).
 */
public class TransformerPOJO implements CountableSerial {

    public String name; // The name of the transformer.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> hyperParameters; // The hyper parameters of the preprocessor.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> parameters; // The parameters of the preprocessor.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> dataStructure; // The data structure of the preprocessor.

    public TransformerPOJO() {
    }

    public TransformerPOJO(String name,
                           Map<String, Object> hyperParameters,
                           Map<String, Object> parameters,
                           Map<String, Object> dataStructure) {
        this.name = name;
        this.hyperParameters = hyperParameters;
        this.parameters = parameters;
        this.dataStructure = dataStructure;
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

    public Map<String, Object> getHyperParameters() {
        return hyperParameters;
    }

    public void setHyperParameters(Map<String, Object> hyperParameters) {
        this.hyperParameters = hyperParameters;
    }

    public Map<String, Object> getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(Map<String, Object> dataStructure) {
        this.dataStructure = dataStructure;
    }

    @JsonIgnore
    public int getMapSize(Map<String, Object> map) {
        int mapSize = 0;
        for (Map.Entry<String, Object> next : map.entrySet()) {
            mapSize += ((int) (8L * next.getKey().length())) + getObjectSize(next.getValue());
        }
        return mapSize;
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return ((name != null) ? 8 * name.length() : 0) +
                ((hyperParameters != null) ? getMapSize(hyperParameters) : 0) +
                ((parameters != null) ? getMapSize(parameters) : 0) +
                ((dataStructure != null) ? getMapSize(dataStructure) : 0);
    }

}