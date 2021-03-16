package ru.netology.transfer_service.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transfer_service.model.Amount;
import ru.netology.transfer_service.model.Card;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;

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

    String nameLog = "file.log";
    String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());


    public boolean transfer(TransferData transferData) {

        for (Map.Entry<String, Card> cardRepoEntry : cardsRepository.entrySet()) {

            if (transferData.getCardFromNumber().equals(cardRepoEntry.getKey())) {

                Card currentCard = cardRepoEntry.getValue();

                if ((currentCard.getCardFromCVV().equals(transferData.getCardFromCVV())) || (currentCard.getCardFromValidTill().equals(transferData.getCardFromValidTill()))) {

                    int currentCardValue = currentCard.getAmount().getValue();
                    int transferValue = transferData.getAmount().getValue();

                    if ((currentCardValue - transferValue) > 0) {

                        currentCardValue -= transferValue;

                        String fee = String.format("%.2f", transferValue * 0.01);

                        currentCard.setAmount(new Amount(currentCardValue, currentCard.getAmount().getCurrency()));

                        cardsRepository.put(currentCard.getCardFromNumber(), currentCard);

                        String operationId = "Operation_" + id.getAndIncrement();

                        String operationLog = "Время операции: "
                                + time + " "
                                + ", ID операции: "
                                + operationId + " "
                                + ", карта списания: "
                                + currentCard.getCardFromNumber() + " "
                                + ", карта зачисления: "
                                + transferData.getCardToNumber() + " "
                                + ", сумма перевода: "
                                + transferValue + " "
                                + ", валюта перевода: "
                                + transferData.getAmount().getCurrency() + " "
                                + ", комиссия в валюте перевода: "
                                + fee + " "
                                + ", результат: перевод прошёл удачно!\n";


                        operationsRepository.put(operationId, operationLog);

                        System.out.println(operationLog);

                        try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                            writerLogs.write(operationLog);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean confirmOperation(Verification verification) {
        if (verification.getCode().equals(verification.getOperationID())) {
            return true;
        }
        return false;
    }
}
