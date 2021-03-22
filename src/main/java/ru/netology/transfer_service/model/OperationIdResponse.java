package ru.netology.transfer_service.model;

public class OperationIdResponse {
    private String operationId;

    public OperationIdResponse(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
