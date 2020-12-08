package ControlAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * A serializable POJO class representing a response
 * from the Online Machine Leaning component.
 */
public class QueryResponse implements Serializable {

    /**
     * The unique id associated with this request. This is the answer to a "Query" request
     * to the Online Machine Learning component. This responseId should be equal to the
     * corresponding requestId that it answers.
     */
    public long responseId;

    public int id; // The unique id of the ML Pipeline that provided this answer.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<PreprocessorPOJO> preprocessors; // A list of preprocessors. This could be empty.

    public LearnerPOJO learner; // A single learner for the ML Pipeline. This should not be empty.

    public String protocol; // The distributed algorithm used to train a ML Pipeline in parallel.

    public Long dataFitted; // The number of fitted data points.

    public Double loss; // The average loss incurred from the most recently fitted data.

    public Double cumulativeLoss; // The total cumulative loss of the learner.

    public Double score; // The query of the Machine Leaning algorithm.

    public QueryResponse() {
    }

    public QueryResponse(long responseId,
                         int id,
                         List<PreprocessorPOJO> preprocessors,
                         LearnerPOJO learner,
                         String protocol,
                         Long dataFitted,
                         Double loss,
                         Double cumulativeLoss,
                         Double score) {
        this.responseId = responseId;
        this.id = id;
        this.preprocessors = preprocessors;
        this.learner = learner;
        this.protocol = protocol;
        this.dataFitted = dataFitted;
        this.loss = loss;
        this.cumulativeLoss = cumulativeLoss;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable response.";
        }
    }

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}
