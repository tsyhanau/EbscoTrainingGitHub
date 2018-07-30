package com.epam.services;

public class DynamoDbService extends ParentService {

    public void initiateDynamoDB() {
        awsHandler.initDynamoDB();
    }

    public boolean isTableWithCurrentNameExist(String tableNameKey) {
        return awsHandler.isExistTable(tableNameKey);
    }

    public boolean isItemInTablePresent(String bucketNameKey, String tableNameKey, String randomFileName) {
        return awsHandler.isItemInTable(bucketNameKey, tableNameKey, randomFileName);
    }
}