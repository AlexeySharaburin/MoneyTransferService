package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MoneyTransferService {


    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferData transferData) {
        String serviceTransfer = null;
        String cardValidTill = transferData.getCardFromValidTill();
        if (validateCardDate(cardValidTill)) {
            serviceTransfer = moneyTransferRepository.transfer(transferData);
        }
        return serviceTransfer;
    }

    public boolean confirmOperation(Verification verification) {
        return moneyTransferRepository.confirmOperation(verification);
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
