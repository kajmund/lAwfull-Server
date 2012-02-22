package lawscraper.server.scraper;

import lawscraper.server.entities.law.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/9/11
 * Time: 7:37 PM
 */

public class Scraper {

    private Law law = new Law();

    ArrayList<String> currentDataType = new ArrayList<String>();
    private LawDocumentPart currentChapter = null;
    private LawDocumentPart currentParaGraph = null;
    private LawDocumentPart currentSection = null;
    private LawDocumentPart currentSectionListItem = null;
    private LawDocumentPart currentDivider = null;
    private String lawUrl;


    public Scraper() {
    }

    public Law parseLaw(String lawUrl) {
        this.lawUrl = lawUrl;

        try {
            parseXHTML(lawUrl);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return law;

    }

    public Law parseLaw(InputStream lawData) {

        try {
            parse(lawData);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of c atch statement use File | Settings | File Templates.
        }

        return law;

    }

    public void parseXHTML(String url) throws SAXException, ParserConfigurationException, IOException {
        InputStream in = new URL(url).openStream();
        parse(in);
    }

    public void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DefaultHandler handler = new
                DefaultHandler() {
                    String data = "";

                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        parseElement(law, qname, attrs);
                    }

                    public void characters(char[] ch, int start, int length) throws SAXException {
                        data = new String(ch, start, length);
                        /* Dont't trim the a's, they need their space :-)
                        * */
                        data = Utilities.trimText(data);
                        if (data.length() > 0) {
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
        if (cdt.equals("title")) {
            law.setTitle(data);
        } else if (cdt.equals("section") || cdt.equals("a")) {
            if (currentSection != null) {
                currentSection.addText(currentSection.getTextElement().getText() + data);

                //the lagen.nu parser sometimes misses deprecated paragraphs. let's check that our selfs.
                if (currentSection.getTextElement().getText().contains("upphävd genom")) {
                    currentSection.setType("deprecated");
                    currentSection.setDeprecated(true);

                    LawDocumentPart parent = currentSection.getParent();
                    if (parent != null && parent.getType().equals("paragraph")) {
                        parent.setDeprecated(true);
                        parent.setType("deprecated");
                    }
                }
            }
        } else if (cdt.equals("dct:publisher")) {
            if (law.getPublisher() == null) {
                law.setPublisher(data);
            }
        } else if (cdt.equals("dct:creator")) {
            if (law.getCreator() == null) {
                law.setCreator(data);
            }
        } else if (cdt.equals("sectionListItem")) {
            currentSectionListItem.addText(currentSectionListItem.getTextElement().getText() + data);
        } else if (cdt.equals("chapterHeadLine")) {
            currentChapter.addText(currentChapter.getTextElement().getText() + data);
        } else if (cdt.equals("dividerHeadLine")) {
            currentDivider.addText(currentDivider.getTextElement().getText() + data);
        } else if (cdt.equals("sectionDeprecated")) {
            currentSection.addText(currentSection.getTextElement().getText() + data);
        }

    }

    private void setCurrentDataType(String currentDataType) {
        this.currentDataType.add(0, currentDataType);
    }

    private void parseElement(Law law, String qname, Attributes attributes) {
        if (qname.equals("meta")) {
            parseMetaElement(law, attributes);
        } else if (qname.equals("link")) {
            parseLinkElement(law, attributes);
        } else if (qname.equals("dd")) {
            parseDDElement(law, attributes);
        } else if (qname.equals("section")) {
            parseSectionElement(law, attributes);
        } else if (qname.equals("p")) {
            parsePElement(law, attributes);
        } else if (qname.equals("li")) {
            parseLiElement(law, attributes);
        } else if (qname.equals("h")) {
            parseHElement(law, attributes);
        } else {
            setCurrentDataType(qname);
        }
    }

    private void parsePElement(Law law, Attributes attributes) {
        if (attributes.getValue(2) != null &&
                attributes.getValue(2).equals("rinfo:Stycke") &&
                !attributes.getValue(0).startsWith("L")) {
            LawDocumentPart section = new LawDocumentPart();
            section.setKey(attributes.getValue(0));
            section.setType("section");
            if (currentParaGraph != null) {
                currentParaGraph.addDocumentPartChild(section);
            } else {
                law.addDocumentPartChild(section);
            }

            currentSection = section;
            setCurrentDataType("section");
        } else {
            setCurrentDataType("p");
        }
    }

    private void parseDDElement(Law law, Attributes attributes) {
        if (attributes.getValue(0) == null) {
            setCurrentDataType("dd");
            return;
        }
        if (attributes.getValue(0).equals("dct:publisher")) {
            setCurrentDataType("dct:publisher");
        } else if (attributes.getValue(0).equals("dct:creator")) {
            setCurrentDataType("dct:creator");
        } else if (attributes.getValue(0).equals("dct:title")) {
            setCurrentDataType("dct:title");
        } else {
            setCurrentDataType("dd");
        }
    }

    private void parseLiElement(Law law, Attributes attributes) {
        setCurrentDataType("li");
        if (attributes.getValue(0) == null) {
            return;
        }

        //checks if value starts with P because we want the LI's that is connected to a paragraph
        if (attributes.getValue(0).startsWith("P") || attributes.getValue(0).startsWith("K")) {
            LawDocumentPart sectionListItem = new LawDocumentPart();
            sectionListItem.setKey(attributes.getValue(1));
            sectionListItem.addText(attributes.getValue(0));
            sectionListItem.setType("sectionListItem");
            setCurrentDataType("sectionListItem");
            currentSection.addDocumentPartChild(sectionListItem);
            currentSectionListItem = sectionListItem;
        }
    }

    private void parseHElement(Law law, Attributes attributes) {

        if (attributes.getValue(0) != null && attributes.getValue(0).equals("kapitelrubrik")) {
            setCurrentDataType("chapterHeadLine");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("avdelningsrubrik")) {
            setCurrentDataType("dividerHeadLine");
        } else {
            setCurrentDataType("h");
        }
    }

    private void parseSectionElement(Law law, Attributes attributes) {
        if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Kapitel")) {
            LawDocumentPart chapter = new LawDocumentPart();
            chapter.setKey(attributes.getValue(2));
            if (currentDivider != null) {
                currentDivider.addDocumentPartChild(chapter);
            } else {
                law.addDocumentPartChild(chapter);
            }

            currentChapter = chapter;
            chapter.setType("chapter");
            setCurrentDataType("chapter");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Paragraf")) {
            LawDocumentPart paragraph = new LawDocumentPart();
            if (attributes.getValue(4) != null) {
                paragraph.addText(attributes.getValue(4));
            }
            if (attributes.getValue(2) != null) {
                paragraph.setKey(attributes.getValue(2));
            }
            if (currentChapter != null) {
                currentChapter.addDocumentPartChild(paragraph);
            } else {
                law.addDocumentPartChild(paragraph);
            }

            currentParaGraph = paragraph;
            paragraph.setType("paragraph");
            setCurrentDataType("paragraph");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("rinfo:Avdelning")) {
            currentDivider = new LawDocumentPart();

            currentDivider.setKey(attributes.getValue(1));
            law.addDocumentPartChild(currentDivider);
            currentDivider.setType("divider");
            setCurrentDataType("divider");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("upphavd")) {
            LawDocumentPart deprecatedElement = new LawDocumentPart();
            deprecatedElement.setType("deprecated");
            deprecatedElement.setDeprecated(true);

            if (currentChapter != null) {
                currentChapter.addDocumentPartChild(deprecatedElement);
            } else {
                law.addDocumentPartChild(deprecatedElement);
            }

            currentParaGraph = deprecatedElement;

            LawDocumentPart section = new LawDocumentPart();
            section.setType("deprecated");
            section.setDeprecated(true);
            currentParaGraph.addDocumentPartChild(section);
            currentSection = section;

            setCurrentDataType("sectionDeprecated");
        } else {
            setCurrentDataType("section");
        }
    }

    private void parseLinkElement(Law law, Attributes attributes) {
        setCurrentDataType("link");
        if (attributes.getValue(0) == null || attributes.getValue(0) == null) {
            return;
        }
        if (attributes.getValue(0).equals("rinfo:forarbete") && !law.getPropositions()
                                                                    .contains(attributes.getValue(1))) {
            law.getPropositions().add(attributes.getValue(1));
        }
    }

    private void parseMetaElement(Law law, Attributes attributes) {
        setCurrentDataType("meta");
        if (attributes.getValue(0) == null || attributes.getValue(1) == null) {
            return;
        }
        if (attributes.getValue(0).equals("rinfo:fsNummer")) {
            law.setFsNumber(attributes.getValue(1));
        } else if (attributes.getValue(0).equals("rinfoex:senastHamtad")) {
            law.setLatestFetchFromGov(attributes.getValue(1));
        } else if (attributes.getValue(0).equals("rinfo:utfardandedatum")) {
            law.setReleaseDate(attributes.getValue(1));
        }
    }

    public String getCurrentDataType() {
        if (!currentDataType.isEmpty()) {
            return currentDataType.get(0);
        }
        return "";
    }
}

