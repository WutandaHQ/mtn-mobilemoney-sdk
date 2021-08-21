package com.wutanda.sdk.money.mobilemoney.mtn.exchange;

import com.google.gson.annotations.SerializedName;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.math.BigDecimal;

@Immutable
public final class AccessToken implements Serializable {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private BigDecimal expiresIn;

    private String scope;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("refresh_token_expired_in")
    private BigDecimal refreshTokenExpiresIn;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public BigDecimal getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(BigDecimal expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BigDecimal getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(BigDecimal refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
