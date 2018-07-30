package com.epam.services;

public class DynamoDbService extends ParentService {


    public void initiateDynamoDB() {
        awsHandler.initDynamoDB();

    }

    public boolean isTableWithCurrentNameExist(String tableNameKey) {
        String tableName = awsHandler.getValueOfKey(tableNameKey);
        return awsHandler.isExistTable(tableName);
    }

/*    public void createItemInTable() {
        awsHandler.createItem();
    }

    public void deleteItemFromTable() {
        awsHandler.deleteItem();
    }*/

    public boolean isItemInTablePresent(String bucketNameKey, String tableNameKey, String randomFileName) {
        return awsHandler.isItemInTable(bucketNameKey, tableNameKey, randomFileName);
    }

    public void deleteItemFromTable(String bucketNameKey, String tableNameKey, String randomFileName) {
        awsHandler.deleteItemFromDB(bucketNameKey, tableNameKey, randomFileName);
    }
}