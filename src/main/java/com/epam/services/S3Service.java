package com.epam.services;

import java.io.File;

public class S3Service extends ParentService {

    public void initiateS3() {
        awsHandler.connectS3();
    }

    public boolean isBucketWithCurrentNameExist(String bucketNameKey) {
        String bucketName = awsHandler.getValueOfKey(bucketNameKey);
        return awsHandler.isExistBucket(bucketName);
    }

    public void putFileInToBucket(String bucketNameKey, File randomFile) {
        awsHandler.putFileInToBucket(bucketNameKey, randomFile);
    }

    public boolean isFileWithCurrentNameInBucketExist(String bucketNameKey, String randomFileName) {
        return awsHandler.isFileInBucketPresent(bucketNameKey, randomFileName);
    }

    public void deleteItemFromBucket(String bucketNameKey, String fileName) {
        awsHandler.deleteFileFromBucket(bucketNameKey, fileName);
    }
}
