package com.wutanda.sdk.money.mobilemoney.mtn.openapi.disbursements;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.wutanda.sdk.money.mobilemoney.mtn.common.Environment;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.ContentType;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpMethod;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpRequest;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpResponse;
import com.wutanda.sdk.money.mobilemoney.mtn.exceptions.SdkException;
import com.wutanda.sdk.money.mobilemoney.mtn.exchange.*;
import com.wutanda.sdk.money.mobilemoney.mtn.http.transport.HttpTransport;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Disbursements {

    private final Environment environment;
    private final String subscriptionKey;

    public Disbursements(final Environment environment,final String subscriptionKey){
        this.environment = environment;
        this.subscriptionKey = subscriptionKey;
    }

    public AccessToken requestAccessToken(final String apiUser, final String apiKey) throws SdkException {
        final String authorizationParam = apiUser.concat(":").concat(apiKey);
        final HttpRequest httpRequest = HttpRequest.builder()
                .url(environment.getBaseUrl().concat("/disbursement/token/"))
                .contentType(ContentType.TEXT_PLAIN)
                .httpMethod(HttpMethod.POST)
                .headers(ImmutableMap.<String, String>builder()
                        .put("Authorization", "Basic ".concat(new String(Base64.getEncoder().encode(authorizationParam.getBytes(StandardCharsets.UTF_8)))))
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .build())
                .build();
        final HttpResponse<AccessToken> accessTokenHttpResponse = HttpTransport.send(httpRequest, new TypeToken<AccessToken>() {
        }.getType());
        if (accessTokenHttpResponse.getResponseStatusCode() != 200) {
            throw SdkException.internalError("Unable to request access token. Please try again");
        }
        return accessTokenHttpResponse.getResponseBody();
    }

    public Boolean transfer(final String accessToken, final Transfer transfer) throws SdkException {
        final HttpRequest<RequestToPay> requestToPayHttpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.POST)
                .url(environment.getBaseUrl().concat("/disbursement/v1_0/transfer"))
                .requestBody(transfer)
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Reference-Id", transfer.getExternalId())
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<Void> httpResponse = HttpTransport.send(requestToPayHttpRequest, new TypeToken<Void>() {
        }.getType());
        if (httpResponse.getResponseStatusCode() == 401) {
            throw SdkException.unauthorized("Unable to authorize this operation");
        }
        if (httpResponse.getResponseStatusCode() != 202) {
            throw SdkException.internalError("Unable to complete transfer operation");
        }
        return Boolean.TRUE;
    }

    public Transfer getTransferInfo(final String accessToken, final String paymentReference) throws SdkException {
        final HttpRequest transferHttpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/disbursement/v1_0/transfer/").concat(paymentReference))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<Transfer> disbursementHttpResponse = HttpTransport.send(transferHttpRequest, new TypeToken<Transfer>() {
        }.getType());
        if (disbursementHttpResponse.getResponseStatusCode() != 200) {
            throw SdkException.internalError("Unable to fetch disbursement information");
        }
        return disbursementHttpResponse.getResponseBody();
    }

    public Balance getAccountBalance(final String accessToken) throws SdkException {
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/disbursement/v1_0/account/balance"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<Balance> balanceHttpResponse = HttpTransport.send(httpRequest, new TypeToken<Balance>() {
        }.getType());
        return balanceHttpResponse.getResponseBody();
    }

    public AccountHolderInfo getAccountHolderInfo(final String accessToken, final String msisdn) throws SdkException {
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/disbursement/v1_0/accountholder/msisdn/").concat(msisdn).concat("/basicuserinfo"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<AccountHolderInfo> accountHolderHttpResponse = HttpTransport.send(httpRequest, new TypeToken<AccountHolderInfo>() {
        }.getType());
        return accountHolderHttpResponse.getResponseBody();
    }


}
