package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

public enum IdentityType {

    MSISDN("msisdn"),
    EMAIL("email"),
    PARTY_CODE("party_code")
    ;

    private String identityTypeValue;

    IdentityType(final String identityTypeValue){
        this.identityTypeValue = identityTypeValue;
    }

    public String getIdentityTypeValue() {
        return identityTypeValue;
    }
}
