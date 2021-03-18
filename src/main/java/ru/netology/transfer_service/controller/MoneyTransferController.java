package ru.netology.transfer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.exception.ErrorTransfer;
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

    @PostMapping("/transfer/{id}")
    public ResponseEntity<String> transfer(@RequestBody TransferData transferData, @PathVariable long id) {
        if (moneyTransferService.transfer(transferData, id) != null) {
            return new ResponseEntity<>(moneyTransferService.transfer(transferData, id), HttpStatus.OK);
        }
        return new ResponseEntity<>("Перевод совершить не удалось. Попробуйте ещё раз.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirmOperation/{id}")
    public boolean confirmOperation(@RequestBody Verification verification, @PathVariable long id) {
        return moneyTransferService.confirmOperation(verification, id);
    }

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> handleErrorInputData(@PathVariable long id) {
        System.out.println("Error input data for user %d" + id);
        return new ResponseEntity<>("Error input data for user %d" + id, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<String> handleErrorConfirmation(@PathVariable long id) {
        System.out.println("Error confirmation for user %d" + id);
        return new ResponseEntity<>("Error confirmation for user %d" + id, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<String> handleErrorTransfer(@PathVariable long id) {
        System.out.println("Error transfer for user %d" + id);
        return new ResponseEntity<>("Error transfer for user %d" + id, HttpStatus.NOT_FOUND);
    }

}
