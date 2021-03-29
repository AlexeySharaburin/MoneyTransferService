package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;
import ru.netology.transfer_service.TransferServiceApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class MoneyTransferLogFile implements TransferLogs {

    public static final String nameLog = "fileOperatiosLogs.log";

    @Override
    public boolean transferLog(String operationsLogs) {

        String msgLog = "Файл fileOperatiosLogs.log успешно создан";
        File logFile = new File(nameLog);

        if (operationsLogs != null) {

            if (!logFile.exists()) {
                try {
                    if (logFile.createNewFile())
                        System.out.println(msgLog);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                    writerLogs.write("Время транзакции:" + TransferServiceApplication.time + ": " + msgLog + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter writerLogs = new FileWriter(nameLog, true)) {
                writerLogs.write("\n" + operationsLogs + "\n");
                System.out.println("Транзакция осуществлена, данные о транзакции занесены в файл");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
