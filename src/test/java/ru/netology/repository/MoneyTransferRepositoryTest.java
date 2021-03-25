package ru.netology.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferRepositoryTest {

    public final static Map<String, Card> cardsTestRepository = new ConcurrentHashMap<>();


    @Test
    void testAcceptData() {
        BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);
        Card testCard = new Card("1111111111111111",
                "11/21",
                "111",
                new AmountCard(testCardValue, "RUR"));
        TransferData testTransferData = new TransferData("1111111111111111", "222222222222", "11/21",
                "111",
                new Amount(100_000, "RUR"));

        BigDecimal transferValue = BigDecimal.valueOf(100_000)
                .setScale(2, RoundingMode.CEILING);

        BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
                .setScale(2, RoundingMode.CEILING);

        BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
                .setScale(2, RoundingMode.CEILING);

        DataOperation expectedDataOperation = new DataOperation(testCard,"222222222222", transferValue,  newValueCardFrom, fee);

        DataOperation resultDataOperation = MoneyTransferRepository.acceptData(testCard,testTransferData);

        Assertions.assertEquals(expectedDataOperation,resultDataOperation);
    }

    @Test
    void testTransferRepository() {
        BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);
        Card testCard = new Card("1111111111111111",
                "11/21",
                "111",
                new AmountCard(testCardValue, "RUR"));
        TransferData testTransferData = new TransferData("1111111111111111", "222222222222", "11/21",
                "111",
                new Amount(100_000, "RUR"));

        BigDecimal transferValue = BigDecimal.valueOf(100_000)
                .setScale(2, RoundingMode.CEILING);

        BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
                .setScale(2, RoundingMode.CEILING);

        BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
                .setScale(2, RoundingMode.CEILING);

        DataOperation expectedDataOperation = new DataOperation(testCard,"222222222222", transferValue,  newValueCardFrom, fee);

        DataOperation resultDataOperation = new MoneyTransferRepository().transfer(testTransferData);

        Assertions.assertEquals(expectedDataOperation,resultDataOperation);
    }

    @Test
    void testConfirmOperationRepository() {
        Card testCard = new Card("1111111111111111",
                "11/21",
                "111",
                new AmountCard(BigDecimal.valueOf(203_345.15), "RUR"));
        String testOperationId = "success_1";
        Boolean result = true;
        Boolean expected = new MoneyTransferRepository().confirmOperation(testCard,testOperationId);
        Assertions.assertEquals(expected, result);
    }
}