package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoneyTransferService {

    private final MoneyTransferLogService moneyTransferLogService;

    public MoneyTransferService(MoneyTransferLogService moneyTransferLogService) {
        this.moneyTransferLogService = moneyTransferLogService;
    }

    public final static Map<String, String> verificationRepository = new ConcurrentHashMap<>();


    public String transfer(TransferData transferData) {
        String operationId;
        String code = generateCode();
        String logError = "Ошибка транзакции";
        operationId = moneyTransferLogService.transfer(transferData);
        if (operationId != null) {
            verificationRepository.put(operationId, code);
            sendCodeToPhone(code);
        } else {
            System.out.println(logError);
            throw new ErrorInputData(logError);
        }
        return operationId;
    }


    public String confirmOperation(Verification verification) {
        String operationId;
        String logCode = "Неверный код подтверждения";
        String logId = "Операция отклонена! Ошибочный ID операции";
        if (verificationRepository.containsKey(verification.getOperationId())) {
            String code = verificationRepository.get(verification.getOperationId());
            if (code != null && isCodeCorrect(code)) {
                operationId = moneyTransferLogService.confirmOperation(verification.getOperationId());
            } else {
                System.out.println(logCode);
                throw new ErrorConfirmation(logCode);
            }
        } else {
            System.out.println(logId);
            throw new ErrorConfirmation(logId);
        }
        return operationId;
    }

    public static String generateCode() {
        Random random = new Random();
        int codeInt = random.nextInt(8999) + 1000;
        return String.valueOf(codeInt);
    }

    public void sendCodeToPhone(String code) {
        System.out.println("Клиенту на телефон отправлен код подтвержения операции: " + code);
    }

    // Эмуляция верификации:
    // если случайный код
    // меньше или равен 5000,
    // мы считаем, что клиент
    // ввёл неверный пин-код.
    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 5000);
    }
}























//    public String transferMoneyServiceLog(TransferData transferData) {
//        String operationId;
//        String logData = "Ошибка ввода данных карты";
//        DataOperation dataNewOperation = moneyTransferRepository.transfer(transferData);
//        if (dataNewOperation != null) {
//            operationId = "Bn@Operation#000" + idNumber.getAndIncrement();
//            operationsRepository.put(operationId, dataNewOperation);
//        } else {
//            System.out.println(logData);
//            throw new ErrorInputData(logData);
//        }
//
//        return operationId;
//    }
//
//
//    public String confirmMoneyServiceLog(String operationId) {
//
//        if (operationId != null) {
//
//            for (Map.Entry<String, DataOperation> dataOperationEntry : operationsRepository.entrySet()) {
//
//                if (operationId.equals(dataOperationEntry.getKey())) {
//
//                    Card currentCard = dataOperationEntry.getValue().getCard();
//
//                    String cardToNumber = dataOperationEntry.getValue().getCardToNumber();
//
//                    BigDecimal transferValue = dataOperationEntry.getValue().getTransferValue();
//
//                    BigDecimal newValueCardFrom = dataOperationEntry.getValue().getValue();
//
//                    BigDecimal fee = dataOperationEntry.getValue().getFee();
//
//                    currentCard.setAmountCard(new AmountCard(newValueCardFrom, currentCard.getAmountCard().getCurrency()));
//
//                    if (moneyTransferRepository.confirmOperation(currentCard, operationId)) {
//
//                        String operationLog = "Время операции: "
//                                + TransferServiceApplication.time
//                                + ",\n Id операции: "
//                                + operationId
//                                + ",\n карта списания: "
//                                + currentCard.getCardFromNumber()
//                                + ",\n карта зачисления: "
//                                + cardToNumber
//                                + ",\n сумма перевода: "
//                                + transferValue
//                                + ",\n валюта перевода: "
//                                + currentCard.getAmountCard().getCurrency()
//                                + ",\n комиссия в валюте перевода: "
//                                + fee
//                                + ",\n остаток на карте списания, руб.: "
//                                + newValueCardFrom;
//
//
//                        System.out.println(operationLog);
//
//                        try (FileWriter writerLogs = new FileWriter(TransferServiceApplication.nameLog, true)) {
//                            writerLogs.write(operationLog + "\n");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//        }
//        return operationId;
//    }


//    public boolean validateCardDate(String cardValdTill) {
//        Date cardDate = null;
//        SimpleDateFormat format = new SimpleDateFormat();
//        format.applyPattern("MM/yy");
//        try {
//            cardDate = format.parse(cardValdTill);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Date todaysDate = new Date();
//        if (cardDate != null) {
//            long diffDate = cardDate.getTime() - todaysDate.getTime();
//            int month = Integer.parseInt(cardValdTill.substring(0, 2));
//
//            return ((diffDate >= 0) && (month > 0) && (month < 13));
//        }
//        return false;
//    }

//    public String confirmOperation(Verification verification) {
//        String operationId;
//        if (verificationRepository.containsKey(verification.getOperationId())) {
//            String code = verificationRepository.get(verification.getOperationId());
//            if (code != null && isCodeCorrect(code)) {                                  // Эмуляция верификации:
//                operationId = moneyTransferRepository.confirmOperation(verification);   // если случайный код
//            } else {                                                                    // меньше или равен 5000,
//                System.out.println("Неверный код подтверждения");                       // мы считаем, что клиент
//                throw new ErrorConfirmation("Неверный код подтверждения");              // ввёл неверный пин-код.
//            }
//        } else {
//            System.out.println("Операция отклонена!");
//            throw new ErrorConfirmation("Ошибочный ID операции");
//        }
//        return operationId;
//    }

//    public String transfer(TransferData transferData) {
//
//        String operationId;
//        String code = generateCode();
//        String cardValidTill = transferData.getCardFromValidTill();
//        if (validateCardDate(cardValidTill)) {
//            operationId = moneyTransferRepository.transfer(transferData);
//            verificationRepository.put(operationId, code);
//            sendCodeToPhone(code);
//        } else {
//            throw new ErrorInputData("Срок действия вашей карты истёк");
//        }
//        return operationId;
//    }