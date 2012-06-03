package lawscraper.server.service;

import lawscraper.server.entities.user.User;
import lawscraper.server.repositories.LawPartRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/16/12
 * Time: 9:33 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    LawPartRepository lawPartRepository;

    @Test
    @Ignore
    public void testFind() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {
        Long userId = userService.addUser(new User("user", "pass"));
        User user = userService.find(userId);
        assertEquals("user", user.getUserName());
    }
}
