package com.epam.services;

import com.epam.awsHandlers.AwsHandler;

public class ParentService {

    public AwsHandler awsHandler;

    public ParentService() {
        this.awsHandler = AwsHandler.getAwsHandler();
    }
}
