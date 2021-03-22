package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;

    public final static Map<String, String> verificationRepository = new ConcurrentHashMap<>();

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    public String transfer(TransferData transferData) {
        String operationId;
        String code = generateCode();
        String cardValidTill = transferData.getCardFromValidTill();
        if (validateCardDate(cardValidTill)) {
            operationId = moneyTransferRepository.transfer(transferData);
            verificationRepository.put(operationId, code);
            sendCodeToPhone(code);
        } else {
            throw new ErrorInputData("Срок действия вашей карты истёк");
        }
        return operationId;
    }

    public String confirmOperation(Verification verification) {
        String operationId = null;
        for (Map.Entry<String, String> verificationEntry : verificationRepository.entrySet()) {
            if (verification.getOperationId().equals(verificationEntry.getKey())) {
                if (Integer.parseInt(verificationEntry.getValue()) > 5000) {                // Эмуляция верификации:
                    operationId = moneyTransferRepository.confirmOperation(verification);   // если случайный код
                } else {                                                                    // меньше или равен 5000,
                    throw new ErrorConfirmation("Неверный код подтверждения");              // мы считаем, что клиент
                }                                                                           // ввёл неверный пин-код.
            }
        }
        return operationId;
    }

    public boolean validateCardDate(String cardValdTill) {
        Date cardDate = null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM/yy");
        try {
            cardDate = format.parse(cardValdTill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date todaysDate = new Date();
        if (cardDate != null) {
            long diffDate = cardDate.getTime() - todaysDate.getTime();
            int month = Integer.parseInt(cardValdTill.substring(0, 2));

            if ((diffDate >= 0) && (month > 0) && (month < 13)) {
                return true;
            }
        }
        return false;
    }

    public static String generateCode() {
        Random random = new Random();
        int codeInt = random.nextInt(8999) + 1000;
        return String.valueOf(codeInt);
    }

    public void sendCodeToPhone(String code) {
        System.out.println("Клиенту на телефон отправлен код подтвержения операции: " + code);
    }
}
