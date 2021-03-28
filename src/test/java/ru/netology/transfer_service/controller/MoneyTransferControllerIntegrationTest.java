package ru.netology.transfer_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.netology.transfer_service.model.Amount;
import ru.netology.transfer_service.model.TransferData;
import ru.netology.transfer_service.model.Verification;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MoneyTransferControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesMoneyTransferController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean(MoneyTransferController.class));
    }

}


















//    @Test
//    void whenTransferValidInputData_thenReturns200() throws Exception {
//        TransferData testTransferData = getTransferData();
//        mockMvc.perform(post("/transfer")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(testTransferData)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void whenConfirmValid_thenReturns200() throws Exception {
//        Verification testVerification = getVerification();
//        mockMvc.perform(post("/confirmOperation")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(testVerification)))
//                .andExpect(status().isOk());
//    }
//
//    public static Verification getVerification() {
//        String testOperationId = "Bn@Operation#0001";
//        String testCode = "7777";
//        return new Verification(testOperationId, testCode);
//    }
//
//    public static TransferData getTransferData() {
//        return new TransferData("1111111111111111", "222222222222", "11/21",
//                "111",
//                new Amount(100_000, "RUR"));
//    }

