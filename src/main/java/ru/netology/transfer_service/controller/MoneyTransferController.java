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

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferData transferData) {
        if (moneyTransferService.transfer(transferData) != null) {
            return new ResponseEntity<>(moneyTransferService.transfer(transferData), HttpStatus.OK);
        }
        return new ResponseEntity<>("Перевод совершить не удалось. Попробуйте ещё раз.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody Verification verification) {
        if (moneyTransferService.confirmOperation(verification) != null) {
            return new ResponseEntity<>(moneyTransferService.confirmOperation(verification), HttpStatus.OK);
        }
        return new ResponseEntity<>("Перевод совершить не удалось. Попробуйте ещё раз.", HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> handleErrorInputData(ErrorInputData e) {
        System.out.println("Error input data");
        return new ResponseEntity<>("Error input data" + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<String> handleErrorConfirmation(ErrorConfirmation e) {
        System.out.println("Error confirmation");
        return new ResponseEntity<>("Error confirmation" + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<String> handleErrorTransfer(ErrorTransfer e) {
        System.out.println("Error transfer");
        return new ResponseEntity<>("Error transfer" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
