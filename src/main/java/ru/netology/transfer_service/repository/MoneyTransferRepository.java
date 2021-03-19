package ru.netology.transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transfer_service.TransferServiceApplication;
import ru.netology.transfer_service.exception.ErrorInputData;
import ru.netology.transfer_service.exception.ErrorTransfer;
import ru.netology.transfer_service.model.*;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MoneyTransferRepository {

    public final static Map<String, Card> cardsRepository = new ConcurrentHashMap<>();
    final private Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
    final private AtomicInteger idNumber = new AtomicInteger(0);


    public String transfer(TransferData transferData) {

        String operationId = null;

        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {

            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {

                Card currentCard = cardRepoEntry.getValue();

                String cardToNumber = transferData.getCardToNumber();

                if (!(currentCard.getCardFromNumber().equals(cardToNumber)) || (currentCard.getCardFromCVV().equals(transferData.getCardFromCVV())) || (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {

                    BigDecimal currentCardValue = currentCard.getAmountCard().getValue().setScale(2, RoundingMode.CEILING);

                    BigDecimal transferValue = BigDecimal.valueOf(transferData.getAmount().getValue()).setScale(2, RoundingMode.CEILING);

                    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.CEILING);

                    BigDecimal newValueCardFrom = (currentCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01)))).setScale(2, RoundingMode.CEILING);

                    if (newValueCardFrom.compareTo(BigDecimal.valueOf(0.01).setScale(2, RoundingMode.CEILING)) > 0) {

                        operationId = "Operation_" + idNumber.getAndIncrement();

                        DataOperation dataNewOperation = new DataOperation(currentCard, cardToNumber, transferValue, newValueCardFrom, fee);

                        operationsRepository.put(operationId, dataNewOperation);

                    }
                } else {
                    throw new ErrorInputData("Ошибка ввода данных карты");
                }
            }
        }

        return operationId;
    }


    public String confirmOperation(String transferServiceOperationId) {

        String operationId = null;

        for (Map.Entry<String, DataOperation> dataOperationEntry : operationsRepository.entrySet()) {

            if (transferServiceOperationId.equals(dataOperationEntry.getKey())) {

                operationId = dataOperationEntry.getKey();

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

            } else {
                throw new ErrorTransfer("Перевод не состоялся");
            }
        }

        return operationId;
    }
}
