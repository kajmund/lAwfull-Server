package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.user.User;
import lawscraper.shared.UserRole;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/30/12
 * Time: 10:49 AM
 */
@ProxyFor(value = User.class)
public interface UserProxy extends ValueProxy {
    String getUserName();

    String getPassword();

    String geteMail();

    void seteMail(String eMail);

    void setUserName(String userName);

    void setPassword(String password);

    UserRole getUserRole();
}
