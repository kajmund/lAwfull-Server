package lawscraper.client.ui.loginrequest;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/12/12
 * Time: 7:39 PM
 */
public class LoginRequest {
    public LoginRequest(RequestCallback callback, String userName, String password) {
        String url = "/authenticate.do";

        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, url);
        rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
        rb.setRequestData("j_username=" + URL.encode(userName) +
                                  "&j_password=" + URL.encode(password));

        rb.setCallback(callback);
        try {
            rb.send();
        } catch (RequestException e) {
            Window.alert(e.getMessage());
        }
    }
}
