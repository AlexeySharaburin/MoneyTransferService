package ru.netology.transfer_service.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.transfer_service.TransferServiceApplication;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class MoneyTransferServiceTest {

    MoneyTransferRepository moneyTransferRepositoryMock = Mockito.mock(MoneyTransferRepository.class);
    MoneyTransferLogConsole moneyTransferLogConsoleMock = Mockito.mock(MoneyTransferLogConsole.class);
    MoneyTransferLogFile moneyTransferLogFileMock = Mockito.mock(MoneyTransferLogFile.class);

    Map<String, DataOperation> operationsRepositoryMock = Mockito.mock(Map.class);
    Map<String, String> verificationRepositoryMock = Mockito.mock(Map.class);

    MoneyTransferService moneyTransferService = new MoneyTransferService(moneyTransferLogFileMock,
            moneyTransferRepositoryMock,
            moneyTransferLogConsoleMock,
            operationsRepositoryMock,
            verificationRepositoryMock);


    BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);
    Card testCard = new Card("1111111111111111",
            "11/21",
            "111",
            new AmountCard(testCardValue, "RUR"));

    String testCardToNumber = "222222222222";

    TransferData testTransferData = new TransferData("1111111111111111", testCardToNumber, "11/21",
            "111",
            new Amount(100_000, "RUR"));

    BigDecimal transferValue = BigDecimal.valueOf(100_000)
            .setScale(2, RoundingMode.CEILING);
    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
            .setScale(2, RoundingMode.CEILING);
    BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
            .setScale(2, RoundingMode.CEILING);

    DataOperation testDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);

    String operationsLogs = "StringLog";
    String testOperationId = "Bn@Operation#0001";
    String testCode = "7777";

    Verification testVerification = new Verification(testOperationId, testCode);

    @BeforeEach
    public void mockBeforeEach() {
        Mockito.when(operationsRepositoryMock.get(testOperationId))
                .thenReturn(testDataOperation);
        Mockito.when(verificationRepositoryMock.containsKey(testOperationId))
                .thenReturn(true);
        Mockito.when(verificationRepositoryMock.get(testOperationId))
                .thenReturn(testCode);
        Mockito.when(moneyTransferLogConsoleMock.transferLog(operationsLogs))
                .thenReturn(true);
        Mockito.when(moneyTransferLogFileMock.transferLog(operationsLogs))
                .thenReturn(true);
    }

    @Before
    public void mockTransfer() {
        Mockito.when(moneyTransferRepositoryMock.transfer(testTransferData))
                .thenReturn(testDataOperation);
    }


    @Test
    void testTransferService() {
        mockTransfer();
        String resultOperationId = moneyTransferService.transfer(testTransferData);
        Assertions.assertEquals(testOperationId, resultOperationId);
    }

    @Before
    public void mockConfirm() {
        Mockito.when(moneyTransferRepositoryMock.confirmOperation(testOperationId, testDataOperation))
                .thenReturn(true);
    }

    @Test
    void testConfirmOperationService() {
        mockConfirm();
        String resultOperationId = moneyTransferService.confirmOperation(testVerification);
        String expectedOperationId = "Bn@Operation#0001";
        Assertions.assertEquals(expectedOperationId, resultOperationId);
    }

    @Test
    void testSendCodeToPhone() {
        String testCode = "7777";
        String expectedMessage = "Клиенту на телефон отправлен код подтвержения транзакции: " + testCode;
        String resultMessage = MoneyTransferService.sendCodeToPhone(testCode);
        Assertions.assertEquals(expectedMessage, resultMessage);
    }

    @Test
    void testIsCodeCorrect_correct() {
        String testCode = "7777";
        System.out.printf("Корректный код: %s!\n", testCode);
        Assertions.assertTrue(MoneyTransferService.isCodeCorrect(testCode));
    }

    @Test
    void testIsCodeCorrect_incorrect() {
        String testCode = "0000";
        System.out.printf("!!! Некорректный код: %s\n", testCode);
        Assertions.assertFalse(MoneyTransferService.isCodeCorrect(testCode));
    }

    @Test
    void testValidateCardDate_correct() {
        String testCardValdTill1 = "11/22";
        System.out.printf("Валидная дата: %s!\n", testCardValdTill1);
        Assertions.assertTrue(MoneyTransferService.validateCardDate(testCardValdTill1));
    }

    @Test
    void testValidateCardDate_incorrect() {
        String testCardValdTill1 = "11/20";
        System.out.printf("!!! Невалидная дата: %s\n", testCardValdTill1);
        Assertions.assertFalse(MoneyTransferService.validateCardDate(testCardValdTill1));
    }

    @Test
    void testWriteStringLog() {

        String testOperationId = "Bn@Operation#0001";

        BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);
        Card testCard = new Card("1111111111111111",
                "11/21",
                "111",
                new AmountCard(testCardValue, "RUR"));

        String testCardToNumber = "222222222222";

        BigDecimal transferValue = BigDecimal.valueOf(100_000)
                .setScale(2, RoundingMode.CEILING);

        BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
                .setScale(2, RoundingMode.CEILING);

        BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
                .setScale(2, RoundingMode.CEILING);

        DataOperation testDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);

        String expectedOperationLog = "Время транзакции: "
                + TransferServiceApplication.time
                + ",\n Id транзакции: "
                + testOperationId
                + ",\n карта списания: "
                + testCard.getCardFromNumber()
                + ",\n карта зачисления: "
                + testCardToNumber
                + ",\n сумма перевода: "
                + transferValue
                + ",\n валюта перевода: "
                + testCard.getAmountCard().getCurrency()
                + ",\n комиссия в валюте перевода: "
                + fee
                + ",\n остаток на карте списания, руб.: "
                + newValueCardFrom;


        String resultOperationLog = MoneyTransferService.writeStringLog(testOperationId, testDataOperation);

        Assertions.assertEquals(expectedOperationLog, resultOperationLog);
    }
}
