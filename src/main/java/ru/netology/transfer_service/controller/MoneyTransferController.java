package ru.netology.transfer_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if (moneyTransferService.transfer(transferData) != null) {
            return new ResponseEntity<>(moneyTransferService.transfer(transferData), HttpStatus.OK);
        }
        return new ResponseEntity<>("Перевод совершить не удалось. Попробуйте ещё раз.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirmOperation/{id}")
    public boolean confirmOperation(@RequestBody Verification verification, @PathVariable long id) {
        return moneyTransferService.confirmOperation(verification);
    }


}
