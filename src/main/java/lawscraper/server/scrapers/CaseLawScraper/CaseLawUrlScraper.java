package lawscraper.server.scrapers.caselawscraper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/20/12
 * Time: 11:47 PM
 */
public class CaseLawUrlScraper {


    private boolean insideLi;
    private List<String> urls = new ArrayList<String>();
    private static final String BASE_URL = "https://lagen.nu/dom/index/";
    private static List<String> COURTS = Arrays.asList("nja", "mod", "mig", "ad", "rk", "md", "ra", "hfd", "rh");
    private static int START_YEAR = 1981;
    private static int END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private String URL_MATCH_PATTERN = "http://rinfo.lagrummet.se/publ/rattsfall/*/(.*)";
    private List<String> URLS_TO_SCRAPE = new ArrayList<String>();

    public CaseLawUrlScraper() {
        constructUrlsToScrape();
    }

    private void constructUrlsToScrape() {
        String url;
        for (String court : COURTS) {
            int year;
            for (year = START_YEAR; year <= END_YEAR; year++) {
                url = BASE_URL + court + "-" + year + ".xht2";
                URLS_TO_SCRAPE.add(url);
                System.out.println(url);
            }
        }
    }

    public void fetchUrls() throws ParserConfigurationException, SAXException {
        DefaultHandler handler = new
                DefaultHandler() {
                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        if (lname.equals("li")) {
                            insideLi = true;
                        } else if (insideLi) {
                            if (lname.equals("a") && attrs.getValue(0) != null) {
                                Matcher matcher = Pattern.compile(URL_MATCH_PATTERN).matcher(attrs.getValue(0));
                                while (matcher.find()) {
                                    urls.add(matcher.group(1));
                                }
                            }
                        }
                    }

                    public void endElement(String uri, String lname,
                                           String qname)
                            throws SAXException {
                        if (lname.equals("li")) {
                            insideLi = false;
                        }
                    }
                };

        SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        for (String s : URLS_TO_SCRAPE) {
            try {
                InputStream in = new URL(s).openStream();
                saxParser.parse(in, handler);
            } catch (Exception e) {
                System.out.println(s + " not found");
                continue;
            }
        }
        System.out.println(urls.size() + " caselawurls scraped");
    }

    public List<String> getUrls() {
        return urls;
    }

}
