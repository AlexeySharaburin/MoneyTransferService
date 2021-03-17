package ru.netology.transfer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class TransferServiceApplication {

    public static final String nameLog = "file.log";
    public static final String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());

    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class, args);
        System.out.println("\nWelcome to MoneyTransferService!");
        createFiles();

    }

    public static void createFiles() {

        String msgLog = "Файл file.log успешно создан";
//        String nameLog = "file.log";
//        String time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());


        File logFile = new File(nameLog);

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