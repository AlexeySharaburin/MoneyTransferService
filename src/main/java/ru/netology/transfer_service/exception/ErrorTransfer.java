package ru.netology.transfer_service.exception;

public class ErrorTransfer extends RuntimeException {
    public ErrorTransfer(String message) {
        super(message);
    }
}
