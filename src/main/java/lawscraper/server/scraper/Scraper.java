package lawscraper.server.scraper;

import lawscraper.server.entities.law.*;
import lawscraper.server.push.PushServiceImpl;
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
    private Chapter currentChapter = null;
    private Paragraph currentParaGraph = null;
    private Section currentSection = null;
    private SectionListItem currentSectionListItem = null;
    private Divider currentDivider;
    private PushServiceImpl pushService;
    private String lawUrl;


    public Scraper() {
        pushService = new PushServiceImpl();
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

    public void parseXHTML(String url) throws SAXException, ParserConfigurationException, IOException {

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
        InputStream in = new URL(url).openStream();
        saxParser.parse(in, handler);
    }

    private void addData(String data) {
        if (currentChapter != null) {
            pushService.sendMessage("Adding data to: " + currentChapter.getHeadLine() + " \n");
        }
        String cdt = getCurrentDataType();
        if (cdt.equals("title")) {
            law.setTitle(data);
        } else if (cdt.equals("section") || cdt.equals("a")) {
            if (currentSection != null) {
                currentSection.setSectionText(currentSection.getSectionText() + data);
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
            currentSectionListItem.setListItemString(currentSectionListItem.getListItemString() + data);
        } else if (cdt.equals("chapterHeadLine")) {
            currentChapter.setHeadLine(data);
        } else if (cdt.equals("dividerHeadLine")) {
            currentDivider.setHeadline(data);
        } else if (cdt.equals("sectionDeprecated")) {
            currentSection.setSectionText(data);
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
        if (attributes.getValue(2).equals("rinfo:Stycke") && !attributes.getValue(0).startsWith("L")) {
            Section section = new Section();
            section.setSectionKey(attributes.getValue(1));
            section.setSectionNo(attributes.getValue(0));

            if (currentParaGraph.getSections() == null) {
                currentParaGraph.setSections(new ArrayList<Section>());
            }
            currentParaGraph.getSections().add(section);
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
            SectionListItem sectionListItem = new SectionListItem();
            sectionListItem.setListItemKey(attributes.getValue(1));
            sectionListItem.setListItemString(attributes.getValue(0));
            setCurrentDataType("sectionListItem");
            if (currentSection.getSectionListItems() == null) {
                currentSection.setSectionListItems(new ArrayList<SectionListItem>());
            }
            currentSection.getSectionListItems().add(sectionListItem);
            currentSectionListItem = sectionListItem;
        }
    }

    private void parseHElement(Law law, Attributes attributes) {
        if (attributes.getValue(0).equals("kapitelrubrik")) {
            setCurrentDataType("chapterHeadLine");
        } else if (attributes.getValue(0).equals("avdelningsrubrik")) {
            setCurrentDataType("dividerHeadLine");
        } else {
            setCurrentDataType("h");
        }
    }

    private void parseSectionElement(Law law, Attributes attributes) {
        if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Kapitel")) {
            Chapter chapter = new Chapter();
            chapter.setNumber(attributes.getValue(4));
            chapter.setKey(attributes.getValue(2));
            if (currentDivider != null) {
                if (currentDivider.getChapters() == null) {
                    currentDivider.setChapters(new ArrayList<Chapter>());
                }
                currentDivider.getChapters().add(chapter);
            } else {
                if (law.getChapters() == null) {
                    law.setChapters(new ArrayList<Chapter>());
                }
                law.getChapters().add(chapter);
            }
            currentChapter = chapter;
            setCurrentDataType("chapter");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Paragraf")) {
            Paragraph paragraph = new Paragraph();
            if (attributes.getValue(4) != null) {
                paragraph.setParagraphNo(attributes.getValue(4));
            }
            if (attributes.getValue(2) != null) {
                paragraph.setKey(attributes.getValue(2));
            }
            if (currentChapter.getParagraphs() == null) {
                currentChapter.setParagraphs(new ArrayList<Paragraph>());
            }
            currentChapter.getParagraphs().add(paragraph);
            currentParaGraph = paragraph;
            setCurrentDataType("paragraph");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("rinfo:Avdelning")) {
            currentDivider = new Divider();
            currentDivider.setKey(attributes.getValue(1));
            if (law.getDividers() == null) {
                law.setDividers(new ArrayList<Divider>());
            }
            law.getDividers().add(currentDivider);
            setCurrentDataType("divider");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("upphavd")) {
            Paragraph paragraph = new Paragraph();

            try {
                int paragraphNo = Integer.parseInt(currentParaGraph.getParagraphNo());
                paragraphNo++;
                paragraph.setParagraphNo(String.valueOf(paragraphNo));
            } catch (Exception e) {
                System.out.println("Could not set paragraph no");
            }
            paragraph.setDeprecated(true);
            /* todo: fix this
            if (attributes.getValue(2) != null) {
                paragraph.setKey(attributes.getValue(2));
            }
            */

            if (currentChapter.getParagraphs() == null) {
                currentChapter.setParagraphs(new ArrayList<Paragraph>());
            }
            currentChapter.getParagraphs().add(paragraph);
            currentParaGraph = paragraph;

            Section section = new Section();

            /* todo: fix this
            int sectionNo = Integer.parseInt(currentSection.getSectionNo())+1;
            section.setSectionNo(String.valueOf(sectionNo));
            section.setSectionKey("#"+sectionNo);
            */
            if (currentParaGraph.getSections() == null) {
                currentParaGraph.setSections(new ArrayList<Section>());
            }
            currentParaGraph.getSections().add(section);
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

