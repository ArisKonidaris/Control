package ControlAPI;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;

/**
 * A serializable POJO class representing a data point for training or predicting.
 */
public class DataInstance implements Validatable, CountableSerial {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id; // A unique id for a data point.

    public List<Double> numericalFeatures; // The numerical features of the data point.

    public List<Integer> discreteFeatures; // The discrete features of the data point.

    public List<String> categoricalFeatures; // The categorical features of the data point.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Double target; // The label/target of the data point.

    public String operation; // The operation of this data point.

    private Map<String, Object> streamMetadata = new HashMap<>(); // Metadata about the stream of the data point.

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

    public Map<String, Object> getStreamMetadata() {
        return streamMetadata;
    }

    public void setStreamMetadata(Map<String, Object> metadata) {
        this.streamMetadata = metadata;
    }

    public KafkaMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(KafkaMetadata metadata) {
        this.metadata = metadata;
    }

    @JsonIgnore
    public void setMetadata(String topic, Integer partition, Long key, Long offset, Long timestamp) {
        metadata = new KafkaMetadata(topic, partition, key, offset, timestamp);
    }

    @JsonAnySetter
    @JsonIgnore
    public void set(String name, Object value) {
        streamMetadata.put(name, value);
    }

    @JsonIgnore
    public int getMetadataSize() {
        int metadataSize = 0;
        if (streamMetadata != null ) {
            for (Map.Entry<String, Object> next : streamMetadata.entrySet()) {
                metadataSize += ((int) (8L * next.getKey().length())) + getObjectSize(next.getValue());
            }
        }
        return metadataSize;
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return ((id != null) ? 8 : 0) +
                ((numericalFeatures != null) ? 8 * numericalFeatures.size() : 0) +
                ((discreteFeatures != null) ? 8 * discreteFeatures.size() : 0) +
                ((categoricalFeatures != null) ? 8 * categoricalFeatures.size() : 0) +
                ((target != null) ? 8 : 0) +
                ((operation != null) ? 8 * operation.length() : 0) +
                ((streamMetadata != null) ? getMetadataSize() : 0) +
                ((metadata != null) ? metadata.getSize() : 0);
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable data point.";
        }
    }

    @JsonIgnore
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

}
