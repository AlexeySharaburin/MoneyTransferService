package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.TransferServiceApplication;
import ru.netology.transfer_service.exception.ErrorConfirmation;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MoneyTransferService {

    private final MoneyTransferLogFile moneyTransferLogFile;
    private final MoneyTransferRepository moneyTransferRepository;
    private final MoneyTransferLogConsole moneyTransferLogConsole;

    public MoneyTransferService(MoneyTransferLogFile moneyTransferLogFile, MoneyTransferRepository moneyTransferRepository, MoneyTransferLogConsole moneyTransferLogConsole) {
        this.moneyTransferLogFile = moneyTransferLogFile;
        this.moneyTransferRepository = moneyTransferRepository;
        this.moneyTransferLogConsole = moneyTransferLogConsole;
    }

    public final Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
    final private AtomicInteger idNumber = new AtomicInteger(1);
    public final Map<String, String> verificationRepository = new ConcurrentHashMap<>();


    public String transfer(TransferData transferData, Map<String, DataOperation> operationsRepository, Map<String, String> verificationRepository) {
        String operationId;
        String code = generateCode();
        String logData = "Ошибка ввода данных карты";
        String logTime = "Срок действия вашей карты истёк";

        String cardValidTill = transferData.getCardFromValidTill();

        if (validateCardDate(cardValidTill)) {
            DataOperation dataNewOperation = moneyTransferRepository.transfer(transferData, MoneyTransferRepository.cardsRepository);
            if (dataNewOperation != null) {
                operationId = "Bn@Operation#000" + idNumber.getAndIncrement();
                operationsRepository.put(operationId, dataNewOperation);
                verificationRepository.put(operationId, code);
                sendCodeToPhone(code);
            } else {
                throw new ErrorInputData(logData);
            }
        } else {
            System.out.println(logTime);
            throw new ErrorInputData(logTime);
        }

        return operationId;
    }

    public String confirmOperation(Verification verification, Map<String, DataOperation> operationsRepository, Map<String, String> verificationRepository) {
        String operationId = verification.getOperationId();
        String logCode = "Неверный код подтверждения";
        String logId = "Транзакция отклонена!";
        if (verificationRepository.containsKey(operationId) && operationId != null) {
            String code = verificationRepository.get(operationId);
            if (code != null && isCodeCorrect(code)) {
                DataOperation currentDataOperation = operationsRepository.get(operationId);
//                if (moneyTransferRepository.confirmOperation(currentDataOperation.getCard(), operationId)) {
                if (moneyTransferRepository.confirmOperation(currentDataOperation.getCard(), operationId, currentDataOperation.getCardToNumber())) {
                    System.out.println("Транзакция подтверждена!");
                    String operationLogs = writeStringLog(operationId, currentDataOperation);
                    synchronized (moneyTransferLogFile) {
                        if (moneyTransferLogFile.transferLog(operationLogs)
                                && moneyTransferLogConsole.transferLog(operationLogs)) {
                            System.out.println("Вся информация о транзакции передана клиенту");
                        }
                    }
                } else {
                    System.out.println(logId);
                    throw new ErrorConfirmation(logId);
                }
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

    public static String sendCodeToPhone(String code) {
        String msg = "Клиенту на телефон отправлен код подтвержения транзакции: " + code;
        System.out.println(msg);
        return msg;
    }

    // Эмуляция верификации:
    // если случайный код
    // меньше или равен 5000,
    // мы считаем, что клиент
    // ввёл неверный пин-код.
    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 5000);
    }

    public static boolean validateCardDate(String cardValdTill) {
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

            return ((diffDate >= 0) && (month > 0) && (month < 13));
        }
        return false;
    }

    public static String writeStringLog(String operationId, DataOperation dataOperation) {

        Card currentCard = dataOperation.getCard();

        String cardToNumber = dataOperation.getCardToNumber();

        BigDecimal transferValue = dataOperation.getTransferValue();

        BigDecimal newValueCardFrom = dataOperation.getValue();

        BigDecimal fee = dataOperation.getFee();

        currentCard.setAmountCard(new AmountCard(newValueCardFrom, currentCard.getAmountCard().getCurrency()));

        String operationLog = "Время транзакции: "
                + TransferServiceApplication.time
                + ",\n Id транзакции: "
                + operationId
                + ",\n карта списания: "
                + currentCard.getCardFromNumber()
                + ",\n карта зачисления: "
                + cardToNumber
                + ",\n сумма перевода: "
                + transferValue
                + ",\n валюта перевода: "
                + currentCard.getAmountCard().getCurrency()
                + ",\n комиссия в валюте перевода: "
                + fee
                + ",\n остаток на карте списания, руб.: "
                + newValueCardFrom;

        return operationLog;
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