package lawscraper.shared.util;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/29/11
 * Time: 11:13 AM
 */
public class SpringRequestServlet extends RequestFactoryServlet{
    private static final long serialVersionUID = 905770135019194988L;

    static class LoquaciousExceptionHandler implements ExceptionHandler {
        private static final Logger LOG = LoggerFactory.getLogger(LoquaciousExceptionHandler.class);

        @Override
        public ServerFailure createServerFailure(Throwable throwable) {
            LOG.error("Server error", throwable);
            return new ServerFailure(throwable.getMessage(), throwable.getClass().getName(), null, true);
        }
    }

    public SpringRequestServlet() {
        super(new LoquaciousExceptionHandler());
    }
}
