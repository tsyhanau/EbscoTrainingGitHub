package com.epam.services;

public class ClientService extends ParentService{

    public void logInAWS(String awsPropertyPath, String userIdKey, String userSecretIdKey) {

        awsHandler.createProperties(awsPropertyPath);
        awsHandler.createBasicAWSCredentials(userIdKey, userSecretIdKey);
    }
}
