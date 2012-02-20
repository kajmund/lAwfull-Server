package lawscraper.server.locators;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/26/11
 * Time: 4:11 PM
 */
public class SpringServiceLocator implements ServiceLocator {
    public Object getInstance(Class<?> clazz) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
                RequestFactoryServlet.getThreadLocalServletContext());
        return context.getBean(clazz);
    }


}
