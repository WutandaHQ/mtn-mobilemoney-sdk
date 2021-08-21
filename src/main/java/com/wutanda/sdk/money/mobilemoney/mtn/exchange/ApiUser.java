package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public final class ApiUser implements Serializable {

    private String providerCallbackHost;

    public String getProviderCallbackHost() {
        return providerCallbackHost;
    }

    public void setProviderCallbackHost(String providerCallbackHost) {
        this.providerCallbackHost = providerCallbackHost;
    }

}
