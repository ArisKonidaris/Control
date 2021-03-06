package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * A serializable POJO class representing a response
 * from the Online Machine Leaning component.
 */
public class QueryResponse implements CountableSerial {

    /**
     * The unique id associated with this request. This is the answer to a "Query" request
     * to the Online Machine Learning component. This responseId should be equal to the
     * corresponding requestId that it answers.
     */
    public long responseId;

    /**
     * This is the id of a response divided into multiple responses due to its big size.
     */
    public long subResponseId;

    /** The unique id of the ML Pipeline that provided this answer. */
    public int mlpId;

    /** A list of preprocessors. This could be empty. */
    public List<PreprocessorPOJO> preprocessors;

    /** A single learner for the ML Pipeline. This should not be empty. */
    public LearnerPOJO learner;

    /** The distributed algorithm used to train a ML Pipeline in parallel. */
    public String protocol;

    /** The number of fitted data points. */
    public Long dataFitted;

    /** The average loss incurred from the most recently fitted data. */
    public Double loss;

    /** The total cumulative loss of the learner. */
    public Double cumulativeLoss;

    /** The query of the Machine Leaning algorithm. */
    public Double score;

    public QueryResponse() {
    }

    public QueryResponse(long responseId,
                         int mlpId,
                         List<PreprocessorPOJO> preprocessors,
                         LearnerPOJO learner,
                         String protocol,
                         Long dataFitted,
                         Double loss,
                         Double cumulativeLoss,
                         Double score) {
        this.responseId = responseId;
        this.subResponseId = 0;
        this.mlpId = mlpId;
        this.preprocessors = preprocessors;
        this.learner = learner;
        this.protocol = protocol;
        this.dataFitted = dataFitted;
        this.loss = loss;
        this.cumulativeLoss = cumulativeLoss;
        this.score = score;
    }

    public QueryResponse(long responseId,
                         long subResponseId,
                         int mlpId,
                         List<PreprocessorPOJO> preprocessors,
                         LearnerPOJO learner,
                         String protocol,
                         Long dataFitted,
                         Double loss,
                         Double cumulativeLoss,
                         Double score) {
        this.responseId = responseId;
        this.subResponseId = subResponseId;
        this.mlpId = mlpId;
        this.preprocessors = preprocessors;
        this.learner = learner;
        this.protocol = protocol;
        this.dataFitted = dataFitted;
        this.loss = loss;
        this.cumulativeLoss = cumulativeLoss;
        this.score = score;
    }

    public int getMlpId() {
        return mlpId;
    }

    public void setMlpId(int mlpId) {
        this.mlpId = mlpId;
    }

    public List<PreprocessorPOJO> getPreprocessors() {
        return preprocessors;
    }

    public void setPreprocessors(List<PreprocessorPOJO> preprocessors) {
        this.preprocessors = preprocessors;
    }

    public LearnerPOJO getLearner() {
        return learner;
    }

    public void setLearner(LearnerPOJO learner) {
        this.learner = learner;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public long getSubResponseId() {
        return subResponseId;
    }

    public void setSubResponseId(long subResponseId) {
        this.subResponseId = subResponseId;
    }

    public Long getDataFitted() {
        return dataFitted;
    }

    public void setDataFitted(Long dataFitted) {
        this.dataFitted = dataFitted;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Double getCumulativeLoss() {
        return cumulativeLoss;
    }

    public void setCumulativeLoss(Double cumulativeLoss) {
        this.cumulativeLoss = cumulativeLoss;
    }

    @JsonIgnore
    public int getPreprocessorsSize() {
        int ppSize = 0;
        for (PreprocessorPOJO pp : preprocessors) {
            ppSize += pp.getSize();
        }
        return ppSize;
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable response.";
        }
    }

    @JsonIgnore
    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return 20 +
                ((preprocessors != null) ? getPreprocessorsSize() : 0) +
                ((learner != null) ? learner.getSize() : 0) +
                ((protocol != null) ? 8 * protocol.length() : 0) +
                ((dataFitted != null) ? 8 : 0) +
                ((loss != null) ? 8 : 0) +
                ((cumulativeLoss != null) ? 8 : 0) +
                ((score != null) ? 8 : 0);
    }

}
