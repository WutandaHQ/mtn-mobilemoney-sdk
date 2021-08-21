package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import java.io.Serializable;

public final class Transfer implements Serializable {

    private String amount;
    private String currency;
    private String externalId;
    private String payerMessage;
    private String payerNote;
    private Payer payee;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Payer getPayee() {
        return payee;
    }

    public void setPayee(Payer payee) {
        this.payee = payee;
    }
}
