package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MoneyTransferService {


    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferData transferData, long id) {
        String serviceTransfer;
        String cardValidTill = transferData.getCardFromValidTill();
        if (validateCardDate(cardValidTill)) {
            serviceTransfer = moneyTransferRepository.transfer(transferData, id);
        } else {
            throw new ErrorInputData("User %d: Срок действия вашей карты истёк" + id);
        }
        return serviceTransfer;
    }

    public boolean confirmOperation(Verification verification, long id) {
        if (verification.getCode().equals(verification.getCode())) {
            return moneyTransferRepository.confirmOperation(verification, id);
        } else {
            throw new ErrorConfirmation("Клиент %d: Ошибка подтверждения" + id);
        }
    }


    public boolean validateCardDate(String cardValdTill) {
        Date cardDate = null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MMyy");
        try {
            cardDate = format.parse(cardValdTill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        long diffDate = cardDate.getTime() - todaysDate.getTime();
        int month = Integer.parseInt(cardValdTill.substring(0, 2));

        if ((diffDate >= 0) && (month > 0) && (month < 13)) {
            return true;
        }
        return false;
    }


}
