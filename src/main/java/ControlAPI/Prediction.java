package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A POJO class for a prediction.
 */
public class Prediction implements CountableSerial {

    Integer mlPipeline;
    DataInstance dataPoint;
    Double prediction;

    public Prediction() {
    }

    public Prediction(Integer mlPipeline, DataInstance dataPoint, Double prediction) {
        this.mlPipeline = mlPipeline;
        this.dataPoint = dataPoint;
        this.prediction = prediction;
    }

    public Integer getMlPipeline() {
        return mlPipeline;
    }

    public void setMlPipeline(Integer mlPipeline) {
        this.mlPipeline = mlPipeline;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }

    public DataInstance getDataPoint() {
        return dataPoint;
    }

    public void setDataInstanceId(DataInstance dataPoint) {
        this.dataPoint = dataPoint;
    }

    public void setDataPoint(DataInstance dataPoint) {
        this.dataPoint = dataPoint;
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return toJsonString();
        } catch (JsonProcessingException e) {
            return "Non printable prediction.";
        }
    }

    @JsonIgnore
    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return ((mlPipeline != null) ? 4 : 0) +
                ((mlPipeline != null) ? dataPoint.getSize() : 0) +
                ((prediction != null) ? 8 : 0);
    }

}
