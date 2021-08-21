package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public final class Payer implements Serializable {

    private String partyIdType;
    private String partyId;

    public String getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(String partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
