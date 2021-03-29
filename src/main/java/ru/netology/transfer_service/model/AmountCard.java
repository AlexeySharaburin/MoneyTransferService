package ru.netology.transfer_service.model;

import java.math.BigDecimal;

public class AmountCard {

    private final BigDecimal value;
    private final String currency;

    public AmountCard(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value= " + value +
                ", currency= '" + currency + '\'' +
                '}';
    }
}