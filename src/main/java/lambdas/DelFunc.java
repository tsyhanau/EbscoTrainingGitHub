package lambdas;

/*import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class DelFunc implements RequestHandler<S3Event, String> {

    private AmazonDynamoDB dbClient= AmazonDynamoDBClientBuilder.standard().build();
    private DynamoDB dynamoDB = new DynamoDB(dbClient);
    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();//.defaultClient();
    S3EventNotificationRecord record;
    Table table;
    Item item;
    String bucketName;
    String bucketId;
    String tableName = "EbscoTable";
    String fileName;
    String fileType;
    Pattern pattern = Pattern.compile("\\.\\w{2,4}($|\\?)");
    Matcher m;


    @Override
    public String handleRequest(S3Event event, Context context) {

        try {

            record = event.getRecords().get(0);
            fileName = record.getS3().getObject().getKey();
            bucketId = record.getS3().getBucket().getArn();
            bucketName = record.getS3().getBucket().getName();
            m = pattern.matcher(fileName);
            if (m.find()) {
                fileType = m.group(0);}

            /////////////////////////////
            table = dynamoDB.getTable(tableName);

            String filePathAttribute = bucketName + "/" + fileName;

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

            String packageID = "";
            double timeStamp = 0;

            for (Map<String, AttributeValue> i : result.getItems()) {

                packageID = i.get("package Id").getS();
                timeStamp = Double.parseDouble(i.get("originTimeStamp").getN());}

            table.deleteItem("package Id", packageID, "originTimeStamp", timeStamp);

            return "AWS Put Item Lambda works!";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}*/
