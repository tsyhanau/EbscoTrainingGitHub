package com.epam.services;

public class LambdaService extends ParentService{

    public void initiateAWSLambdaService(){
        awsHandler.initLambdaService();
    }

    public boolean isLambaWithCurrentNameExist(String lambdaNameKey) {
        return awsHandler.isLambdaExist(lambdaNameKey);
    }
}
