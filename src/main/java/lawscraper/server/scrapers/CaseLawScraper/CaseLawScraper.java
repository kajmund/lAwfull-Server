package lawscraper.server.scrapers.caselawscraper;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.caselaw.CaseLawDocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.scrapers.Utilities;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/10/12
 * Time: 6:26 PM
 */
public class CaseLawScraper {
    private ArrayList<String> currentDataType = new ArrayList<String>();
    private Stack<CaseLawDocumentPart> caseLawDocumentPartStack = new Stack<CaseLawDocumentPart>();
    private CaseLaw caseLaw = new CaseLaw();

    static final String ARSUTGAVA = "rinfo:arsutgava";
    static final String PUBLISHER = "dct:publisher";
    static final String CREATOR = "dct:creator";
    static final String RELATION = "dct:relation";
    static final String DOMSNUMMER = "rinfo:domsnummer";
    static final String SUBJECT = "dct:subject";
    static final String RATTSFALLSPUBLIKATION = "rinfo:rattsfallspublikation";
    static final String MALNUMMER = "rinfo:malnummer";
    static final String AVGORANDEDATUM = "rinfo:avgorandedatum";
    static final String DESCRIPTION = "dct:description";
    static final String IDENTIFIER = "dct:identifier";
    static final String LAGRUM = "rinfo:lagrum";
    static final String SIDNUMMER = "rinfo:sidnummer";


    public void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DefaultHandler handler = new
                DefaultHandler() {
                    String data = "";

                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        parseElement(qname, attrs);
                    }

                    public void characters(char[] ch, int start, int length) throws SAXException {
                        data = Utilities.trimText(new String(ch, start, length));
                        if (data.length() > 0) {
                            /* Dont't trim the a's, they need their space :-)
                        * */

                            if (getCurrentDataType().equals("a")) {
                                data = " " + data.trim() + " ";
                            }
                            addData(data);
                        }
                    }

                    public void endElement(String uri, String localName,
                                           String qName)
                            throws SAXException {
                        if (!currentDataType.isEmpty()) {
                            currentDataType.remove(0);
                        } else {
                            System.out.println("cant remove " + qName);
                        }
                    }
                };

        SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(in, handler);

    }

    private void addData(String data) {
        String cdt = getCurrentDataType();
        if (cdt.equals(ARSUTGAVA)) {
            caseLaw.setPublicationYear(data);
        } else if (cdt.equals(PUBLISHER)) {
            caseLaw.setPublisher(data);
        } else if (cdt.equals(CREATOR)) {
            caseLaw.setCreator(data);
        } else if (cdt.equals(RELATION)) {
            caseLaw.setRelation(data);
        } else if (cdt.equals(DOMSNUMMER)) {
            caseLaw.setCaseIdentifier(data);
        } else if (cdt.equals(SUBJECT)) {
            caseLaw.addSubject(data);
        } else if (cdt.equals(RATTSFALLSPUBLIKATION)) {
            caseLaw.setCasePublication(data);
        } else if (cdt.equals(MALNUMMER)) {
            caseLaw.setCaseNumber(data);
        } else if (cdt.equals(AVGORANDEDATUM)) {
            caseLaw.setDecisionDate(data);
        } else if (cdt.equals(DESCRIPTION)) {
            caseLaw.setDescription(data);
        } else if (cdt.equals(IDENTIFIER)) {
            caseLaw.setKey(data.replace(" ", "_"));
        } else if (cdt.equals(LAGRUM)) {
            caseLaw.addLawReference(data);
        } else if (cdt.equals(SIDNUMMER)) {
            caseLaw.setPageNumber(data);
        } else if (cdt.equals("p")) {
            CaseLawDocumentPart caseLawDocumentPart = caseLawDocumentPartStack.peek();
            caseLawDocumentPart.setTextElement(new TextElement(caseLawDocumentPart.getTextElement().getText() + data));
        }
    }

    private void setCurrentDataType(String currentDataType) {
        this.currentDataType.add(0, currentDataType);
    }

    public String getCurrentDataType() {
        if (!currentDataType.isEmpty()) {
            return currentDataType.get(0);
        }
        return "";
    }

    private void parseElement(String qname, Attributes attributes) {
        if (qname.equals("dd")) {
            parseDDElement(attributes);
        } else if (qname.equals("p")) {
            parsePElement(attributes);
        } else {
            setCurrentDataType(qname);
        }
    }

    private void parsePElement(Attributes attributes) {
        CaseLawDocumentPart caseLawDocumentPart = createCaseLawDocumentPart();
        caseLawDocumentPartStack.add(caseLawDocumentPart);
        caseLaw.addCaseLawDocumentPart(caseLawDocumentPart);
        setCurrentDataType("p");
    }

    private CaseLawDocumentPart createCaseLawDocumentPart() {
        CaseLawDocumentPart caseLawDocumentPart = new CaseLawDocumentPart();
        caseLawDocumentPart.setBelongsToCaseLaw(caseLaw);
        return caseLawDocumentPart;
    }

    public CaseLaw getCaseLaw() {
        return caseLaw;
    }

    private void parseDDElement(Attributes attributes) {
        if (attributes.getValue(0) == null) {
            setCurrentDataType("dd");
            return;
        }

        if (attributes.getValue(0).equals(ARSUTGAVA)) {
            setCurrentDataType(ARSUTGAVA);
        } else if (attributes.getValue(0).equals(PUBLISHER)) {
            setCurrentDataType(PUBLISHER);
        } else if (attributes.getValue(0).equals(CREATOR)) {
            setCurrentDataType(CREATOR);
        } else if (attributes.getValue(0).equals(RELATION)) {
            setCurrentDataType(RELATION);
        } else if (attributes.getValue(0).equals(DOMSNUMMER)) {
            setCurrentDataType(DOMSNUMMER);
        } else if (attributes.getValue(0).equals(SUBJECT)) {
            setCurrentDataType(SUBJECT);
        } else if (attributes.getValue(0).equals(RATTSFALLSPUBLIKATION)) {
            setCurrentDataType(RATTSFALLSPUBLIKATION);
        } else if (attributes.getValue(0).equals(MALNUMMER)) {
            setCurrentDataType(MALNUMMER);
        } else if (attributes.getValue(0).equals(AVGORANDEDATUM)) {
            setCurrentDataType(AVGORANDEDATUM);
        } else if (attributes.getValue(0).equals(DESCRIPTION)) {
            setCurrentDataType(DESCRIPTION);
        } else if (attributes.getValue(0).equals(IDENTIFIER)) {
            setCurrentDataType(IDENTIFIER);
        } else if (attributes.getValue(0).equals(LAGRUM)) {
            setCurrentDataType(LAGRUM);
        } else if (attributes.getValue(0).equals(SIDNUMMER)) {
            setCurrentDataType(SIDNUMMER);
        } else {
            setCurrentDataType("dd");
        }
    }

}
