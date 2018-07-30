package lambdas;

/*
import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Event;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PutFunc implements RequestHandler<S3Event, String> {

    private AmazonDynamoDB client= AmazonDynamoDBClientBuilder.standard().build();
    private DynamoDB dynamoDB = new DynamoDB(client);
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


            table = dynamoDB.getTable(tableName);
            item = new Item()
                    .withPrimaryKey("package Id", bucketId,
                            "originTimeStamp", new Date().getTime())
                    .withString("file Path", bucketName +"/"+ fileName)
                    .withString("file Type", fileType);
            PutItemSpec itemSpec = new PutItemSpec()
                    .withItem(item);
            table.putItem(itemSpec);

            return "AWS Put Item Lambda works!";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}*/
