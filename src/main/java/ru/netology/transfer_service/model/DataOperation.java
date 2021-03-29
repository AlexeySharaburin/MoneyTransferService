package ru.netology.transfer_service.model;

import java.math.BigDecimal;

public class DataOperation {

    private Card card;
    private String cardToNumber;
    private BigDecimal transferValue;
    private BigDecimal value;
    private BigDecimal fee;


    public DataOperation(Card card, String cardToNumber, BigDecimal transferValue, BigDecimal value, BigDecimal fee) {
        this.card = card;
        this.cardToNumber = cardToNumber;
        this.transferValue = transferValue;
        this.value = value;
        this.fee = fee;
    }

    public Card getCard() {
        return card;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }

    @Override
    public String toString() {
        return "DataOperation{" +
                "card=" + card +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", transferValue=" + transferValue +
                ", value=" + value +
                ", fee=" + fee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataOperation that = (DataOperation) o;
        return card.equals(that.card) && cardToNumber.equals(that.cardToNumber) && transferValue.equals(that.transferValue) && value.equals(that.value) && fee.equals(that.fee);
    }

}
