package com.wutanda.sdk.money.mobilemoney.mtn.openapi.collections;

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

public final class Collections {

    private final String subscriptionKey;
    private final Environment environment;

    public Collections(final Environment environment,final String subscriptionKey){
        this.subscriptionKey = subscriptionKey;
        this.environment = environment;
    }

    public AccessToken requestAccessToken(final String apiUser, final String apiKey) throws SdkException {
        final String authorizationParam = apiUser.concat(":").concat(apiKey);
        final HttpRequest httpRequest = HttpRequest.builder()
                .url(environment.getBaseUrl().concat("/collection/token/"))
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

    public Boolean requestToPay(String accessToken, final RequestToPay requestToPay) throws SdkException {
        final HttpRequest<RequestToPay> requestToPayHttpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.POST)
                .url(environment.getBaseUrl().concat("/collection/v1_0/requesttopay"))
                .requestBody(requestToPay)
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Reference-Id", requestToPay.getExternalId())
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
            throw SdkException.internalError("Unable to complete request to pay operation");
        }
        return Boolean.TRUE;
    }

    public RequestToPay getRequestToPayInformation(String accessToken, final String transactionReference) throws SdkException {
        final HttpRequest requestToPayHttpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/collection/v1_0/requesttopay/").concat(transactionReference))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<RequestToPay> requestToPayHttpResponse = HttpTransport.send(requestToPayHttpRequest, new TypeToken<RequestToPay>() {
        }.getType());
        if (requestToPayHttpResponse.getResponseStatusCode() != 200) {
            throw SdkException.internalError("Unable to fetch request to pay information");
        }
        return requestToPayHttpResponse.getResponseBody();
    }

    public Balance getAccountBalance(final String accessToken) throws SdkException {
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/collection/v1_0/account/balance"))
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

    public Boolean validateAccountHolder(final String accessToken, final String identity, final IdentityType identityType) throws SdkException {
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/collection/v1_0/accountholder/").concat(identityType.getIdentityTypeValue()).concat("/").concat(identity).concat("/active"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<Void> accountHolderHttpResponse = HttpTransport.send(httpRequest, new TypeToken<Void>() {
        }.getType());
        return accountHolderHttpResponse.getResponseStatusCode() == 200;
    }

    public AccountHolderInfo getAccountHolderInfo(final String accessToken, final String msisdn) throws SdkException {
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/collection/v1_0/accountholder/msisdn/").concat(msisdn).concat("/basicuserinfo"))
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

    public Boolean deliveryNotification(final String accessToken, final String transactionReference, final String notificationMessage) throws SdkException {
        if (notificationMessage.length() > 160) {
            throw SdkException.badRequest("Notification message length is greater than 160");
        }
        final HttpRequest<Void> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/collection/v1_0/requesttopay/").concat(transactionReference).concat("/deliverynotification"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("notificationMessage", notificationMessage)
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .put("X-Target-Environment", environment.name().toLowerCase())
                        .put("Authorization", "Bearer ".concat(accessToken))
                        .build())
                .build();
        final HttpResponse<Void> httpResponse = HttpTransport.send(httpRequest, new TypeToken<Void>() {
        }.getType());
        return httpResponse.getResponseStatusCode() == 200;
    }


}
