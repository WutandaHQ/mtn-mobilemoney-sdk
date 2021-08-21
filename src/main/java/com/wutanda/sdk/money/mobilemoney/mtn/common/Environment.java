package com.wutanda.sdk.money.mobilemoney.mtn.common;

public enum Environment {

    SANDBOX("https://sandbox.momodeveloper.mtn.com"),
    PRODUCTION("https://momodeveloper.mtn.com")
    ;

    private String baseUrl;

    Environment(final String baseUrl){
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}