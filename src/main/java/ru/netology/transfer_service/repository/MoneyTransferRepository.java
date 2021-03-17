package ru.netology.transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transfer_service.TransferServiceApplication;
import ru.netology.transfer_service.model.*;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MoneyTransferRepository {

    final private Map<String, Card> cardsRepository = new ConcurrentHashMap<>();
    final private Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
    final private AtomicInteger id = new AtomicInteger(0);


    public String transfer(TransferData transferData) {

        String operationId = null;

        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {

            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {

                Card currentCard = cardRepoEntry.getValue();

                String cardToNumber = transferData.getCardToNumber();

                if ((currentCard.getCardFromCVV().equals(transferData.getCardFromCVV())) || (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {

                    BigDecimal currentCardValue = currentCard.getAmountCard().getValue().setScale(2, RoundingMode.CEILING);

                    BigDecimal transferValue = BigDecimal.valueOf(transferData.getAmount().getValue()).setScale(2, RoundingMode.CEILING);

                    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.CEILING);

                    BigDecimal newCardValue = (currentCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01)))).setScale(2, RoundingMode.CEILING);

                    if (newCardValue.compareTo(BigDecimal.valueOf(0.01).setScale(2, RoundingMode.CEILING)) > 0) {

                        operationId = "Operation_" + id.getAndIncrement();

                        DataOperation dataNewOperation = new DataOperation(currentCard, cardToNumber, transferValue, newCardValue, fee);

                        operationsRepository.put(operationId, dataNewOperation);

                    }
                }
            }
        }

        return operationId;
    }


    public boolean confirmOperation(Verification verification) {

        for (Map.Entry<String, DataOperation> dataOperationEntry : operationsRepository.entrySet()) {


            if (verification.getOperationID().equals(dataOperationEntry.getKey())) {

                String operationId = dataOperationEntry.getKey();

                Card currentCard = dataOperationEntry.getValue().getCard();

                String cardToNumber = dataOperationEntry.getValue().getCardToNumber();

                BigDecimal transferValue = dataOperationEntry.getValue().getTransferValue();

                BigDecimal newCardValue = dataOperationEntry.getValue().getValue();

                BigDecimal fee = dataOperationEntry.getValue().getFee();

                currentCard.setAmountCard(new AmountCard(newCardValue, currentCard.getAmountCard().getCurrency()));

                cardsRepository.put(currentCard.getCardFromNumber(), currentCard);

                String operationLog = "Время операции: "
                        + TransferServiceApplication.time + " "
                        + ", ID операции: "
                        + operationId + " "
                        + ", карта списания: "
                        + currentCard.getCardFromNumber() + " "
                        + ", карта зачисления: "
                        + cardToNumber + " "
                        + ", сумма перевода: "
                        + transferValue + " "
                        + ", валюта перевода: "
                        + currentCard.getAmountCard().getCurrency() + " "
                        + ", комиссия в валюте перевода: "
                        + fee + " "
                        + ", остаток на карте списания, руб.: "
                        + newCardValue;


                System.out.println(operationLog);

                try (FileWriter writerLogs = new FileWriter(TransferServiceApplication.nameLog, true)) {
                    writerLogs.write(operationLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
