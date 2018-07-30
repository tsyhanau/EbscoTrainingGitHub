package com.epam.awsHandlers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.ListFunctionsRequest;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.epam.utils.FileTypes;
import com.epam.utils.RandomsGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class AwsHandler {

    public static AmazonS3 s3;
    private Properties awsProp = new Properties();
    private BasicAWSCredentials basicAWSCredentials;
    private static ThreadLocal<AwsHandler> instance = new ThreadLocal<>();
    AWSLambda lambdaClient;

    private AmazonDynamoDB dbClient;
    private DynamoDB dynamoDB;
    private Table table;
    private File file;
    private String bucketName;
    private String fileName;
    private String fileType;
    private String lambdaName;
    private String tableName;

    private AwsHandler() {
    }

    public static synchronized AwsHandler getAwsHandler() {
        if (instance.get() == null) {
            instance.set(new AwsHandler());
        }
        return instance.get();
    }

    public String getValueOfKey(String propertiesKey) {
        String value = awsProp.getProperty(propertiesKey);
        return value;
    }

    ////////////////////////////////// Log in
    public void createProperties(String awsPropertyPath) {
        try {

            awsProp.load
                    (new FileInputStream("../EbscoTraining/src/main/resources/aws.properties"));
        } catch (Exception e) {
            System.out.println("File in " + awsPropertyPath + "was not found! " + e);
        }
    }

    public void createBasicAWSCredentials(String userIdKey, String userSecretIdKey) {
        basicAWSCredentials = new BasicAWSCredentials(awsProp.getProperty(userIdKey),
                awsProp.getProperty(userSecretIdKey));
    }

    /////////////////////////////////  S3
    public void connectS3() {

        //s3 = new AmazonS3Client(basicAWSCredentials);
        //s3 = AwsClientBuilder.withClientConfiguration(basicAWSCredentials);

        s3 = AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();

    }

    public List<Bucket> getBuckets() {
        return s3.listBuckets();

    }

    public Bucket findBucket(String bucketName) {
        for (Bucket bucket : getBuckets()) {
            if (bucket.getName().equals(bucketName)) return bucket;
        }
        return null;
    }

    public boolean isExistBucket(String bucketName) {
        if (findBucket(bucketName).equals(null)) {
            return false;
        }
        return true;
    }
/*    public File findFileInBucket (String bucketNameKey, String fileName) {
        bucketName = getValueOfKey(bucketNameKey);
        File file = null;
        ObjectListing listing = s3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();

        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects(listing);
            summaries.addAll(listing.getObjectSummaries());
        }
*//*        for (S3ObjectSummary file : summaries) {
            System.out.println(file.getKey() + file.getLastModified().getTime() + "\n" );
        }*//*
        for (S3ObjectSummary f : summaries) {
            if (f.getKey().equals(fileName)) {
                return f.;
            }
        }
        return false;
    }*/

    public boolean isFileInBucketPresent(String bucketNameKey, String randomFileName) {

        String bName = awsProp.getProperty(bucketNameKey);
        ObjectListing listing = s3.listObjects(bName);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();

        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects(listing);
            summaries.addAll(listing.getObjectSummaries());
        }
        for (S3ObjectSummary file : summaries) {
            if (file.getKey().equals(randomFileName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteFileFromBucket(String bucketNameKey, String fileName) {
        bucketName = getValueOfKey(bucketNameKey);
        System.out.format("Bucketname is %s/// file name is %s///", bucketName, fileName);
        s3.deleteObject(bucketName, fileName);

    }
    /////////////////////////////////// dynamoBD

    public void initDynamoDB() {
        dbClient = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDB = new DynamoDB(dbClient);
    }

    public Table getTable(String tableName) {
        Table tab = null;
        try {
            tab = dynamoDB.getTable(tableName);
        } catch (AmazonDynamoDBException e) {
            System.out.println("Table " + tableName + " doesn't exist.");
        }
        return tab;
    }

    public boolean isExistTable(String tableName) {
        for (Table t : dynamoDB.listTables()) {
            if (t.getTableName().equals(tableName)) return true;
        }
        return false;
    }

    public boolean isItemInTable(String bucketNameKey, String tableNameKey, String randomFileName) {
        if(findItemInTable(bucketNameKey, tableNameKey, randomFileName) == null){return false;}
        return true;
    }

    public Item findItemInTable(String bucketNameKey, String tableNameKey, String randomFileName) {
        Item item;
        bucketName = getValueOfKey(bucketNameKey);
        tableName = getValueOfKey(tableNameKey);
        table = getTable(tableName);
        String filePathAttribute = bucketName + "/" + randomFileName;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Condition scanFilterCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(filePathAttribute));
        Map<String, Condition> conditions = new HashMap<String, Condition>();
        conditions.put("file Path", scanFilterCondition);

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName)
                .withScanFilter(conditions);
        ScanResult result = dbClient.scan(scanRequest);
        System.out.format("result is %s \n", result);
        for (Map<String, AttributeValue> i : result.getItems()) {
            String packageID = i.get("package Id").getS();
            double timeStamp = Double.parseDouble(i.get("originTimeStamp").getN());
            //int n = (int) timeStamp;
            item = table.getItem("package Id", packageID,
                    "originTimeStamp", timeStamp);
            System.out.format("Item was found <<<%s>>>", item);
            return item;
        }
        return null;
    }

    public void deleteItemFromDB(String bucketNameKey, String tableNameKey, String randomFileName) {
        Item item = findItemInTable(bucketNameKey,tableNameKey,randomFileName);
    }

/*    public void createItem() {
        Item item = new Item()
                .withPrimaryKey("package Id", "ebsco.aws.home.task.bucket",
                        "originTimeStamp", 4445)
                .withString("file Path", "path")
                .withString("file Type", "png");
        PutItemSpec itemSpec = new PutItemSpec()
                .withItem(item);
        table.putItem(itemSpec);
    }

    public void deleteItem() {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                //.withNameMap(new NameMap().with("file Type", "kkk"));
                .withPrimaryKey("package Id", "ebsco.aws.home.task.bucket",
                        "originTimeStamp", 1532529871935L);
        table.deleteItem(deleteItemSpec);
    }*/

    ///////////////////////////////////////// Lambda

    public void initLambdaService() {
        lambdaClient = AWSLambdaClientBuilder.standard().build();
    }

    public boolean isLambdaExist(String lambdaNameKey) {
        lambdaName = awsProp.getProperty(lambdaNameKey);
        ListFunctionsRequest request = new ListFunctionsRequest();
        ListFunctionsResult response = lambdaClient.listFunctions(request);
        //System.out.println(response.getFunctions());
        for (FunctionConfiguration fun : response.getFunctions()) {
            if (fun.getFunctionName().equals(lambdaName)) {
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////// Files


    public void putFileInToBucket(String bucketNameKey, File file) {
        bucketName = awsProp.getProperty(bucketNameKey);
        System.out.println("Uploading a new object + <<<" + file + ">>>  in to S3 backet >>>" + bucketName);
        //String typeOfFile = fileType.fileTypeAsString();

        TransferManager transferManager = TransferManagerBuilder.standard().build();

        Upload xfer = transferManager.upload(bucketName, file.getName(), file);
        try {
            xfer.waitForCompletion();
            System.out.println("file " + file.getName() + " was uploaded");
        } catch (Exception e) {
            System.out.println("Exeption" + e);
        }
    }

    public File createRandomFile(FileTypes typeOfFile) {
        fileName = RandomsGenerator.getRandomString();
        fileType = typeOfFile.fileTypeAsString();
        try {
            file = File.createTempFile(fileName, fileType, new File("../EbscoTraining/src/main/resources/"));
            file.deleteOnExit();
            return file;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("can't create file");
            return null;
        }
    }



}
