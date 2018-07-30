import com.epam.services.ClientService;
import org.junit.jupiter.api.BeforeEach;

public class BeforeAfterTest {

    private static final String AWS_PROPERTY_PATH = "../EbscoTraining/src/main/resources/aws.properties";
    private static final String USER_ID_KEY = "aws_access_key_id";
    private static final String USER_SECRET_ID_KEY = "aws_secret_access_key";

    protected static final String BUCKET_NAME_KEY = "bucket_name_key";
    protected static final String TABLE_NAME_KEY = "table_name_key";
    protected static final String LAMBDA_NAME_KEY = "lambda_name_key";

    ClientService clientService = new ClientService();

    @BeforeEach
    public void logInBeforeEachTestMethod(){
        System.out.println("Before test::::::::::::::::::::::::::::::::::");
        clientService.logInAWS(AWS_PROPERTY_PATH, USER_ID_KEY, USER_SECRET_ID_KEY);
    }
}
