package ControlAPI;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A serializable POJO class representing a data point for training or predicting.
 */
public class DataInstance implements Validatable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id; // A unique id for a data point.

    public List<Double> numericalFeatures; // The numerical features of the data point.

    public List<Integer> discreteFeatures; // The discrete features of the data point.

    public List<String> categoricalFeatures; // The categorical features of the data point.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Double target; // The label/target of the data point.

    public String operation; // The operation of this data point.

    private Map stream_metadata = new HashMap<String,Object>(); // Metadata about the stream of the data point.

    @JsonIgnore
    public KafkaMetadata metadata;

    public DataInstance() {
    }

    public DataInstance(List<Double> numericFeatures, Double target) {
        this(null, numericFeatures, null, null, target, "training");
    }

    public DataInstance(Long id,
                        List<Double> numericalFeatures,
                        List<Integer> discreteFeatures,
                        List<String> categoricalFeatures,
                        Double target,
                        String operation) {
        this.id = id;
        this.numericalFeatures = numericalFeatures;
        this.discreteFeatures = discreteFeatures;
        this.categoricalFeatures = categoricalFeatures;
        this.target = target;
        this.operation = operation;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<Double> getNumericalFeatures() {
        return numericalFeatures;
    }

    public void setNumericalFeatures(List<Double> numericFeatures) {
        this.numericalFeatures = numericFeatures;
    }

    public List<Integer> getDiscreteFeatures() {
        return discreteFeatures;
    }

    public void setDiscreteFeatures(List<Integer> discreteFeatures) {
        this.discreteFeatures = discreteFeatures;
    }

    public void setCategoricalFeatures(List<String> categoricalFeatures) {
        this.categoricalFeatures = categoricalFeatures;
    }

    public List<String> getCategoricalFeatures() {
        return categoricalFeatures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map getStreamMetadata() {
        return stream_metadata;
    }

    public void setStreamMetadata(Map metadata) {
        this.stream_metadata = metadata;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        stream_metadata.put(name, value);
    }

    @JsonIgnore
    public KafkaMetadata getMetadata() {
        return metadata;
    }

    @JsonIgnore
    public void setMetadata(KafkaMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable data point.";
        }
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    @JsonIgnore
    public boolean isValid() {
        if (operation == null || (!operation.equals("training") && !operation.equals("forecasting"))) return false;
        if (
                (numericalFeatures == null || numericalFeatures.size() == 0) &&
                        (discreteFeatures == null || discreteFeatures.size() == 0) &&
                        (categoricalFeatures == null || categoricalFeatures.size() == 0)
        ) return false;
        if (operation.equals("forecasting") && target != null) return false;
        return true;
    }

    @JsonIgnore
    public void setMetadata(String topic, Integer partition, Long key, Long offset, Long timestamp) {
        metadata = new KafkaMetadata(topic, partition, key, offset, timestamp);
    }

}
