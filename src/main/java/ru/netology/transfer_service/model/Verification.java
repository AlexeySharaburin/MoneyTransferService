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

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Verification{" +
                "operationID='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
