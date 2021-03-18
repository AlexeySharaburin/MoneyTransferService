package ru.netology.transfer_service.exception;

public class ErrorConfirmation extends RuntimeException {
    public ErrorConfirmation(String message) {
        super(message);
    }
}
