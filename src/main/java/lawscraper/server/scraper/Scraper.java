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
    private LawDocumentPart currentSubHeadline = null;
    private LawDocumentPart currentHeadLine = null;

    public Scraper() {
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
        String cdt = getCurrentDataType();
        if (cdt.equals("title")) {
            law.setTitle(data);
        } else if (cdt.equals("section") || cdt.equals("a")) {
            if (currentSection != null) {
                currentSection.addText(currentSection.getTextElement().getText() + data);

                //the lagen.nu parser sometimes misses deprecated paragraphs. let's check that our selfs.
                if (currentSection.getTextElement().getText().contains("upphävd genom")) {
                    currentSection.setLawPartType(LawDocumentPartType.DEPRECATED);
                    currentSection.setDeprecated(true);

                    LawDocumentPart parent = currentSection.getParent();
                    if (parent != null && parent.getLawPartType().equals(LawDocumentPartType.PARAGRAPH)) {
                        parent.setDeprecated(true);
                        parent.setLawPartType(LawDocumentPartType.DEPRECATED);
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
        } else if (cdt.equals("headLine")) {
            currentHeadLine.addText(currentHeadLine.getTextElement().getText() + data);
        } else if (cdt.equals("subHeadLine")) {
            currentSubHeadline.addText(currentSubHeadline.getTextElement().getText() + data);
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
            if (currentParaGraph != null) {
                currentParaGraph.addDocumentPartChild(section);
            } else {
                law.addDocumentPartChild(section);
            }

            setCurrentSection(section);
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
            currentSection.addDocumentPartChild(sectionListItem);
            setCurrentSectionListItem(sectionListItem);
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
            if (Utilities.getParentLawDocumentPartTypeByKey(attributes.getValue(0)) == LawDocumentPartType.CHAPTER) {
                currentChapter.addDocumentPartChild(subHeadLine);
            } else if (Utilities.getParentLawDocumentPartTypeByKey(attributes.getValue(0)) == LawDocumentPartType.LAW) {
                law.addDocumentPartChild(subHeadLine);
            }
            setCurrentSubHeadLine(subHeadLine);
            setCurrentDataType("subHeadLine");

        } else if (attributes.getValue(0) != null && attributes.getValue(0).contains("R")) {
            LawDocumentPart headLine = new LawDocumentPart();
            headLine.setLawPartType(LawDocumentPartType.HEADING);
            if (Utilities.getParentLawDocumentPartTypeByKey(attributes.getValue(0)) == LawDocumentPartType.CHAPTER) {
                currentChapter.addDocumentPartChild(headLine);
            } else if (Utilities.getParentLawDocumentPartTypeByKey(attributes.getValue(0)) == LawDocumentPartType.LAW) {
                law.addDocumentPartChild(headLine);
            }
            setCurrentHeadLine(headLine);
            setCurrentDataType("headLine");

        } else {
            setCurrentDataType("h");
        }
    }


    private void parseSectionElement(Attributes attributes) {
        if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Kapitel")) {
            LawDocumentPart chapter = new LawDocumentPart();
            chapter.setKey(attributes.getValue(2));
            if (currentDivider != null) {
                currentDivider.addDocumentPartChild(chapter);
            } else {
                law.addDocumentPartChild(chapter);
            }

            setCurrentChapter(chapter);
            chapter.setLawPartType(LawDocumentPartType.CHAPTER);
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

            setCurrentParagraph(paragraph);
            paragraph.setLawPartType(LawDocumentPartType.PARAGRAPH);
            setCurrentDataType("paragraph");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("rinfo:Avdelning")) {
            LawDocumentPart divider = new LawDocumentPart();
            divider.setKey(attributes.getValue(1));
            law.addDocumentPartChild(divider);
            divider.setLawPartType(LawDocumentPartType.DIVIDER);
            setCurrentDivider(divider);
            setCurrentDataType("divider");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("upphavd")) {
            LawDocumentPart deprecatedElement = new LawDocumentPart();
            deprecatedElement.setLawPartType(LawDocumentPartType.DEPRECATED);
            deprecatedElement.setDeprecated(true);

            if (currentChapter != null) {
                currentChapter.addDocumentPartChild(deprecatedElement);
            } else {
                law.addDocumentPartChild(deprecatedElement);
            }

            currentParaGraph = deprecatedElement;

            LawDocumentPart section = new LawDocumentPart();
            section.setLawPartType(LawDocumentPartType.DEPRECATED);
            section.setDeprecated(true);
            currentParaGraph.addDocumentPartChild(section);
            setCurrentSection(section);

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

    private void setCurrentSubHeadLine(LawDocumentPart subHeadLine) {
        this.currentSubHeadline = subHeadLine;
    }

    private void setCurrentHeadLine(LawDocumentPart headLine) {
        this.currentHeadLine = headLine;
    }

    private void setCurrentParagraph(LawDocumentPart paragraph) {
        this.currentParaGraph = paragraph;
    }

    private void setCurrentChapter(LawDocumentPart chapter) {
        this.currentChapter = chapter;
    }

    private void setCurrentSection(LawDocumentPart section) {
        this.currentSection = section;
    }

    private void setCurrentSectionListItem(LawDocumentPart sectionListItem) {
        this.currentSectionListItem = sectionListItem;
    }

    private void setCurrentDivider(LawDocumentPart divider) {
        this.currentDivider = divider;
    }

}

