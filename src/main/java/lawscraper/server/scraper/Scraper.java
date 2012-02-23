package lawscraper.server.scraper;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
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
 * Date: 10/9/11
 * Time: 7:37 PM
 */

public class Scraper {

    private Law law = new Law();

    ArrayList<String> currentDataType = new ArrayList<String>();
    private Stack<LawDocumentPart> lawDocumentPartStack = new Stack<LawDocumentPart>();

    public Scraper() {
        this.lawDocumentPartStack.add(law);
    }

    public void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DefaultHandler handler = new
                DefaultHandler() {
                    String data = "";

                    public void startElement(String namespaceURI,
                                             String lname, String qname, Attributes attrs) {
                        parseElement(qname, attrs);
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
        LawDocumentPart currentDocumentPart = lawDocumentPartStack.peek();
        String cdt = getCurrentDataType();
        if (cdt.equals("title")) {
            law.setTitle(data);
        } else if (cdt.equals("section") || cdt.equals("a")) {
            if (currentDocumentPart != null) {
                currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);

                //the lagen.nu parser sometimes misses deprecated paragraphs. let's check that our selfs.
                if (currentDocumentPart.getTextElement().getText().contains("upphävd genom")) {
                    currentDocumentPart.setLawPartType(LawDocumentPartType.DEPRECATED);
                    currentDocumentPart.setDeprecated(true);

                    LawDocumentPart parent = currentDocumentPart.getParent();
                    if (parent != null && parent.getLawPartType().equals(LawDocumentPartType.PARAGRAPH)) {
                        parent.setDeprecated(true);
                        parent.setLawPartType(LawDocumentPartType.DEPRECATED);
                    }
                }
            }
        } else if (cdt.equals("sectionListItem")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("chapterHeadLine")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("dividerHeadLine")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("sectionDeprecated")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("headLine")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("subHeadLine")) {
            currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
        } else if (cdt.equals("dct:publisher")) {
            if (law.getPublisher() == null) {
                law.setPublisher(data);
            }
        } else if (cdt.equals("dct:creator")) {
            if (law.getCreator() == null) {
                law.setCreator(data);
            }
        }
    }

    private void setCurrentDataType(String currentDataType) {
        this.currentDataType.add(0, currentDataType);
    }

    private void parseElement(String qname, Attributes attributes) {
        if (qname.equals("meta")) {
            parseMetaElement(attributes);
        } else if (qname.equals("link")) {
            parseLinkElement(attributes);
        } else if (qname.equals("dd")) {
            parseDDElement(attributes);
        } else if (qname.equals("section")) {
            parseSectionElement(attributes);
        } else if (qname.equals("p")) {
            parsePElement(attributes);
        } else if (qname.equals("li")) {
            parseLiElement(attributes);
        } else if (qname.equals("h")) {
            parseHElement(attributes);
        } else {
            setCurrentDataType(qname);
        }
    }

    private void parsePElement(Attributes attributes) {
        if (attributes.getValue(2) != null &&
                attributes.getValue(2).equals("rinfo:Stycke") &&
                !attributes.getValue(0).startsWith("L")) {
            LawDocumentPart section = new LawDocumentPart();
            section.setKey(attributes.getValue(0));
            section.setLawPartType(LawDocumentPartType.SECTION);
            setCurrentLawDocumentPart(section);
            setCurrentDataType("section");
        } else {
            setCurrentDataType("p");
        }
    }

    private void parseDDElement(Attributes attributes) {
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

    private void parseLiElement(Attributes attributes) {
        setCurrentDataType("li");
        if (attributes.getValue(0) == null) {
            return;
        }

        //checks if value starts with P because we want the LI's that is connected to a paragraph
        if (attributes.getValue(0).startsWith("P") || attributes.getValue(0).startsWith("K")) {
            LawDocumentPart sectionListItem = new LawDocumentPart();
            sectionListItem.setKey(attributes.getValue(1));
            sectionListItem.addText(attributes.getValue(0));
            sectionListItem.setLawPartType(LawDocumentPartType.SECTION_LIST_ITEM);
            setCurrentDataType("sectionListItem");
            setCurrentLawDocumentPart(sectionListItem);
        }
    }

    private void parseHElement(Attributes attributes) {

        if (attributes.getValue(0) != null && attributes.getValue(0).equals("kapitelrubrik")) {
            setCurrentDataType("chapterHeadLine");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("avdelningsrubrik")) {
            setCurrentDataType("dividerHeadLine");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("underrubrik")) {
            LawDocumentPart subHeadLine = new LawDocumentPart();
            subHeadLine.setLawPartType(LawDocumentPartType.SUB_HEADING);

            setCurrentLawDocumentPart(subHeadLine);
            setCurrentDataType("subHeadLine");

        } else if (attributes.getValue(0) != null && attributes.getValue(0).contains("R")) {
            LawDocumentPart headLine = new LawDocumentPart();
            headLine.setLawPartType(LawDocumentPartType.HEADING);
            setCurrentLawDocumentPart(headLine);
            setCurrentDataType("headLine");

        } else {
            setCurrentDataType("h");
        }
    }


    private void parseSectionElement(Attributes attributes) {
        if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Kapitel")) {
            LawDocumentPart chapter = new LawDocumentPart();
            chapter.setKey(attributes.getValue(2));
            chapter.setLawPartType(LawDocumentPartType.CHAPTER);
            setCurrentLawDocumentPart(chapter);
            setCurrentDataType("chapter");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Paragraf")) {
            LawDocumentPart paragraph = new LawDocumentPart();
            if (attributes.getValue(4) != null) {
                paragraph.addText(attributes.getValue(4));
            }
            if (attributes.getValue(2) != null) {
                paragraph.setKey(attributes.getValue(2));
            }
            paragraph.setLawPartType(LawDocumentPartType.PARAGRAPH);
            setCurrentLawDocumentPart(paragraph);
            setCurrentDataType("paragraph");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("rinfo:Avdelning")) {
            LawDocumentPart divider = new LawDocumentPart();
            divider.setKey(attributes.getValue(1));
            divider.setLawPartType(LawDocumentPartType.DIVIDER);
            setCurrentLawDocumentPart(divider);
            setCurrentDataType("divider");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("upphavd")) {
            LawDocumentPart deprecatedElement = new LawDocumentPart();
            deprecatedElement.setLawPartType(LawDocumentPartType.DEPRECATED);
            deprecatedElement.setDeprecated(true);
            setCurrentLawDocumentPart(deprecatedElement);
            LawDocumentPart section = new LawDocumentPart();
            section.setLawPartType(LawDocumentPartType.DEPRECATED);
            section.setDeprecated(true);
            setCurrentLawDocumentPart(section);
            setCurrentDataType("sectionDeprecated");
        } else {
            setCurrentDataType("section");
        }
    }

    private void parseLinkElement(Attributes attributes) {
        setCurrentDataType("link");
        if (attributes.getValue(0) == null || attributes.getValue(0) == null) {
            return;
        }
        if (attributes.getValue(0).equals("rinfo:forarbete") && !law.getPropositions()
                                                                    .contains(attributes.getValue(1))) {
            law.getPropositions().add(attributes.getValue(1));
        }
    }

    private void parseMetaElement(Attributes attributes) {
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

    public Law getLaw() {
        return law;
    }

    private void setCurrentLawDocumentPart(LawDocumentPart lawDocumentPart) {
        addDocumentPart(lawDocumentPart);
        this.lawDocumentPartStack.add(lawDocumentPart);
    }

    private void addDocumentPart(LawDocumentPart documentPart) {
        LawDocumentPart allowedParent = getAllowedParent(documentPart);
        if (allowedParent == null) {
            System.out.println("Couldn't find an allowed parent");
        } else {
            allowedParent.addDocumentPartChild(documentPart);
        }
    }

    private LawDocumentPart getAllowedParent(LawDocumentPart documentPart) {
        LawDocumentPart stackTop = lawDocumentPartStack.peek();
        if (stackTop == null) {
            return null;
        }

        if (isAllowedParent(stackTop.getLawPartType(), documentPart.getLawPartType())) {
            return stackTop;
        }
        lawDocumentPartStack.pop();
        return getAllowedParent(documentPart);
    }

    private boolean isAllowedParent(LawDocumentPartType parentLawPartType, LawDocumentPartType lawDocumentPartType) {
        if (lawDocumentPartType == LawDocumentPartType.LAW || parentLawPartType == lawDocumentPartType) {
            return false;
        }

        switch (parentLawPartType) {
            case LAW:
            case DIVIDER:
                if (lawDocumentPartType != LawDocumentPartType.SECTION_LIST_ITEM &&
                        lawDocumentPartType != LawDocumentPartType.SUB_HEADING) {
                    return true;
                }
                break;
            case HEADING:
                if (lawDocumentPartType != LawDocumentPartType.SECTION_LIST_ITEM) {
                    return true;
                }
                break;
            case CHAPTER:
                if (lawDocumentPartType != LawDocumentPartType.SECTION_LIST_ITEM &&
                        lawDocumentPartType != LawDocumentPartType.DIVIDER &&
                        lawDocumentPartType != LawDocumentPartType.SUB_HEADING) {
                    return true;
                }
                break;
            case PARAGRAPH:
                if (lawDocumentPartType != LawDocumentPartType.SECTION_LIST_ITEM &&
                        lawDocumentPartType != LawDocumentPartType.DIVIDER &&
                        lawDocumentPartType != LawDocumentPartType.CHAPTER) {
                    return true;
                }
                break;
            case SECTION:
                if (lawDocumentPartType == LawDocumentPartType.SECTION_LIST_ITEM) {
                    return true;
                }
                break;
            case SECTION_LIST_ITEM:
                return false;
            case SUB_HEADING:
                if (lawDocumentPartType != LawDocumentPartType.SECTION_LIST_ITEM) {
                    return true;
                }
                break;
            case DEPRECATED:
                if (lawDocumentPartType == LawDocumentPartType.DEPRECATED) {
                    return true;
                }
                break;
        }
        return false;
    }

}

