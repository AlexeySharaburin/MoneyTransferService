package ru.netology.transfer_service.service;

import ru.netology.transfer_service.model.DataOperation;

public interface TransferLogs {
    boolean transferLog(String operationId, DataOperation dataOperation);
}
