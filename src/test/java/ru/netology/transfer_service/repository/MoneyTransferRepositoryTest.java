package ru.netology.transfer_service.repository;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.transfer_service.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransferRepositoryTest {

    BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);
    Card testCard = new Card("1111111111111111",
            "11/21",
            "111",
            new AmountCard(testCardValue, "RUR"));

    String testCardToNumber = "222222222222";

    TransferData testTransferData = new TransferData("1111111111111111", testCardToNumber, "11/21",
            "111",
            new Amount(100_000, "RUR"));

    BigDecimal transferValue = BigDecimal.valueOf(100_000 / 100)
            .setScale(2, RoundingMode.CEILING);

    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
            .setScale(2, RoundingMode.CEILING);

    BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
            .setScale(2, RoundingMode.CEILING);

    String testOperationId = "Bn@Operation#0001";

    Map<String, Card> testCardsRepository = new HashMap<>();

    DataOperation testDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);

    @BeforeEach
    public void fillMap() {
        testCardsRepository.put("1111111111111111", testCard);
    }

    @Test
    void testAcceptData() {
        DataOperation expectedDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);
        DataOperation resultDataOperation = MoneyTransferRepository.acceptData(testCard, testTransferData);
        Assertions.assertEquals(expectedDataOperation, resultDataOperation);
    }

    @Test
    void testTransferRepository() {
        fillMap();
        DataOperation expectedDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);
        DataOperation resultDataOperation = new MoneyTransferRepository(testCardsRepository).transfer(testTransferData);
        Assertions.assertEquals(expectedDataOperation, resultDataOperation);
    }

    @Test
    void testConfirmOperationRepository() {
        Boolean expectedConfirm = true;
        Boolean resultConfirm = new MoneyTransferRepository(testCardsRepository).confirmOperation(testOperationId, testDataOperation);
        Assertions.assertEquals(expectedConfirm, resultConfirm);
    }
}
