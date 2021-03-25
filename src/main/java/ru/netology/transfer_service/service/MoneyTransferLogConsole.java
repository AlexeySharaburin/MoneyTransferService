package ru.netology.transfer_service.service;

import org.springframework.stereotype.Service;

@Service
public class MoneyTransferLogConsole implements TransferLogs {

    @Override
    public boolean transferLog(String operationsLogs) {
        if (operationsLogs != null) {
            System.out.println(operationsLogs);
            return true;
        }
        return false;
    }
}

