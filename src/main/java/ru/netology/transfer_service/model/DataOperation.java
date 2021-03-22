package ru.netology.transfer_service.model;

import java.math.BigDecimal;

//public class DataOperation {
//
//    private Card card;
//    private String cardToNumber;
//    private int transferValue;
//    private int value;
//    private int fee;
//
//    public DataOperation(Card card, String cardToNumber, int transferValue, int value, int fee) {
//        this.card = card;
//        this.cardToNumber = cardToNumber;
//        this.transferValue = transferValue;
//        this.value = value;
//        this.fee = fee;
//    }
//
//    public Card getCard() {
//        return card;
//    }
//
//    public void setCard(Card card) {
//        this.card = card;
//    }
//
//    public String getCardToNumber() {
//        return cardToNumber;
//    }
//
//    public void setCardToNumber(String cardToNumber) {
//        this.cardToNumber = cardToNumber;
//    }
//
//    public int getTransferValue() {
//        return transferValue;
//    }
//
//    public void setTransferValue(int transferValue) {
//        this.transferValue = transferValue;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//
//    public int getFee() {
//        return fee;
//    }
//
//    public void setFee(int fee) {
//        this.fee = fee;
//    }
//}

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

    public void setCard(Card card) {
        this.card = card;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(BigDecimal transferValue) {
        this.transferValue = transferValue;
    }
}
