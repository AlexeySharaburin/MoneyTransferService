package ru.netology.transfer_service.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;
import ru.netology.transfer_service.service.MoneyTransferLogConsole;
import ru.netology.transfer_service.service.MoneyTransferLogFile;
import ru.netology.transfer_service.service.MoneyTransferService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MoneyTransferServiceTest {

    @Test
    void testTransfer() {

        MoneyTransferRepository moneyTransferRepositoryMock = Mockito.mock(MoneyTransferRepository.class);
        MoneyTransferLogConsole moneyTransferLogConsoleMock = Mockito.mock(MoneyTransferLogConsole.class);
        MoneyTransferLogFile moneyTransferLogFileMock = Mockito.mock(MoneyTransferLogFile.class);

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

        DataOperation expectedDataOperation = new DataOperation(testCard, "222222222222", transferValue, newValueCardFrom, fee);

        String operationsLogs = "StringLog";

        Mockito.when(moneyTransferRepositoryMock.transfer(testTransferData))
                .thenReturn(expectedDataOperation);
        Mockito.when(moneyTransferLogConsoleMock.transferLog(operationsLogs))
                .thenReturn(true);
        Mockito.when(moneyTransferLogFileMock.transferLog(operationsLogs))
                .thenReturn(true);

        MoneyTransferService moneyTransferService = new MoneyTransferService(moneyTransferLogFileMock, moneyTransferRepositoryMock, moneyTransferLogConsoleMock);

        String resultOperationId = moneyTransferService.transfer(testTransferData);

        String expectedOperationId = "Bn@Operation#0001";

        Assertions.assertEquals(expectedOperationId, resultOperationId);

    }

    @Test
    void testConfirmOperation() {

        MoneyTransferRepository moneyTransferRepositoryMock = Mockito.mock(MoneyTransferRepository.class);
        MoneyTransferLogConsole moneyTransferLogConsoleMock = Mockito.mock(MoneyTransferLogConsole.class);
        MoneyTransferLogFile moneyTransferLogFileMock = Mockito.mock(MoneyTransferLogFile.class);

        Map<String, DataOperation> operationsRepositoryMock = Mockito.mock(Map.class);
        Map<String, String> verificationRepositoryMock = Mockito.mock(Map.class);


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

        DataOperation expectedDataOperation = new DataOperation(testCard, "222222222222", transferValue, newValueCardFrom, fee);

        String testOperationId = "Bn@Operation#0001";

        String testCode = "7777";

        Verification testVerification = new Verification(testOperationId, testCode);


        String operationsLogs = "StringLog";

        Mockito.when(moneyTransferRepositoryMock.confirmOperation(testCard, testOperationId))
                .thenReturn(true);
        Mockito.when(moneyTransferLogConsoleMock.transferLog(operationsLogs))
                .thenReturn(true);
        Mockito.when(moneyTransferLogFileMock.transferLog(operationsLogs))
                .thenReturn(true);

        Mockito.when(operationsRepositoryMock.containsKey(testOperationId))
                .thenReturn(true);
        Mockito.when(operationsRepositoryMock.get(testOperationId))
                .thenReturn(expectedDataOperation);

        Mockito.when(verificationRepositoryMock.containsKey(testOperationId))
                .thenReturn(true);
        Mockito.when(verificationRepositoryMock.get(testOperationId))
                .thenReturn(testCode);

        MoneyTransferService moneyTransferService = new MoneyTransferService(moneyTransferLogFileMock, moneyTransferRepositoryMock, moneyTransferLogConsoleMock);

        String resultOperationId = moneyTransferService.confirmOperation(testVerification);

        String expectedOperationId = "Bn@Operation#0001";

        Assertions.assertEquals(expectedOperationId, resultOperationId);
    }
//
//    @Test
//    void generateCode() {
//    }
//
//    @Test
//    void sendCodeToPhone() {
//    }
//
//    @Test
//    void isCodeCorrect() {
//    }
//
//    @Test
//    void validateCardDate() {
//    }
//
//    @Test
//    void writeStringLog() {
//    }
}