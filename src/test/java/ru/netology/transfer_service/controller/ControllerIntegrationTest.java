//package ru.netology.transfer_service.controller;

//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import ru.netology.transfer_service.model.Amount;
//import ru.netology.transfer_service.model.DataOperation;
//import ru.netology.transfer_service.model.TransferData;
//import ru.netology.transfer_service.model.Verification;
//import ru.netology.transfer_service.repository.MoneyTransferRepository;
//import ru.netology.transfer_service.service.MoneyTransferService;
//
//import javax.servlet.ServletContext;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ControllerIntegrationTest {
//
//    private final String HOST = "http://localhost:5500";
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private MoneyTransferRepository moneyTransferRepository;
//
//    @Autowired
//    private MoneyTransferService moneyTransferService;
//
//
//    @Test
//    public void givenConfirm_thenMock_thenVerifyResponse() throws Exception {
//        Verification testVerification = getVerification();
//        this.mockMvc.perform(
//                post("/confirmOperation")
//                        .content(objectMapper.writeValueAsString(testVerification))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.operationId").value("Bn@Operation#0001")
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code").value("7777"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON)
//                );
//    }
//
//    public static Verification getVerification() {
//        String testOperationId = "Bn@Operation#0001";
//        String testCode = "7777";
//        return new Verification(testOperationId, testCode);
//    }
//


//    @Test
//    public void givenTransfer_thenMock_thenVerifyResponse() throws Exception {
//        TransferData testTransferData = getTransferData();
//        this.mockMvc.perform(
//                post("/transfer")
//                        .content(objectMapper.writeValueAsString(testTransferData))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.cardFromNumber").value("1111111111111111"))
//                .andExpect(jsonPath("$.cardToNumber").value("222222222222"))
//                .andExpect(jsonPath("$.cardFromValidTill").value("11/21"))
//                .andExpect(jsonPath("$.cardFromValidTill").value("11/21"))
//                .andExpect(jsonPath("$.cardFromValidTill").value("11/21"))
//
//                );
//    }

//    @Test
//    public void givenConfirm_thenMock_thenVerifyResponse() throws Exception {
//        Verification testVerification = getVerification();
//        this.mockMvc.perform(
//                post("/confirmOperation")
//                        .content(objectMapper.writeValueAsString(testVerification))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.operationId").value("Bn@Operation#0001")
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(jsonPath("$.code").value("7777"))
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON)
//                        );
//    }


