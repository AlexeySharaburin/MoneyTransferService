package ru.netology.transfer_service.model;

public class Card {

    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private AmountCard amount;

    public Card(String cardFromNumber, String cardFromValidTill, String cardFromCVV, AmountCard amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public AmountCard getAmountCard() {
        return amount;
    }

    public void setAmountCard(AmountCard amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", amount= " + amount +
                '}';
    }
}
