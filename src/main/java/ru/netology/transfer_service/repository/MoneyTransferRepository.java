package ru.netology.transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transfer_service.TransferServiceApplication;
import ru.netology.transfer_service.model.*;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MoneyTransferRepository {

    final private Map<String, Card> cardsRepository = new ConcurrentHashMap<>();
    final private Map<String, String> operationsRepository = new ConcurrentHashMap<>();
    final private AtomicInteger id = new AtomicInteger(0);

    Card currentCard;
    double transferValue;
    double currentCardValue;
    String operationId = null;
    String cardToNumber;
    String currency;

//    String nameLog = "file.log";
//    String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());


    public String transfer(TransferData transferData) {

        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {

            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {

                currentCard = cardRepoEntry.getValue();

                currency = transferData.getAmount().getCurrency();
                cardToNumber = transferData.getCardToNumber();

                if ((currentCard.getCardFromCVV().equals(transferData.getCardFromCVV())) || (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {

                    currentCardValue = currentCard.getAmountCard().getValue();

                    transferValue = transferData.getAmount().getValue();

                    if ((currentCardValue - transferValue) > 0.01) {

                        operationId = "Operation_" + id.getAndIncrement();

                    }
                }
            }
        }
        return operationId;
    }


    public boolean confirmOperation(Verification verification) {

        if (verification.getOperationID().equals(operationId)) {

            currentCardValue -= transferValue * 1.01;

            String fee = String.format("%.2f", transferValue * 0.01);

            currentCard.setAmountCard(new AmountCard(currentCardValue, currentCard.getAmountCard().getCurrency()));

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
                    + currency + " "
                    + ", комиссия в валюте перевода: "
                    + fee + " "
                    + ", остаток на карте списания, руб.: "
                    + currentCardValue;


            operationsRepository.put(operationId, operationLog);

            System.out.println(operationLog);

            try (FileWriter writerLogs = new FileWriter(TransferServiceApplication.nameLog, true)) {
                writerLogs.write(operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
