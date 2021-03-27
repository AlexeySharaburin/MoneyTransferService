package ru.netology.transfer_service.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "OperationIdResponse{" +
                "operationId='" + operationId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationIdResponse that = (OperationIdResponse) o;
        return operationId.equals(that.operationId);
    }

}
