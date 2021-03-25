//package ru.netology;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TransferServiceApplicationTest {
//
//
//    private final String HOST = "http://localhost:";
//    @Autowired
//    TestRestTemplate restTemplate;
//    @Container
//    public static GenericContainer<?> devApp = new GenericContainer<>("front_transfer:3.0")
//            .withExposedPorts(8080);
//    @Container
//    public static GenericContainer<?> prodApp = new GenericContainer<>("back_transfer:3.0")
//            .withExposedPorts(8081);
//
//    @BeforeAll
//    public static void setUp() {
//        devApp.start();
//        prodApp.start();
//    }
//
//
//    @Test
//    void context1() {
//
//        var port1 = devApp.getMappedPort(5500;
//        ResponseEntity<String> forEntity1 = restTemplate.getForEntity(HOST + port1 + "/profile", String.class);
//        System.out.println("\nPort1: " + port1);
//        System.out.println(forEntity1.getBody());
//        String msgExpected1 = "";
//        String msg1 = forEntity1.getBody();
//        Assertions.assertEquals(msg1, msgExpected1);
//    }
//
//}
