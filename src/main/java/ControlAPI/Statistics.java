package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * A POJO class holding the statistics of a machine learning pipeline.
 */
public class Statistics implements CountableSerial {

    int pipeline;
    String protocol;
    double meanBufferSize;
    long models;
    long bytes;
    long blocks;
    long fitted;
    double score;
    List<Double> learningCurve;
    List<Long> lcx;

    public Statistics() {
        this(-1, "", 0, 0, 0, 0, 0, 0, null, null);
    }

    public Statistics(int pipeline) {
        this(pipeline, "", 0, 0, 0, 0, 0, 0, null, null);
    }

    public Statistics(int pipeline,
                      String protocol,
                      long models,
                      long bytes,
                      long blocks,
                      long fitted) {
        this(pipeline, protocol, 0, models, bytes, blocks, fitted, 0, null, null);
    }

    public Statistics(int pipeline,
                      String protocol,
                      long models,
                      long bytes,
                      long blocks,
                      long fitted,
                      List<Double> learningCurve,
                      List<Long> lcx) {
        this(pipeline, protocol, 0, models, bytes, blocks, fitted, 0, learningCurve, lcx);
    }

    public Statistics(int pipeline,
                      String protocol,
                      double meanBufferSize,
                      long models,
                      long bytes,
                      long blocks,
                      long fitted,
                      double score,
                      List<Double> learningCurve,
                      List<Long> lcx) {
        this.pipeline = pipeline;
        this.protocol = protocol;
        this.meanBufferSize = meanBufferSize;
        this.models = models;
        this.bytes = bytes;
        this.blocks = blocks;
        this.fitted = fitted;
        this.score = score;
        this.learningCurve = learningCurve;
        this.lcx = lcx;
    }

    public int getPipeline() {
        return pipeline;
    }

    public String getProtocol() {
        return protocol;
    }

    public double getMeanBufferSize() {
        return meanBufferSize;
    }

    public long getModels() {
        return models;
    }

    public long getBytes() {
        return bytes;
    }

    public long getBlocks() {
        return blocks;
    }

    public long getFitted() {
        return fitted;
    }

    public double getScore() {
        return score;
    }

    public List<Double> getLearningCurve() {
        return learningCurve;
    }

    public List<Long> getLCX() {
        return lcx;
    }

    public void setMeanBufferSize(double meanBufferSize) {
        this.meanBufferSize = meanBufferSize;
    }

    public void setPipeline(int pipeline) {
        this.pipeline = pipeline;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setModels(long models) {
        this.models = models;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public void setBlocks(long blocks) {
        this.blocks = blocks;
    }

    public void setFitted(long fitted) {
        this.fitted = fitted;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setLearningCurve(List<Double> learningCurve) {
        this.learningCurve = learningCurve;
    }

    public void setLCX(List<Long> lcx) {
        this.lcx = lcx;
    }

    @JsonIgnore
    public long updateLong(long memory, long update) {
        if (memory != Long.MAX_VALUE)
            if (memory < Long.MAX_VALUE - update)
                return memory + update;
            else
                return Long.MAX_VALUE;
        else
            return memory;
    }

    @JsonIgnore
    public double updateDouble(double memory, double update) {
        if (memory != Double.MAX_VALUE)
            if (memory < Double.MAX_VALUE - update)
                return memory + update;
            else
                return Double.MAX_VALUE;
        else
            return memory;
    }

    @JsonIgnore
    public void updateMeanBufferSize(double update) {
        meanBufferSize = updateDouble(meanBufferSize, update);
    }

    @JsonIgnore
    public void updateModels(long update) {
        models = updateLong(models, update);
    }

    @JsonIgnore
    public void updateModels() {
        updateModels(1);
    }

    @JsonIgnore
    public void updateBytes(long update) {
        bytes = updateLong(bytes, update);
    }

    @JsonIgnore
    public void updateBytes() {
        updateBytes(1);
    }

    @JsonIgnore
    public void updateBlocks(long update) {
        blocks = updateLong(blocks, update);
    }

    @JsonIgnore
    public void updateBlocks() {
        updateBlocks(1);
    }

    @JsonIgnore
    public void updateFitted(long update) {
        fitted = updateLong(fitted, update);
    }

    @JsonIgnore
    public void updateFitted() {
        updateFitted(1);
    }

    @JsonIgnore
    public void updateScore(double update) {
        score = updateDouble(score, update);
    }

    @JsonIgnore
    public void updateScore() {
        updateScore(1);
    }

    @JsonIgnore
    public void updateLearningCurve(List<Double> lC) {
        if ((learningCurve == null && lC != null) || (lC != null && lC.size() > learningCurve.size()))
            learningCurve = lC;
    }

    @JsonIgnore
    public void updateLCX(List<Long> lCx) {
        if ((lcx == null && lCx != null) || (lCx != null && lCx.size() > lcx.size()))
            lcx = lCx;
    }

    @JsonIgnore
    public void updateStats(Statistics stats) {
        assert (pipeline == stats.getPipeline() && protocol.equals(stats.getProtocol()));
        updateMeanBufferSize(stats.getMeanBufferSize());
        updateModels(stats.getModels());
        updateBytes(stats.getBytes());
        updateBlocks(stats.getBlocks());
        updateFitted(stats.getFitted());
        updateScore(stats.getScore());
        updateLearningCurve(stats.getLearningCurve());
        updateLCX(stats.getLCX());
    }

    @Override
    @JsonIgnore
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Non printable statistics.";
        }
    }

    @JsonIgnore
    public int getSize() {
        return 56 +
                ((protocol != null) ? 8 * protocol.length() : 0) +
                ((learningCurve != null) ? 8 * learningCurve.size() : 0);
    }

}
