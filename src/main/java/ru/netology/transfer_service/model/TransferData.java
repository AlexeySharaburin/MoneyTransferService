package ru.netology.transfer_service.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class TransferData {

    @NotNull(message = "Необходимо ввести номер карты")
    @Size(min = 16, message = "Номер карты должен состоять из 16-и символов")
    private String cardFromNumber;

    @NotNull(message = "Необходимо ввести номер карты")
    @Size(min = 16, message = "Номер карты должен состоять из 16-и символов")
    private String cardToNumber;

    @NotNull(message = "Необходимо ввести срок действия карты")
    @Size(min = 4, message = "Срок действия карты должен состоять из 4-х символов")
    private String cardFromValidTill;

    @NotNull(message = "Необходимо ввести CVV")
    @Size(min = 3, message = "CVV должен состоять из 3-х символов")
    private String cardFromCVV;

    private Amount amount;

    public TransferData(String cardFromNumber, String cardToNumber, String cardFromValidTill, String cardFromCVV, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
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

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
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

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FromCardToCard{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", amount=" + amount +
                '}';
    }
}
