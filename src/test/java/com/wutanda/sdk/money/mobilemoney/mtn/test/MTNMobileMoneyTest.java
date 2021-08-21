package com.wutanda.sdk.money.mobilemoney.mtn.test;

import com.wutanda.sdk.money.mobilemoney.mtn.MTNMobileMoney;
import com.wutanda.sdk.money.mobilemoney.mtn.common.ApiKey;
import com.wutanda.sdk.money.mobilemoney.mtn.common.Environment;
import com.wutanda.sdk.money.mobilemoney.mtn.exceptions.SdkException;
import com.wutanda.sdk.money.mobilemoney.mtn.exchange.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MTNMobileMoneyTest {

    private final static String COLLECTION_SUBSCRIPTION_KEY = "a52ef688876a49aeaa4720cce0db5cdc";
    private final static String DISBURSEMENT_SUBSCRIPTION_KEY = "d95a9caeba4d49aeb12e36a8264809b0";
    private final static String REMITTANCE_SUBSCRIPTION_KEY = "421e8588c1784131a3bcd83e54394e45";
    private final static String CALLBACK_HOST = "https://www.sandbox.wutanda.com/sdk/callback";

    @Test
    public void createAPIUser() {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
    }

    @Test
    public void testCreateApiKey() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        assertNotNull(apiKey);
    }

    @Test
    public void testValidateApiUser() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        assertTrue(mtnMobileMoney.user().validateApiUser(COLLECTION_SUBSCRIPTION_KEY, apiUser));
    }

    @Test
    public void testCollectionCreateAccessToken() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        assertNotNull(accessToken);
    }

    @Test
    public void testCollectionsRequestToPay() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final RequestToPay requestToPay = new RequestToPay();
        requestToPay.setAmount("1000");
        requestToPay.setCurrency("EUR");
        requestToPay.setExternalId(transactionReference);
        requestToPay.setPayerMessage("Payment to payer");
        requestToPay.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("1111111111");
        payer.setPartyIdType("MSISDN");
        requestToPay.setPayer(payer);


        assertTrue(mtnMobileMoney.collections().requestToPay(accessToken.getAccessToken(), requestToPay));
    }

    @Test
    public void testRequestToPayInfo() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final RequestToPay requestToPay = new RequestToPay();
        requestToPay.setAmount("1000");
        requestToPay.setCurrency("EUR");
        requestToPay.setExternalId(transactionReference);
        requestToPay.setPayerMessage("Payment to payer");
        requestToPay.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("1111111111");
        payer.setPartyIdType("MSISDN");
        requestToPay.setPayer(payer);

        assertTrue(mtnMobileMoney.collections().requestToPay(accessToken.getAccessToken(), requestToPay));
        final RequestToPay requestToPayResponse = mtnMobileMoney.collections().getRequestToPayInformation(accessToken.getAccessToken(), transactionReference);
        assertNotNull(requestToPayResponse);
        assertEquals(requestToPayResponse.getAmount(), requestToPay.getAmount());
        assertEquals(requestToPayResponse.getCurrency(), requestToPay.getCurrency());
        assertEquals(requestToPayResponse.getPayer().getPartyId(), requestToPay.getPayer().getPartyId());
        assertEquals(requestToPayResponse.getPayer().getPartyIdType(), requestToPay.getPayer().getPartyIdType());
        assertEquals(requestToPayResponse.getExternalId(), requestToPay.getExternalId());
    }

    @Test
    public void testAccountBalance() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.collections().getAccountBalance(accessToken.getAccessToken());
        assertNotNull(balance);
    }

    @Test
    public void testValidateAccountHolder() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        assertTrue(mtnMobileMoney.collections().validateAccountHolder(accessToken.getAccessToken(), "4111111111", IdentityType.MSISDN));
    }

    @Test
    public void testCollectionAccountHolderInfo() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.collections().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");
        assertNotNull(accountHolderInfo);
        assertEquals(accountHolderInfo.getFamilyName(), "Box");
        assertEquals(accountHolderInfo.getGivenName(), "Sand");
        assertEquals(accountHolderInfo.getName(), "Sand Box");
        assertEquals(accountHolderInfo.getBirthDate(), "1976-08-13");
    }

    @Test
    public void testRequestToPayDeliveryNotification() throws SdkException{
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final RequestToPay requestToPay = new RequestToPay();
        requestToPay.setAmount("1000");
        requestToPay.setCurrency("EUR");
        requestToPay.setExternalId(transactionReference);
        requestToPay.setPayerMessage("Payment to payer");
        requestToPay.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("1111111111");
        payer.setPartyIdType("MSISDN");
        requestToPay.setPayer(payer);

        assertTrue(mtnMobileMoney.collections().requestToPay(accessToken.getAccessToken(), requestToPay));
        final RequestToPay requestToPayResponse = mtnMobileMoney.collections().getRequestToPayInformation(accessToken.getAccessToken(), transactionReference);
        assertNotNull(requestToPayResponse);
        mtnMobileMoney.collections().deliveryNotification(accessToken.getAccessToken(),transactionReference,"Notification Message");
    }

    @Test
    public void testDisbursementsCreateAccessToken() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());
        assertNotNull(accessToken);
        assertNotNull(accessToken.getAccessToken());
    }

    @Test
    public void testDisbursementTransfer() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final Transfer transfer = new Transfer();
        transfer.setAmount("1");
        transfer.setCurrency("EUR");
        transfer.setExternalId(transactionReference);
        transfer.setPayerMessage("Payment to payer");
        transfer.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("46733123454");
        payer.setPartyIdType("msisdn");
        transfer.setPayee(payer);

        assertTrue(mtnMobileMoney.disbursements().transfer(accessToken.getAccessToken(), transfer));
    }

    @Test
    public void transferInfo() throws SdkException{
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final Transfer transfer = new Transfer();
        transfer.setAmount("1");
        transfer.setCurrency("EUR");
        transfer.setExternalId(transactionReference);
        transfer.setPayerMessage("Payment to payer");
        transfer.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("46733123454");
        payer.setPartyIdType("MSISDN");
        transfer.setPayee(payer);

        assertTrue(mtnMobileMoney.disbursements().transfer(accessToken.getAccessToken(), transfer));
        final Transfer transferResponse = mtnMobileMoney.disbursements().getTransferInfo(accessToken.getAccessToken(),transactionReference);
        assertNotNull(transferResponse);
        assertEquals(transferResponse.getAmount(), transfer.getAmount());
        assertEquals(transferResponse.getCurrency(), transfer.getCurrency());
        assertEquals(transferResponse.getPayee().getPartyId(), transfer.getPayee().getPartyId());
        assertEquals(transferResponse.getPayee().getPartyIdType(), transfer.getPayee().getPartyIdType());
        assertEquals(transferResponse.getExternalId(), transfer.getExternalId());
    }

    @Test
    public void testDisbursementAccountBalance() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.disbursements().getAccountBalance(accessToken.getAccessToken());
        assertNotNull(balance);
    }

    @Test
    public void testDisbursementAccountHolderInfo() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.disbursements().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");
        assertNotNull(accountHolderInfo);
        assertEquals(accountHolderInfo.getFamilyName(), "Box");
        assertEquals(accountHolderInfo.getGivenName(), "Sand");
        assertEquals(accountHolderInfo.getName(), "Sand Box");
        assertEquals(accountHolderInfo.getBirthDate(), "1976-08-13");
    }

    @Test
    public void testRemittanceAccessToken() throws SdkException{
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());
        assertNotNull(accessToken);
        assertNotNull(accessToken.getAccessToken());
    }


    @Test
    public void testRemittanceTransfer() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final Transfer transfer = new Transfer();
        transfer.setAmount("1");
        transfer.setCurrency("EUR");
        transfer.setExternalId(transactionReference);
        transfer.setPayerMessage("Payment to payer");
        transfer.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("46733123454");
        payer.setPartyIdType("msisdn");
        transfer.setPayee(payer);

        assertTrue(mtnMobileMoney.remittances().transfer(accessToken.getAccessToken(), transfer));
    }

    @Test
    public void testRemittanceTransferInfo() throws SdkException{
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());

        final String transactionReference = UUID.randomUUID().toString();
        final Transfer transfer = new Transfer();
        transfer.setAmount("1");
        transfer.setCurrency("EUR");
        transfer.setExternalId(transactionReference);
        transfer.setPayerMessage("Payment to payer");
        transfer.setPayerNote("Note to the payer");
        final Payer payer = new Payer();
        payer.setPartyId("46733123454");
        payer.setPartyIdType("MSISDN");
        transfer.setPayee(payer);

        assertTrue(mtnMobileMoney.remittances().transfer(accessToken.getAccessToken(), transfer));
        final Transfer transferResponse = mtnMobileMoney.remittances().getTransferInfo(accessToken.getAccessToken(),transactionReference);
        assertNotNull(transferResponse);
        assertEquals(transferResponse.getAmount(), transfer.getAmount());
        assertEquals(transferResponse.getCurrency(), transfer.getCurrency());
        assertEquals(transferResponse.getPayee().getPartyId(), transfer.getPayee().getPartyId());
        assertEquals(transferResponse.getPayee().getPartyIdType(), transfer.getPayee().getPartyIdType());
        assertEquals(transferResponse.getExternalId(), transfer.getExternalId());
    }

    @Test
    public void testRemittanceAccountBalance() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.remittances().getAccountBalance(accessToken.getAccessToken());
        assertNotNull(balance);
    }

    @Test
    public void testRemittanceAccountHolderInfo() throws SdkException {
        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser));
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.remittances().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");
        assertNotNull(accountHolderInfo);
        assertEquals(accountHolderInfo.getFamilyName(), "Box");
        assertEquals(accountHolderInfo.getGivenName(), "Sand");
        assertEquals(accountHolderInfo.getName(), "Sand Box");
        assertEquals(accountHolderInfo.getBirthDate(), "1976-08-13");
    }


}
