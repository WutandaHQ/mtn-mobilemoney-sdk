package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public final class RequestToPay implements Serializable {

    private String amount;
    private String currency;
    private String externalId;
    private String payerMessage;
    private String payerNote;
    private Payer payer;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayerNote() {
        return payerNote;
    }

    public void setPayerNote(String payerNote) {
        this.payerNote = payerNote;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }
}
