package com.wutanda.sdk.money.mobilemoney.mtn.openapi.apiuser;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.wutanda.sdk.money.mobilemoney.mtn.common.ApiKey;
import com.wutanda.sdk.money.mobilemoney.mtn.common.Environment;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.ContentType;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpMethod;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpRequest;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpResponse;
import com.wutanda.sdk.money.mobilemoney.mtn.exceptions.SdkException;
import com.wutanda.sdk.money.mobilemoney.mtn.exchange.ApiUser;
import com.wutanda.sdk.money.mobilemoney.mtn.http.transport.HttpTransport;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class User {

    private final Environment environment;

    public User(final Environment environment){
        this.environment = environment;
    }

    public void createAPIUser(final String subscriptionKey, final String providerCallbackHost, final String referenceId) throws SdkException {
        if (environment != Environment.SANDBOX) {
            throw SdkException.internalError("Only sandbox environment are allowed to create API user/ Please try on a separate environment.");
        }
        final ApiUser apiUser = new ApiUser();
        apiUser.setProviderCallbackHost(providerCallbackHost);
        final HttpRequest<ApiUser> httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.POST)
                .requestBody(apiUser)
                .url(environment.getBaseUrl().concat("/v1_0/apiuser"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("X-Reference-Id", referenceId)
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .build())
                .build();
        final HttpResponse<Void> httpResponse = HttpTransport.send(httpRequest, new TypeToken<Void>() {
        }.getType());
        if (httpResponse.getResponseStatusCode() != 201) {
            throw SdkException.internalError("Unable to create API user. Please try again");
        }
    }

    public ApiKey createAPIKey(final String subscriptionKey, final String referenceId) throws SdkException {
        final HttpRequest httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.POST)
                .url(environment.getBaseUrl().concat("/v1_0/apiuser/").concat(referenceId).concat("/apikey"))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .build())
                .build();
        final HttpResponse<ApiKey> httpResponse = HttpTransport.send(httpRequest, new TypeToken<ApiKey>() {
        }.getType());
        if (httpResponse.getResponseStatusCode() != 201) {
            throw SdkException.internalError("Unable to create API user. Please try again");
        }
        return httpResponse.getResponseBody();
    }

    public Boolean validateApiUser(final String subscriptionKey, final String referenceId) throws SdkException {
        final HttpRequest httpRequest = HttpRequest.builder()
                .contentType(ContentType.APPLICATION_JSON)
                .httpMethod(HttpMethod.GET)
                .url(environment.getBaseUrl().concat("/v1_0/apiuser/").concat(referenceId))
                .headers(ImmutableMap.<String, String>builder()
                        .put("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .build())
                .build();
        final HttpResponse<Void> httpResponse = HttpTransport.send(httpRequest, new TypeToken<Void>() {
        }.getType());
        return httpResponse.getResponseStatusCode() != 201;
    }


}
