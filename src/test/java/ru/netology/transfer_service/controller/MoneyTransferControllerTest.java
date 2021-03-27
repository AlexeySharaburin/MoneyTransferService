package ru.netology.transfer_service.controller;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.netology.transfer_service.exception.ErrorTransfer;
import ru.netology.transfer_service.model.*;
import ru.netology.transfer_service.repository.MoneyTransferRepository;
import ru.netology.transfer_service.service.MoneyTransferService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferControllerTest {

    MoneyTransferService moneyTransferServiceMock = Mockito.mock(MoneyTransferService.class);

    Map<String, DataOperation> operationsRepositoryMock = Mockito.mock(Map.class);
    Map<String, String> verificationRepositoryMock = Mockito.mock(Map.class);

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

    String testOperationId = "Bn@Operation#0001";
    String testCode = "7777";
    Verification testVerification = new Verification(testOperationId, testCode);


    @Test
    void testOperationIdTransferController() {

        Mockito.when(moneyTransferServiceMock.transfer(testTransferData, operationsRepositoryMock, verificationRepositoryMock))
                .thenReturn(testOperationId);

        OperationIdResponse expectedResponse = new OperationIdResponse(testOperationId);
        OperationIdResponse resultResponse = new OperationIdResponse(moneyTransferServiceMock.transfer(testTransferData, operationsRepositoryMock, verificationRepositoryMock));

        Assertions.assertEquals(expectedResponse.toString(), resultResponse.toString());
    }


    @Test
    void testTransferController() {

        Mockito.when(moneyTransferServiceMock.transfer(testTransferData, operationsRepositoryMock, verificationRepositoryMock))
                .thenReturn(testOperationId);

        System.out.println(moneyTransferServiceMock.transfer(testTransferData, operationsRepositoryMock, verificationRepositoryMock));

        ResponseEntity<OperationIdResponse> expected = new ResponseEntity<>((new OperationIdResponse(testOperationId)), HttpStatus.OK);
        ResponseEntity<OperationIdResponse> result = new MoneyTransferController(moneyTransferServiceMock).transfer(testTransferData);

        Assertions.assertEquals(expected, result);

    }


    @Test
    void testOperationIdConfirmController() {

        Verification testVerification = new Verification(testOperationId, testCode);

        Mockito.when(moneyTransferServiceMock.confirmOperation(testVerification, operationsRepositoryMock, verificationRepositoryMock))
                .thenReturn(testOperationId);

        OperationIdResponse expectedResponse = new OperationIdResponse(testOperationId);
        OperationIdResponse resultResponse = new OperationIdResponse(moneyTransferServiceMock.confirmOperation(testVerification, operationsRepositoryMock, verificationRepositoryMock));

        Assertions.assertEquals(expectedResponse.toString(), resultResponse.toString());
    }

}


//    Map<String, DataOperation> operationsRepository = new HashMap<>();
//    Map<String, String> verificationRepository = new HashMap<>();

//        operationsRepository.put(testOperationId, testDataOperation);
//        verificationRepository.put(testOperationId, testCode);


//    @Before
//    public void fillMap() {
//        operationsRepository.put(testOperationId, testDataOperation);
//        verificationRepository.put(testOperationId, testCode);
//    }


//    @Test
//    void testErrorTransfer() {
//
//        ResponseEntity<ExceptionResponse> expectedResponse = new ResponseEntity<> (new ExceptionResponse("ErrorTransfer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
//        ResponseEntity<ExceptionResponse> resultResponse = new MoneyTransferController(moneyTransferServiceMock).handleErrorTransfer(new ErrorTransfer("EroorTransfer"));
//
//        Assertions.assertEquals(expectedResponse, resultResponse);
//    }


//    @Test
//    void confirmOperation() {
//
//        Verification testVerification = new Verification(testOperationId, testCode);
//
//        Mockito.when(moneyTransferServiceMock.confirmOperation(testVerification, operationsRepositoryMock, verificationRepositoryMock))
//                .thenReturn(testOperationId);
//
//        ResponseEntity<OperationIdResponse> expected = new ResponseEntity<>((new OperationIdResponse(testOperationId)), HttpStatus.OK);
//
//        ResponseEntity<OperationIdResponse> result = new MoneyTransferController(moneyTransferServiceMock).confirmOperation(testVerification);
//
//        Assertions.assertEquals(expected, result);
//
//    }
//
//
