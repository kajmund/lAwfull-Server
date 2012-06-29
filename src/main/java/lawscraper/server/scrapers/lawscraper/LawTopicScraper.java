package lawscraper.server.scrapers.lawscraper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Scrapes association between a law and law topics.
 */
public class LawTopicScraper {

    private static String DEFAULT_URL = "https://lagen.nu/";
    private ScrapedTopic currentTopic;
    private Set<ScrapedTopic> topics = new HashSet<ScrapedTopic>();

    private static final String URL_MATCH_PATTERN = "http://rinfo.lagrummet.se/publ/sfs/(.*)";

    public void scrapeLagenNu() throws ParserConfigurationException, SAXException, IOException {
        scrape(new URL(DEFAULT_URL).openStream());
    }

    public void scrape(InputStream inputStream) throws ParserConfigurationException, SAXException {
        DefaultHandler handler = new
                DefaultHandler() {
                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        if (lname.equals("a") && "lcwl".equals(attrs.getValue("class"))) {
                            String href = attrs.getValue("href");
                            currentTopic = new ScrapedTopic(href.substring(href.lastIndexOf("/")),
                                    attrs.getValue("title"));
                            System.out.println("Ämnesord: " + currentTopic.name);
                            topics.add(currentTopic);
                        } else if (lname.equals("a") && "lwl".equals(attrs.getValue("class"))
                                && currentTopic != null) {
                            String href = attrs.getValue(attrs.getValue("href"));
                            String lawName = href.substring(1);
                            currentTopic.addLaw(lawName);
                            System.out.println(" " + lawName);
                            topics.add(currentTopic);
                        }

                    }

                    public void endElement(String uri, String lname,
                                           String qname)
                            throws SAXException {
                    }
                };

        SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        try {
            saxParser.parse(inputStream, handler);
        } catch (Exception e) {
            System.out.println("Failed to read topics from input stream");
            e.printStackTrace();
        }
        System.out.println(topics.size() + " topics scraped");
    }

    public Set<ScrapedTopic> getTopics() {
        return topics;
    }

    private class ScrapedTopic {
        private final String name;
        private final String description;
        private final List<String> lawNames = new LinkedList<String>();

        public ScrapedTopic(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public void addLaw(String lawName) {
            lawNames.add(lawName);
        }
    }
}
