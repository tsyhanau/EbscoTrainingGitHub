import com.epam.services.DynamoDbService;
import com.epam.services.LambdaService;
import com.epam.services.S3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CheckParametersOfAwsServisecTest extends BeforeAfterTest {

    private S3Service s3Service = new S3Service();
    private LambdaService lambdaService = new LambdaService();
    private DynamoDbService dynamoDbService = new DynamoDbService();

    @ParameterizedTest
    @ValueSource(strings = {BUCKET_NAME_KEY})
    public void S3test(String bucketNameKey) {
        s3Service.initiateS3();
        Assertions.assertTrue(s3Service.isBucketWithCurrentNameExist(bucketNameKey));
    }

    @ParameterizedTest
    @ValueSource(strings = {TABLE_NAME_KEY})
    public void dynamoDbTest(String tableNameKey) {
        dynamoDbService.initiateDynamoDB();
        Assertions.assertTrue(dynamoDbService.isTableWithCurrentNameExist(tableNameKey));
    }

    @ParameterizedTest
    @ValueSource(strings = {LAMBDA_NAME_KEY})
    public void lambdaTest(String lambdaNameKey) {
        lambdaService.initiateAWSLambdaService();
        Assertions.assertTrue(lambdaService.isLambaWithCurrentNameExist(lambdaNameKey));
    }
}
