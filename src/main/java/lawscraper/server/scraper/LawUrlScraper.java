package lawscraper.server.scraper;

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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/20/12
 * Time: 11:47 PM
 */
public class LawUrlScraper {

    private boolean insideLi;
    private List<String> urls = new ArrayList<String>();
    private static List<String> URLS_TO_SCRAPE = Arrays.asList(
            "https://lagen.nu/index/a.xht2",
            "https://lagen.nu/index/b.xht2",
            "https://lagen.nu/index/c.xht2",
            "https://lagen.nu/index/d.xht2",
            "https://lagen.nu/index/e.xht2",
            "https://lagen.nu/index/f.xht2",
            "https://lagen.nu/index/g.xht2",
            "https://lagen.nu/index/h.xht2",
            "https://lagen.nu/index/i.xht2",
            "https://lagen.nu/index/j.xht2",
            "https://lagen.nu/index/k.xht2",
            "https://lagen.nu/index/l.xht2",
            "https://lagen.nu/index/m.xht2",
            "https://lagen.nu/index/n.xht2",
            "https://lagen.nu/index/o.xht2",
            "https://lagen.nu/index/p.xht2",
            "https://lagen.nu/index/q.xht2",
            "https://lagen.nu/index/r.xht2",
            "https://lagen.nu/index/s.xht2",
            "https://lagen.nu/index/t.xht2",
            "https://lagen.nu/index/u.xht2",
            "https://lagen.nu/index/v.xht2",
            "https://lagen.nu/index/x.xht2",
            "https://lagen.nu/index/y.xht2",
            "https://lagen.nu/index/z.xht2",
            "https://lagen.nu/index/%E5.xht2",
            "https://lagen.nu/index/%E4.xht2",
            "https://lagen.nu/index/%F6.xht2");

    private static final String URL_MATCH_PATTERN = "http://rinfo.lagrummet.se/publ/sfs/(.*)";

    public void fetchUrls() throws ParserConfigurationException, SAXException {
        DefaultHandler handler = new
                DefaultHandler() {
                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        if (lname.equals("li")) {
                            insideLi = true;
                        } else if (insideLi) {
                            if (insideLi) {
                                if (lname.equals("a") && attrs.getValue(0) != null) {
                                    Matcher matcher = Pattern.compile(URL_MATCH_PATTERN).matcher(attrs.getValue(0));
                                    while (matcher.find()) {
                                        urls.add(matcher.group(1));
                                    }
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
        System.out.println(urls.size() + " lawurls scraped");
    }

    public List<String> getUrls() {
        return urls;
    }

}
