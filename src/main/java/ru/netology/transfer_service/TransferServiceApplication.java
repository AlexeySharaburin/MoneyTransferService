package ru.netology.transfer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.netology.transfer_service.model.AmountCard;
import ru.netology.transfer_service.model.Card;
import ru.netology.transfer_service.repository.MoneyTransferRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class TransferServiceApplication {

    public static final String nameLog = "file.log";
    public static final String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());

    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class, args);
        System.out.println("\nWelcome to MoneyTransferService!");
        createFiles();
        addCards();

    }

    public static void createFiles() {

        String msgLog = "Файл file.log успешно создан";
//        String nameLog = "file.log";
//        String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());


        File logFile = new File(nameLog);

        if (!logFile.exists()) {

            try {
                if (logFile.createNewFile())
                    System.out.println(msgLog);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                writerLogs.write(time + ": " + msgLog + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCards() {
        MoneyTransferRepository.cardsRepository.put("1111111111111111",
                new Card("1111111111111111",
                        "1121",
                        "111",
                        new AmountCard(BigDecimal.valueOf(203_345.15), "RUR")));
        MoneyTransferRepository.cardsRepository.put("1111111111111112",
                new Card("1111111111111112",
                        "1122",
                        "112",
                        new AmountCard(BigDecimal.valueOf(345.15), "RUR")));
        MoneyTransferRepository.cardsRepository.put("1111111111111113",
                new Card("1111111111111113",
                        "1123",
                        "113",
                        new AmountCard(BigDecimal.valueOf(203_345_111.15), "RUR")));
    }

}