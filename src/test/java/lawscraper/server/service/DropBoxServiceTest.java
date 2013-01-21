package lawscraper.server.service;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.swing.*;
import java.io.ByteArrayInputStream;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/13/13
 * Time: 9:27 AM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/dataSource.xml",
                                   "classpath:/applicationContext-security.xml"})
@TransactionConfiguration(defaultRollback = true)
public class DropBoxServiceTest {

    private static final String APP_KEY = "28g56faiev19z6f";
    private static final String APP_SECRET = "aadzzshqjehs3fo";
    private static final Session.AccessType ACCESS_TYPE = Session.AccessType.APP_FOLDER;
    private static DropboxAPI mDBApi;

    @Test
    public void testDropBox() {
        /**
         * A very basic dropbox example.
         * @author mjrb5
         */

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthSession.WebAuthInfo authInfo = null;
        try {
            authInfo = session.getAuthInfo();
        } catch (DropboxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        RequestTokenPair pair = authInfo.requestTokenPair;
        String url = authInfo.url;

        System.out.println("Browse to this URL and authenticate: " + url);

        JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        try {
            session.retrieveWebAccessToken(pair);
        } catch (DropboxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        AccessTokenPair tokens = session.getAccessTokenPair();
        System.out.println("Use this token pair in future so you don't have to re-authenticate each time:");
        System.out.println("Key token: " + tokens.key);
        System.out.println("Secret token: " + tokens.secret);

        mDBApi = new DropboxAPI<WebAuthSession>(session);

        System.out.println();
        System.out.print("Uploading file...");
        String fileContents = "Hello World!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
        DropboxAPI.Entry newEntry = null;
        try {
            newEntry = mDBApi.putFile("/testing.txt", inputStream, fileContents.length(), null, null);
        } catch (DropboxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Done. \nRevision of file: " + newEntry.rev);

    }
}

