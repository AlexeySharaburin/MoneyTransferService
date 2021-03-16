package ru.netology.transfer_service.model;

public class Verification {
    private String operationID;
    private String code;

    public Verification(String operationID, String code) {
        this.operationID = operationID;
        this.code = code;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
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
                "operationID='" + operationID + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
