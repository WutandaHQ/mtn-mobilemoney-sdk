package com.wutanda.sdk.money.mobilemoney.mtn.http.transport;

import com.wutanda.sdk.money.mobilemoney.mtn.domains.ContentType;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpMethod;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpRequest;
import com.wutanda.sdk.money.mobilemoney.mtn.domains.HttpResponse;
import com.wutanda.sdk.money.mobilemoney.mtn.exceptions.SdkException;
import com.wutanda.sdk.money.mobilemoney.mtn.utils.Json;
import com.wutanda.sdk.money.mobilemoney.mtn.utils.Xml;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyStore;


public class HttpTransport {

    public static <T> HttpResponse send(final HttpRequest<T> httpRequest, final Type type) throws SdkException {
        try {
            SSLContext sslContext = null;
            if (httpRequest.getSslConfiguration() != null) {
                final KeyStore keyStore = KeyStore.getInstance("JKS");
                final SSLContextBuilder sslContextBuilder = SSLContexts.custom();
                if (httpRequest.getSslConfiguration().getKeyStorePath() != null && httpRequest.getSslConfiguration().getKeyStorePassword() != null) {
                    keyStore.load(new FileInputStream(httpRequest.getSslConfiguration().getKeyStorePath()), httpRequest.getSslConfiguration().getKeyStorePassword().toCharArray());
                    sslContextBuilder.loadKeyMaterial(keyStore, httpRequest.getSslConfiguration().getKeyStorePassword().toCharArray());
                }
                if (httpRequest.getSslConfiguration().getTrustStorePassword() != null && httpRequest.getSslConfiguration().getTrustStorePassword() != null) {
                    final KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    trustStore.load(new FileInputStream(httpRequest.getSslConfiguration().getTrustStorePath()), httpRequest.getSslConfiguration().getTrustStorePassword().toCharArray());
                    sslContextBuilder.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy());
                }
                sslContext = sslContextBuilder.build();
            }
            final HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (sslContext != null) {
                httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1", "TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier()));
            }
            final CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            if (httpRequest.getHttpMethod() == HttpMethod.GET) {
                final HttpGet httpGet = new HttpGet(httpRequest.getUrl());
                httpGet.setHeader("Content-Type",httpRequest.getContentType().getContentType());
                httpRequest.getHeaders().keySet().stream().forEach(headerKey -> httpGet.addHeader(headerKey, httpRequest.getHeaders().get(headerKey)));
                final CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
                return createHttpResponse(httpRequest.getContentType(), closeableHttpResponse, type);
            } else if (httpRequest.getHttpMethod() == HttpMethod.POST) {
                final HttpPost httpPost = new HttpPost(httpRequest.getUrl());
                httpRequest.getHeaders().keySet().stream().forEach(headerKey -> httpPost.addHeader(headerKey, httpRequest.getHeaders().get(headerKey)));
                httpPost.setHeader("Content-Type",httpRequest.getContentType().getContentType());
                if (httpRequest.getContentType() == ContentType.APPLICATION_JSON) {
                    final String jsonRequest = Json.toJson(httpRequest.getRequestBody());
                    httpPost.setEntity(new StringEntity(jsonRequest, org.apache.http.entity.ContentType.APPLICATION_JSON));
                }
                if (httpRequest.getContentType() == ContentType.TEXT_XML) {
                    httpPost.setEntity(new StringEntity(Xml.toXml(httpRequest.getRequestBody()), org.apache.http.entity.ContentType.TEXT_XML));
                }
                final CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
                return createHttpResponse(httpRequest.getContentType(), closeableHttpResponse, type);
            } else {
                throw SdkException.internalError("Unsupported http method. Please try again");
            }
        } catch (Exception exception) {
            throw SdkException.internalError(exception.getMessage());
        }
    }

    private static HttpResponse createHttpResponse(final ContentType contentType, final CloseableHttpResponse closeableHttpResponse, final Type type) throws SdkException {
        try {
            final HttpResponse.Builder builder = HttpResponse.builder();
            if (contentType == ContentType.TEXT_XML) {
                builder.responseBody(Xml.fromXml(closeableHttpResponse.getEntity().getContent(), type.getClass()));
            }
            if (contentType == ContentType.APPLICATION_JSON || contentType == ContentType.APPLICATION_X_WWW_FORM_URLENCODED || contentType == ContentType.TEXT_PLAIN) {
                builder.responseBody(Json.fromJson(closeableHttpResponse.getEntity().getContent(), type));
            }
            builder.responseStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
            return builder.build();
        } catch (IOException ioException) {
            throw SdkException.internalError("Unable to parse data. Response code : ".concat(String.valueOf(closeableHttpResponse.getStatusLine().getStatusCode())));
        }
    }

}
