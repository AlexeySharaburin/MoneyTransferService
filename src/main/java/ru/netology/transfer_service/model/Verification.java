package ru.netology.transfer_service.model;

public class Verification {
    private String operationId;
    private String code;

    public Verification(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    @Override
    public String toString() {
        return "Verification{" +
                "operationID='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
