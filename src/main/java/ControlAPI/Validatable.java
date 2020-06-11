package ControlAPI;

public interface Validatable extends KafkaRecord {
    boolean isValid();
}
