package ru.netology.transfer_service.model;

public class AmountCard {

    private double value;
    private String currency;

    public AmountCard(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value= " + value +
                ", currency= '" + currency + '\'' +
                '}';
    }
}