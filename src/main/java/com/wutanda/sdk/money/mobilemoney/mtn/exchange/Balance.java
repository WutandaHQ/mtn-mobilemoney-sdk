package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public final class Balance implements Serializable {

    private String availableBalance;
    private String currency;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
