package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize;

/**
 * A serializable POJO class representing a request to the Online Machine Leaning component request Flink.
 */
public class Request implements Validatable, CountableSerial {

    /** The unique id used to identify an ML Pipeline. */
    public int id;

    /** The request type. */
    public String request;

    /** A list of preprocessors. This could be empty. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PreprocessorPOJO> preProcessors;

    /** A single learner for the ML Pipeline. This should not be empty. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LearnerPOJO learner;

    /** The unique id associated with this request. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long requestId;

    /** A helper map containing information about the training configuration. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> trainingConfiguration;

    /** Metadata about the Kafka cluster that delivered this request. */
    public KafkaMetadata metadata;

    public Request() {
    }

    public Request(int id, Long requestId) {
        this.id = id;
        this.requestId = requestId;
        this.request = "Query";
    }

    public Request(int id,
                   String request,
                   List<PreprocessorPOJO> preProcessors,
                   LearnerPOJO learner,
                   Long requestId,
                   Map<String, Object> trainingConfiguration) {
        this.id = id;
        this.request = request;
        this.preProcessors = preProcessors;
        this.learner = learner;
        this.requestId = requestId;
        this.trainingConfiguration = trainingConfiguration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<PreprocessorPOJO> getPreProcessors() {
        return preProcessors;
    }

    public void setPreProcessors(List<PreprocessorPOJO> preProcessors) {
        this.preProcessors = preProcessors;
    }

    public LearnerPOJO getLearner() {
        return learner;
    }

    public void setLearner(LearnerPOJO learner) {
        this.learner = learner;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getTrainingConfiguration() {
        return trainingConfiguration;
    }

    public void setTrainingConfiguration(Map<String, Object> trainingConfiguration) {
        this.trainingConfiguration = trainingConfiguration;
    }

    public KafkaMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(KafkaMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    @JsonIgnore
    public void setMetadata(String topic, Integer partition, Long key, Long offset, Long timestamp) {
        metadata = new KafkaMetadata(topic, partition, key, offset, timestamp);
    }

    @JsonIgnore
    public int getMapSize(Map<String, Object> map) {
        int mapSize = 0;
        for (Map.Entry<String, Object> next : map.entrySet()) {
            mapSize += ((int) (8L * next.getKey().length())) + getObjectSize(next.getValue());
        }
        return mapSize;
    }

    @JsonIgnore
    public int getPreprocessorsSize() {
        int ppSize = 0;
        for (PreprocessorPOJO pp : preProcessors) {
            ppSize += pp.getSize();
        }
        return ppSize;
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        if (id < 0) return false;
        if (request == null ||
                (!request.equals("Create") &&
                        !request.equals("Update") &&
                        !request.equals("Delete") &&
                        !request.equals("Query"))
        ) return false;
        if (request.equals("Create") && learner == null) return false;
        if (request.equals("Update") && preProcessors == null && learner == null) return false;
        if (learner != null && learner.name == null) return false;
        if (preProcessors != null) for (PreprocessorPOJO p : preProcessors) if (p.name == null) return false;
        if (trainingConfiguration != null &&
                trainingConfiguration.containsKey("protocol") &&
                !trainingConfiguration.get("protocol").equals("Asynchronous") &&
                !trainingConfiguration.get("protocol").equals("Synchronous") &&
                !trainingConfiguration.get("protocol").equals("FGM")
        ) return false;
        try {
            if (trainingConfiguration != null &&
                    trainingConfiguration.containsKey("miniBatchSize") &&
                    (
                            (
                                    (trainingConfiguration.get("miniBatchSize") instanceof Double) &&
                                    ((Double) trainingConfiguration.get("miniBatchSize")) <= 0D
                            ) ||
                            (
                                    (trainingConfiguration.get("miniBatchSize") instanceof Integer) &&
                                    ((int) trainingConfiguration.get("miniBatchSize")) <= 0
                            )
                    )
            ) return false;
        } catch (Exception e) {
            return false;
        }
        if (request.equals("Query") && (requestId == null || requestId < 0)) return false;
        return true;
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return toJsonString();
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            return "Non printable request.";
        }
    }

    @JsonIgnore
    public String toJsonString() throws com.fasterxml.jackson.core.JsonProcessingException {
        return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return 4 +
                ((request != null) ? 8 * request.length() : 0) +
                ((preProcessors != null) ? getPreprocessorsSize() : 0) +
                ((learner != null) ? learner.getSize() : 0) +
                ((requestId != null) ? 8 : 0) +
                ((trainingConfiguration != null) ? getMapSize(trainingConfiguration) : 0) +
                ((metadata != null) ? metadata.getSize() : 0);
    }

}
