package lawscraper.server.service;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.user.User;
import lawscraper.server.repositories.RepositoryBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 9:33 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Qualifier("repositoryBaseImpl") @Autowired
    private RepositoryBase<LawDocumentPart> lawPartRepository;

    @Before
    public void setUp() throws Exception {
        lawPartRepository.setEntityClass(LawDocumentPart.class);
    }

    @Test
    public void testAddUser() throws Exception {
        Long userId = userService.addUser(new User("user", "pass"));
        User user = userService.find(userId);
        assertEquals("user", user.getUserName());
    }
}
