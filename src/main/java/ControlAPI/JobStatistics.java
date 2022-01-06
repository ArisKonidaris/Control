package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JobStatistics {

    String jobName;
    int parallelism;
    long performance;
    Statistics[] statistics;

    public JobStatistics() {
        this("", 0, -1, null);
    }

    public JobStatistics(String jobName, int parallelism, long performance, Statistics[] statistics) {
        this.jobName = jobName;
        this.parallelism = parallelism;
        this.performance = performance;
        this.statistics = statistics;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public long getPerformance() {
        return performance;
    }

    public void setPerformance(long performance) {
        this.performance = performance;
    }

    public Statistics[] getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics[] statistics) {
        this.statistics = statistics;
    }

    @Override
    @JsonIgnore
    public String toString() {
        String result = "";
        for (Statistics stats : statistics)
            result += jobName + "," +
                    parallelism + "," +
                    performance + "," +
                    stats.getPipeline() + "," +
                    stats.getProtocol() + "," +
                    String.format("%.3f", stats.getMeanBufferSize()) + "," +
                    stats.getModels() + "," +
                    stats.getBytes() + "," +
                    stats.getBlocks() + "," +
                    stats.getFitted() + "," +
                    String.format("%.4f", stats.getScore()) + "," +
                    stats.getLearningCurve().toString() + "," +
                    stats.getLCX().toString() + "\n";
        return result;
    }

}
