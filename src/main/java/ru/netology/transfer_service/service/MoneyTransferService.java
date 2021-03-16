package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

@Service
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public boolean transfer(TransferData transferData) {

        return moneyTransferRepository.transfer(transferData);
    }

    public boolean confirmOperation(Verification verification) {
        return moneyTransferRepository.confirmOperation(verification);
    }


}
