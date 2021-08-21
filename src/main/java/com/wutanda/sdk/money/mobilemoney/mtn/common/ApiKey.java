package com.wutanda.sdk.money.mobilemoney.mtn.common;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public final class ApiKey implements Serializable {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
