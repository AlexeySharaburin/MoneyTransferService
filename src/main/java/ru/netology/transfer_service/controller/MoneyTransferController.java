package ru.netology.transfer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.exception.ErrorTransfer;
import ru.netology.transfer_service.model.ExceptionResponse;
import ru.netology.transfer_service.model.OperationIdResponse;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;
import ru.netology.transfer_service.service.MoneyTransferService;

@RestController
@RequestMapping("/")
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<OperationIdResponse> transfer(@RequestBody TransferData transferData) {
        String operationId = moneyTransferService.transfer(transferData);
        if (operationId != null) {
            System.out.println("Transfer is ready!");
            return new ResponseEntity<>(new OperationIdResponse(operationId), HttpStatus.OK);
        }
        System.out.println("Transfer is not ready!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationIdResponse> confirmOperation(@RequestBody Verification verification) {
        String operationId = moneyTransferService.confirmOperation(verification);
        if (operationId != null) {
            System.out.println("Operation is confirmed!");
            return new ResponseEntity<>(new OperationIdResponse(operationId), HttpStatus.OK);
        }
        System.out.println("Operation was cancelled!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ExceptionResponse> handleErrorInputData(ErrorInputData e) {
        String msgInput = "Error input data";
        System.out.println(msgInput);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), 400), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<ExceptionResponse> handleErrorTransfer(ErrorTransfer e) {
        String msgTransfer = "Error transfer";
        System.out.println(msgTransfer);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<ExceptionResponse> handleErrorConfirmation(ErrorConfirmation e) {
        String msgConfirmation = "Error confirmation";
        System.out.println(msgConfirmation);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), 500), HttpStatus.NOT_FOUND);
    }


}
