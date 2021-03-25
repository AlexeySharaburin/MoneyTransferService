package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.TransferServiceApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class MoneyTransferLogFile implements TransferLogs {

    public static final String nameLog = "fileOperatiosLogs.log";

    @Override
    public boolean transferLog(String operationsLogs) {

        String msgLog = "Файл fileOperatiosLogs.log успешно создан";
        File logFile = new File(nameLog);

        if (operationsLogs != null) {

            if (!logFile.exists()) {
                try {
                    if (logFile.createNewFile())
                        System.out.println(msgLog);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                    writerLogs.write("Время транзакции:" + TransferServiceApplication.time + ": " + msgLog + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                writerLogs.write("\n" + operationsLogs + "\n");
                System.out.println("Транзакция осуществлена, данные о транзакции занесены в файл");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}















//
//        if (operationId != null) {
//            Card currentCard = dataOperation.getCard();
//
//            String cardToNumber = dataOperation.getCardToNumber();
//
//            BigDecimal transferValue = dataOperation.getTransferValue();
//
//            BigDecimal newValueCardFrom = dataOperation.getValue();
//
//            BigDecimal fee = dataOperation.getFee();
//
//            currentCard.setAmountCard(new AmountCard(newValueCardFrom, currentCard.getAmountCard().getCurrency()));
//
//
//
//                String operationLog = "Время операции: "
//                        + TransferServiceApplication.time
//                        + ",\n Id операции: "
//                        + operationId
//                        + ",\n карта списания: "
//                        + currentCard.getCardFromNumber()
//                        + ",\n карта зачисления: "
//                        + cardToNumber
//                        + ",\n сумма перевода: "
//                        + transferValue
//                        + ",\n валюта перевода: "
//                        + currentCard.getAmountCard().getCurrency()
//                        + ",\n комиссия в валюте перевода: "
//                        + fee
//                        + ",\n остаток на карте списания, руб.: "
//                        + newValueCardFrom;


//    public final static Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
//    final private AtomicInteger idNumber = new AtomicInteger(1);
//
//    public String transfer(TransferData transferData) {
//        String operationId;
//        String logData = "Ошибка ввода данных карты";
//        String logTime = "Срок действия вашей карты истёк";
//        String cardValidTill = transferData.getCardFromValidTill();
//        if (validateCardDate(cardValidTill)) {
//            DataOperation dataNewOperation = moneyTransferRepository.transfer(transferData);
//            if (dataNewOperation != null) {
//                operationId = "Bn@Operation#000" + idNumber.getAndIncrement();
//                operationsRepository.put(operationId, dataNewOperation);
//            } else {
//                throw new ErrorInputData(logData);
//            }
//        } else {
//            System.out.println(logTime);
//            throw new ErrorInputData(logTime);
//        }
//
//        return operationId;
//    }
//
//
//    public String confirmOperation(String operationId) {
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
//                        System.out.println(operationLog);
//
//                        try (FileWriter writerLogs = new FileWriter(TransferServiceApplication.nameLog, true)) {
//                            writerLogs.write("\n" + operationLog + "\n");
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
//
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

