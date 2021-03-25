package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;
import ru.netology.transfer_service.service.MoneyTransferLogConsole;
import ru.netology.transfer_service.service.MoneyTransferLogFile;
import ru.netology.transfer_service.service.MoneyTransferService;

import java.math.BigDecimal;
import java.math.RoundingMode;

class MoneyTransferServiceTest {

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

        DataOperation expectedDataOperation = new DataOperation(testCard,"222222222222", transferValue,  newValueCardFrom, fee);

        String operationsLogs = "String";

        Mockito.when(moneyTransferRepositoryMock.transfer(testTransferData))
                .thenReturn(expectedDataOperation);
        Mockito.when(moneyTransferLogConsoleMock.transferLog(operationsLogs))
                .thenReturn(true);
        Mockito.when(moneyTransferLogFileMock.transferLog(operationsLogs))
                .thenReturn(true);

        MoneyTransferService moneyTransferService = new MoneyTransferService(moneyTransferLogFileMock,moneyTransferRepositoryMock, moneyTransferLogConsoleMock);

        String resultOperationId = moneyTransferService.transfer(testTransferData);

        String expectedOperationId = "Bn@Operation#0001";

        Assertions.assertEquals(expectedOperationId, resultOperationId);

    }

//    @Test
//    void testConfirmOperation() {
//    }
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