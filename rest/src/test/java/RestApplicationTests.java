import com.chaikovsky.RestApplication;
import controllerTest.testConfig.ControllerContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


@ContextConfiguration(classes = {ControllerContext.class})
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = RestApplication.class)
public class RestApplicationTests {

	@Test
	public void contextLoads() {
	}

}
