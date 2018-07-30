import com.epam.services.DynamoDbService;
import com.epam.services.S3Service;
import com.epam.utils.CustomArgumentsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckLambdaTriggerTest extends BeforeAfterTest {

    private S3Service s3Service = new S3Service();
    private DynamoDbService dynamoDbService = new DynamoDbService();

    @BeforeEach
    public void beforeTests() {
        s3Service.initiateS3();
        dynamoDbService.initiateDynamoDB();
    }

    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsGenerator.class)
    void uploadFileAndCheckThatTriggerWorksTest(File randomFile) {
        String randomFileName = randomFile.getName();
        s3Service.putFileInToBucket(BUCKET_NAME_KEY, randomFile);
        assertTrue(s3Service.isFileWithCurrentNameInBucketExist(BUCKET_NAME_KEY, randomFileName));
        assertTrue(dynamoDbService.isItemInTablePresent(BUCKET_NAME_KEY, TABLE_NAME_KEY, randomFileName));
        s3Service.deleteItemFromBucket(BUCKET_NAME_KEY, randomFileName);
    }

    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsGenerator.class)
    void uploadFileThanDeleteItAndCheckThatTriggerWorksTest(File randomFile) {
        String randomFileName = randomFile.getName();
        s3Service.putFileInToBucket(BUCKET_NAME_KEY, randomFile);
        assertTrue(s3Service.isFileWithCurrentNameInBucketExist(BUCKET_NAME_KEY, randomFileName));
        assertTrue(dynamoDbService.isItemInTablePresent(BUCKET_NAME_KEY, TABLE_NAME_KEY, randomFileName));
        s3Service.deleteItemFromBucket(BUCKET_NAME_KEY, randomFileName);
        assertFalse(dynamoDbService.isItemInTablePresent(BUCKET_NAME_KEY, TABLE_NAME_KEY, randomFileName));
    }
}

