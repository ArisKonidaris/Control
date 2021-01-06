package ControlAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KafkaMetadata implements CountableSerial {

    String topic;
    Integer partition;
    Long key;
    Long offset;
    Long timestamp;

    public KafkaMetadata() {
    }

    public KafkaMetadata(String topic, Integer partition, Long key, Long offset, Long timestamp) {
        this.topic = topic;
        this.partition = partition;
        this.key = key;
        this.offset = offset;
        this.timestamp = timestamp;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return  ((topic != null) ? 8 * topic.length() : 0) +
                ((partition != null) ? 4 : 0) +
                ((key != null) ? 8 : 0) +
                ((offset != null) ? 8 : 0) +
                ((timestamp != null) ? 8 : 0);
    }

}
