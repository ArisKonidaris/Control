package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Statistics implements CountableSerial {

    int pipeline;
    String protocol;
    double meanBufferSize;
    long models;
    long bytes;
    long blocks;
    long fitted;
    double score;

    public Statistics() {
        this(-1, "", 0, 0, 0, 0, 0, 0);
    }

    public Statistics(int pipeline) {
        this(pipeline, "", 0, 0, 0, 0, 0, 0);
    }

    public Statistics(int pipeline, String protocol, long models, long bytes, long blocks, long fitted) {
        this(pipeline, protocol, 0, models, bytes, blocks, fitted, 0);
    }

    public Statistics(int pipeline,
                      String protocol,
                      double meanBufferSize,
                      long models,
                      long bytes,
                      long blocks,
                      long fitted,
                      double score) {
        this.pipeline = pipeline;
        this.protocol = protocol;
        this.meanBufferSize = meanBufferSize;
        this.models = models;
        this.bytes = bytes;
        this.blocks = blocks;
        this.fitted = fitted;
        this.score = score;
    }

    public int getPipeline() {
        return pipeline;
    }

    public void setPipeline(int pipeline) {
        this.pipeline = pipeline;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public double getMeanBufferSize() {
        return meanBufferSize;
    }

    public void setMeanBufferSize(double meanBufferSize) {
        this.meanBufferSize = meanBufferSize;
    }

    public long getModels() {
        return models;
    }

    public void setModels(long models) {
        this.models = models;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public long getBlocks() {
        return blocks;
    }

    public void setBlocks(long blocks) {
        this.blocks = blocks;
    }

    public long getFitted() {
        return fitted;
    }

    public void setFitted(long fitted) {
        this.fitted = fitted;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
    public void updateBytes(long update){
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
    public void updateStats(Statistics stats) {
        assert(pipeline == stats.getPipeline() && protocol.equals(stats.getProtocol()));
        updateMeanBufferSize(stats.getMeanBufferSize());
        updateModels(stats.getModels());
        updateBytes(stats.getBytes());
        updateBlocks(stats.getBlocks());
        updateFitted(stats.getFitted());
        updateScore(stats.getScore());
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
        return 52 + ((protocol != null) ? 8 * protocol.length() : 0);
    }

}
