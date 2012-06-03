package lawscraper.server.controlers;

import lawscraper.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/1/12
 * Time: 8:17 PM
 */
@Controller
public class AuthenticateUserImpl {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/failedlogin.do", method = RequestMethod.GET)
    public void failedLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            response.getWriter().printf("{ \"Access Denied\": \"true\" }");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public void successfullLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            response.getWriter().printf("{ \"Login\": \"true\" }");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/notallowed.do", method = RequestMethod.GET)
    public void notAllowed(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("NOOOOOOOO!!!!!!!!!!! NOT ALLOWED!!!!!!");
    }
}

