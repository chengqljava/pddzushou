
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles(profiles = { "chengql" })
public class GenTest {

//    @Autowired
//    private CodeGenService codeGenService;
//
    @Test
    public void testGen() throws Exception {
//
//        codeGenService.generatorCode("t_user");
   }

}
