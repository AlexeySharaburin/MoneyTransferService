package ru.netology.transfer_service.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class Amount {

    @NotNull(message = "Необходимо ввести сумму перевода")
    @Size(min = 0, message = "Сумма перевода не может быть меньше или равна 0")
    private final int value;

    private final String currency;

    public Amount(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value= " + value +
                ", currency= '" + currency + '\'' +
                '}';
    }
}


