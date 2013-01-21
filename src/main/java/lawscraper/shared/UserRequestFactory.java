package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.UserService;
import lawscraper.shared.proxies.UserProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface UserRequestFactory extends RequestFactory {
    @Service(value = UserService.class, locator = SpringServiceLocator.class)
    interface UserRequest extends RequestContext {
        Request<UserProxy> find(Long id);

        Request<List<UserProxy>> findAll();

        Request<Long> addUser(UserProxy user);

        Request<UserProxy> getCurrentUser();
    }

    UserRequest userRequest();
}
