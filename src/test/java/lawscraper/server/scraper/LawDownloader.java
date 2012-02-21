package lawscraper.server.scraper;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Manual download of all laws. Used for generating test data.
 */
public class LawDownloader {

    public static void main(String[] args) {
        File lawDir = new File("laws");
        lawDir.mkdirs();
        LawUrlScraper urlScraper = new LawUrlScraper();
        try {
            urlScraper.fetchUrls();
            for (String lawName : urlScraper.getUrls()) {
                URL url = new URL("https://lagen.nu/" + lawName + ".xht2");
                File destination = new File(lawDir, lawName + ".xht2");
                if (!destination.exists()) {
                    try {
                        FileUtils.copyURLToFile(url, destination);
                        System.out.println("Wrote " + destination.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Failed to download " + url);
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(lawName + " already downloaded");
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
