# mtn-mobilemoney-java-sdk

###Introduction

The MTN Mobile Money platform is currently available in 15 countries serving millions of subscribers. The platform processes at least $1b transaction volume monthly, and has thousands of providers integrated to the platform.

The MTN Mobile Money platform introduced the OpenAPI that serves as the central integration point to the platform, aimed at harmonizing integration processes across its footprints.

There are three main use case categories of the OpenAPI;

1. Collections
2. Disbursements
3. Remittances


The following are the details of the use cases available on the MTN Mobile Money Open API;

1. RequestToPay - This API is used in cases where you need a registered mobile money customer to make a payment, before/after a service, requiring authorization and consent from the customer. In this case, the customer is prompted about the transaction (most times via a USSD push), and on approval, the transaction is either in SUCCESSFUL, FAILED or PENDING state.


2. Transfer - The use case allow you to transfer money from your provider account to a customer. In this case, a provider can use this service to exchange cash/service for e-money on the Mobile Money platform.


3. AccountHolder Validation - The use case allows a third party application to verify and/or validate if an accountholder is active and able to receive funds. It however does not validate if the customer can receive an amount into the wallet.


4. Get Balance - The API checks the default account balance of a registered account holder.


5. Delivery Notification - The service provides an additional notification to the customer after a financial transaction is completed successfully.


### Installation
The MTN Mobile Money Java SDK can be added as a library to your Java project as a JAR dependency.



####1 - Create API user (sandbox users)

       final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);



####2 - Create API Key

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build(); 
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(SUBSCRIPTION_KEY, apiUser);



####3 - Validate API User

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        Boolean validateStatus = mtnMobileMoney.user().validateApiUser(SUBSCRIPTION_KEY, apiUser);



####4 - Collections - access token

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());



####5 - Collections - Request to pay

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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


        mtnMobileMoney.collections().requestToPay(accessToken.getAccessToken(), requestToPay);



####6 - Collections - Request to pay information

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();
        //send a request to pay as in 5.
        final RequestToPay requestToPay = mtnMobileMoney.collections().getRequestToPayInformation(accessToken.getAccessToken(), transactionReference);



####7 - Collections - Account balance

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.collections().getAccountBalance(accessToken.getAccessToken());


####8 - Collections - Validate account holder

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        Boolean validateAccountHolderStatus = mtnMobileMoney.collections().validateAccountHolder(accessToken.getAccessToken(), "4111111111", IdentityType.MSISDN);

####9 - Collections - Accountholder information

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(COLLECTION_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.collections().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.collections().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");

####10 - Collections - Request to Pay delivery notification

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .collectionSubscriptionKey(COLLECTION_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(COLLECTION_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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

        Boolean requestToPayStatus = mtnMobileMoney.collections().requestToPay(accessToken.getAccessToken(), requestToPay);
        Boolean deliveryNotificationStatus = mtnMobileMoney.collections().deliveryNotification(accessToken.getAccessToken(),transactionReference,"Notification Message");



####10 - Disbursements - Create access token

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());


####11 - Disbursements - Transfer

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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

        Boolean transferStatus = mtnMobileMoney.disbursements().transfer(accessToken.getAccessToken(), transfer);

####12 - Disbursements - Transfer Info

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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

        Boolean transferStatus = mtnMobileMoney.disbursements().transfer(accessToken.getAccessToken(), transfer);
        final Transfer transferResponse = mtnMobileMoney.disbursements().getTransferInfo(accessToken.getAccessToken(),transactionReference);



####13 - Disbursements - Account Balance

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.disbursements().getAccountBalance(accessToken.getAccessToken());


####14 - Disbursements - Account holder information

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .disbursementSubscriptionKey(DISBURSEMENT_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(DISBURSEMENT_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(DISBURSEMENT_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.disbursements().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.disbursements().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");



####15 - Remittance - Create access token

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();
        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());



####16 - Remittance - Transfer

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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

        Boolean transferStatus = mtnMobileMoney.remittances().transfer(accessToken.getAccessToken(), transfer);


####17 - Remittance - Transfer information


        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
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

        Boolean transferStatus = mtnMobileMoney.remittances().transfer(accessToken.getAccessToken(), transfer);
        if(transferStatus){
            final Transfer transfer = mtnMobileMoney.remittances().getTransferInfo(accessToken.getAccessToken(),transactionReference);
        }

####18 - Remittance - Account balance

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());
        final Balance balance = mtnMobileMoney.remittances().getAccountBalance(accessToken.getAccessToken());


####19 - Remittance - Account holder information

        final MTNMobileMoney mtnMobileMoney = MTNMobileMoney.builder()
                .environment(Environment.SANDBOX)
                .remittanceSubscriptionKey(REMITTANCE_SUBSCRIPTION_KEY)
                .build();

        final String apiUser = UUID.randomUUID().toString();
        mtnMobileMoney.user().createAPIUser(REMITTANCE_SUBSCRIPTION_KEY, CALLBACK_HOST, apiUser);
        final ApiKey apiKey = mtnMobileMoney.user().createAPIKey(REMITTANCE_SUBSCRIPTION_KEY, apiUser);
        final AccessToken accessToken = mtnMobileMoney.remittances().requestAccessToken(apiUser, apiKey.getApiKey());
        final AccountHolderInfo accountHolderInfo = mtnMobileMoney.remittances().getAccountHolderInfo(accessToken.getAccessToken(), "4111111111");