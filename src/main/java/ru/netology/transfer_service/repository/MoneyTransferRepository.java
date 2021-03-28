package ru.netology.transfer_service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MoneyTransferRepository {

    public static Map<String, Card> cardsRepository = new ConcurrentHashMap<>();

    @Autowired
    private MoneyTransferRepository() {
    }

    public MoneyTransferRepository(Map<String, Card> cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public DataOperation transfer(TransferData transferData) {
        Card currentCard;
        DataOperation dataNewOperation = null;

        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {

            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {

                currentCard = cardRepoEntry.getValue();
                dataNewOperation = acceptData(currentCard, transferData);
            }
        }
        return dataNewOperation;
    }


    public boolean confirmOperation(String operationId, DataOperation dataOperation) {
        if (operationId != null) {
            String cardFromNumber = dataOperation.getCard().getCardFromNumber();
            String cardToNumber = dataOperation.getCardToNumber();
            synchronized (cardFromNumber) {
                synchronized (cardToNumber) {
                    Card currentCard = dataOperation.getCard();
                    BigDecimal newValueCardFrom = dataOperation.getValue();
                    currentCard.setAmountCard(new AmountCard(newValueCardFrom, "RUR"));
                    cardsRepository.put(cardFromNumber, currentCard);
                    if (cardsRepository.containsKey(cardToNumber)) {
                        Card cardTo = cardsRepository.get(cardToNumber);
                        BigDecimal valueCardTo = cardTo.getAmountCard().getValue();
                        BigDecimal transferValue = dataOperation.getTransferValue();
                        BigDecimal newValueCardTo = valueCardTo.add(transferValue)
                                .setScale(2, RoundingMode.CEILING);
                        cardTo.setAmountCard(new AmountCard(newValueCardTo, "RUR"));
                        cardsRepository.put(cardToNumber, cardTo);
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public static DataOperation acceptData(Card currentCard, TransferData transferData) {

        DataOperation dataNewOperation;
        String cardToNumber = transferData.getCardToNumber();
        String logAmount = "На карте списания недостаточно средств";

        if (!(currentCard.getCardFromNumber().equals(cardToNumber))
                && (currentCard.getCardFromCVV().equals(transferData.getCardFromCVV()))
                && (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {

            BigDecimal currentCardValue = currentCard.getAmountCard().getValue()
                    .setScale(2, RoundingMode.CEILING);

            BigDecimal transferValue = BigDecimal.valueOf(transferData.getAmount().getValue() / 100)
                    .setScale(2, RoundingMode.CEILING);

            BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
                    .setScale(2, RoundingMode.CEILING);

            BigDecimal newValueCardFrom = (currentCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
                    .setScale(2, RoundingMode.CEILING);

            if (newValueCardFrom.compareTo(BigDecimal.valueOf(0.01)
                    .setScale(2, RoundingMode.CEILING)) > 0) {

                dataNewOperation = new DataOperation(currentCard, cardToNumber, transferValue, newValueCardFrom, fee);

            } else {
                System.out.println(logAmount);
                throw new ErrorInputData(logAmount);
            }

        } else {
            throw new ErrorInputData("Ошибка ввода данных карты");
        }

        return dataNewOperation;
    }
}


























//@Repository
//public class MoneyTransferRepository {
//
//    public final static Map<String, Card> cardsRepository = new ConcurrentHashMap<>();
//
//    public DataOperation transfer(TransferData transferData, Map<String, Card> cardsRepository) {
//        Card currentCard;
//        DataOperation dataNewOperation = null;
//
//        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {
//
//            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {
//
//                currentCard = cardRepoEntry.getValue();
//                dataNewOperation = acceptData(currentCard, transferData);
//            }
//        }
//        return dataNewOperation;
//    }


//
//    public String transfer(TransferData transferData) {
//
//        String operationId = null;
//        String cardToNumber = transferData.getCardToNumber();
//
//
//        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {
//
//            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {
//
//                Card currentCard = cardRepoEntry.getValue();
//
//                if (!(currentCard.getCardFromNumber().equals(cardToNumber))
//                        && (currentCard.getCardFromCVV().equals(transferData.getCardFromCVV()))
//                        && (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {
//
//
//                    BigDecimal currentCardValue = currentCard.getAmountCard().getValue().setScale(2, RoundingMode.CEILING);
//
//                    BigDecimal transferValue = BigDecimal.valueOf(transferData.getAmount().getValue() / 100).setScale(2, RoundingMode.CEILING);
//
//                    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.CEILING);
//
//                    BigDecimal newValueCardFrom = (currentCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01)))).setScale(2, RoundingMode.CEILING);
//
//                    if (newValueCardFrom.compareTo(BigDecimal.valueOf(0.01).setScale(2, RoundingMode.CEILING)) > 0) {
//
//                        operationId = "Bn@Operation#000" + idNumber.getAndIncrement();
//
//                        DataOperation dataNewOperation = new DataOperation(currentCard, cardToNumber, transferValue, newValueCardFrom, fee);
//
//                        operationsRepository.put(operationId, dataNewOperation);
//
//                    } else {
//                        throw new ErrorInputData("На карте списания недостаточно средств");
//                    }
//
//                } else {
//                    throw new ErrorInputData("Ошибка ввода данных карты");
//                }
//            }
//        }
//
//        return operationId;
//    }
//    public String confirmOperation(Verification verification) {
//
//        String operationId = null;
//
//        for (Map.Entry<String, DataOperation> dataOperationEntry : operationsRepository.entrySet()) {
//
//            if (verification.getOperationId().equals(dataOperationEntry.getKey())) {
//
//                operationId = dataOperationEntry.getKey();
//
//                Card currentCard = dataOperationEntry.getValue().getCard();
//
//                String cardToNumber = dataOperationEntry.getValue().getCardToNumber();
//
//                BigDecimal transferValue = dataOperationEntry.getValue().getTransferValue();
//
//                BigDecimal newValueCardFrom = dataOperationEntry.getValue().getValue();
//
//                BigDecimal fee = dataOperationEntry.getValue().getFee();
//
//
//                currentCard.setAmountCard(new AmountCard(newValueCardFrom, currentCard.getAmountCard().getCurrency()));
//
//                cardsRepository.put(currentCard.getCardFromNumber(), currentCard);
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
//
//
//                System.out.println(operationLog);
//
//                try (FileWriter writerLogs = new FileWriter(TransferServiceApplication.nameLog, true)) {
//                    writerLogs.write(operationLog+ "\n");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//        return operationId;
//    }