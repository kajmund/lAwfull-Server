package lawscraper.server.scrapers.lawscraper;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.Utilities;
import lawscraper.shared.DocumentPartType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 10/9/11
 * Time: 7:37 PM
 */

public class LawScraper {

    private final Law law = new Law();
    private boolean endParsing;
    private final ArrayList<String> currentDataType = new ArrayList<String>();
    private final Stack<LawDocumentPart> lawDocumentPartStack = new Stack<LawDocumentPart>();
    private final Set<String> documentKeys = new HashSet<String>();
    private RepositoryBase<DocumentPart> documentPartRepository;
    private Set<String> docKeys = new HashSet<String>();

    public LawScraper(RepositoryBase<DocumentPart> documentPartRepository) {
        this.documentPartRepository = documentPartRepository;
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
                        if (endParsing) {
                            return;
                        }
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
        LawDocumentPart currentDocumentPart = getCurrentLawDocumentPart();
        String cdt = getCurrentDataType();
        if (cdt.equals("title")) {
            law.setTitle(data);
        } else if (cdt.equals("section") || cdt.equals("a")) {
            if (currentDocumentPart != null) {
                currentDocumentPart.addText(currentDocumentPart.getTextElement().getText() + data);
                //the lagen.nu parser sometimes misses deprecated paragraphs. let's check that our selfs.
                if (currentDocumentPart.getTextElement().getText().contains("upphävd genom")) {
                    currentDocumentPart.setLawPartType(DocumentPartType.PARAGRAPH_DEPRECATED);
                    currentDocumentPart.setDeprecated(true);
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
            if (currentDocumentPart.getType() == null) {
                // TODO Fler fall?
                if (currentDocumentPart.getTextElement().getText().contains("kap")) {
                    currentDocumentPart.setLawPartType(DocumentPartType.CHAPTER);
                    addDocumentPart(currentDocumentPart);
                } else {
                    currentDocumentPart.setLawPartType(DocumentPartType.PARAGRAPH);
                    addDocumentPart(currentDocumentPart);
                }
            }

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
            LawDocumentPart section = createPart(DocumentPartType.SECTION);
            setCurrentLawDocumentPart(section, attributes.getValue(0));
            setCurrentDataType("section");
        } else {
            setCurrentDataType("p");
        }
    }

    private void setDocumentKey(LawDocumentPart lawDocumentPart, String key) {
        String finalKey = key;

        if (lawDocumentPart.getParent() != null && lawDocumentPart.getParent().getKey() != null) {
            finalKey = lawDocumentPart.getParent().getKey() + "_" + key;

            if (documentKeys.contains(finalKey)) {
                boolean done = false;
                int version = 1;
                while (!done) {
                    version++;
                    if (!documentKeys.contains(finalKey + "_" + version)) {
                        finalKey += "_" + version;
                        done = true;
                    }
                }
            }
            documentKeys.add(finalKey);
        }
        lawDocumentPart.setKey(finalKey);

        getOrCreateDocumentPart(finalKey);
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
            LawDocumentPart sectionListItem = createPart(DocumentPartType.SECTION_LIST_ITEM);

            //sectionListItem.addText(attributes.getValue(0));
            setCurrentDataType("sectionListItem");
            setCurrentLawDocumentPart(sectionListItem, attributes.getValue(1));

        }
    }

    private void parseHElement(Attributes attributes) {

        if (attributes.getValue(0) != null && attributes.getValue(0).equals("kapitelrubrik")) {
            setCurrentDataType("chapterHeadLine");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("avdelningsrubrik")) {
            setCurrentDataType("dividerHeadLine");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("underrubrik")) {
            LawDocumentPart subHeadLine = createPart(DocumentPartType.SUB_HEADING);
            setCurrentLawDocumentPart(subHeadLine, attributes.getValue(0));
            setCurrentDataType("subHeadLine");

        } else if (attributes.getValue(0) != null && attributes.getValue(0).contains("R")) {
            LawDocumentPart headLine = createPart(DocumentPartType.HEADING);
            setCurrentLawDocumentPart(headLine, attributes.getValue(0));
            setCurrentDataType("headLine");

        } else {
            setCurrentDataType("h");
        }
    }

    private LawDocumentPart createPart(DocumentPartType type) {
        LawDocumentPart lawDocumentPart = new LawDocumentPart();
        if (type != null) {
            lawDocumentPart.setLawPartType(type);
        }
        lawDocumentPart.setBelongsToLaw(law);
        return lawDocumentPart;
    }


    private void parseSectionElement(Attributes attributes) {

        if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Kapitel")) {
            LawDocumentPart chapter = createPart(DocumentPartType.CHAPTER);

            setCurrentLawDocumentPart(chapter, attributes.getValue(2));
            setCurrentDataType("chapter");
        } else if (attributes.getValue(1) != null && attributes.getValue(1).equals("rinfo:Paragraf")) {
            LawDocumentPart paragraph = createPart(DocumentPartType.PARAGRAPH);
            if (attributes.getValue(4) != null) {
                paragraph.addText(attributes.getValue(4) + " &sect; ");
            }
            setCurrentLawDocumentPart(paragraph, attributes.getValue(0));
            setCurrentDataType("paragraph");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("rinfo:Avdelning")) {
            LawDocumentPart divider = createPart(DocumentPartType.DIVIDER);
            setCurrentLawDocumentPart(divider, attributes.getValue(0));
            setCurrentDataType("divider");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("upphavd")) {
            LawDocumentPart deprecatedElement = createPart(DocumentPartType.SECTION);
            deprecatedElement.setDeprecated(true);
            this.lawDocumentPartStack.add(deprecatedElement);
            setCurrentDataType("sectionDeprecated");
        } else if (attributes.getValue(0) != null && attributes.getValue(0).equals("secondary")) {
            //stop parsing when we hit the meta-data in the end
            setCurrentDataType("secondary");
            endParsing = true;
        } else {
            setCurrentDataType("section");
        }
    }

    private void parseLinkElement(Attributes attributes) {
        setCurrentDataType("link");
        if (attributes.getValue(0) == null || attributes.getValue(0) == null) {
            return;
        }
        if (attributes.getValue(0).equals("rinfo:forarbete") && !law.getDocumentReferenceList()
                                                                    .contains(attributes.getValue(1))) {

            String propName = attributes.getValue(1);
            law.addDocumentReference(
                    new DocumentPart(propName, propName.replace(" ", "_"), DocumentPartType.PROPOSITION));

        }
    }

    private void parseMetaElement(Attributes attributes) {
        setCurrentDataType("meta");
        if (attributes.getValue(0) == null || attributes.getValue(1) == null) {
            return;
        }
        if (attributes.getValue(0).equals("rinfo:fsNummer")) {
            setDocumentKey(law, attributes.getValue(1));
        } else if (attributes.getValue(0).equals("rinfoex:senastHamtad")) {
            law.setLatestFetchFromGov(attributes.getValue(1));
        } else if (attributes.getValue(0).equals("rinfo:utfardandedatum")) {
            law.setReleaseDate(attributes.getValue(1));
        }
    }

    private String getCurrentDataType() {
        if (!currentDataType.isEmpty()) {
            return currentDataType.get(0);
        }
        return "";
    }

    public Law getLaw() {
        return law;
    }

    private void setCurrentLawDocumentPart(LawDocumentPart lawDocumentPart, String key) {
        addDocumentPart(lawDocumentPart);

        this.lawDocumentPartStack.add(lawDocumentPart);
        setDocumentKey(lawDocumentPart, key);
    }

    private LawDocumentPart getCurrentLawDocumentPart() {
        return lawDocumentPartStack.peek();
    }

    private DocumentPart getOrCreateDocumentPart(String documentId) {
        /** redundant shitty code */
        if (docKeys.contains(documentId)) {
            return null;
        } else {
            docKeys.add(documentId);
        }

        DocumentPart documentPart = documentPartRepository.findOne(documentId);
        if (documentPart == null) {
            documentPart = new DocumentPart();
            documentPart.setKey(documentId);
        } else {
            System.out.println("Reference already persisted");
        }

        return documentPart;
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
        LawDocumentPart stackTop = getCurrentLawDocumentPart();
        if (stackTop == null) {
            return null;
        }

        if (isAllowedParent(stackTop.getLawPartType(), documentPart.getLawPartType())) {
            return stackTop;
        }
        lawDocumentPartStack.pop();
        return getAllowedParent(documentPart);
    }

    private boolean isAllowedParent(DocumentPartType parentPartType, DocumentPartType documentPartType) {
        if (documentPartType == DocumentPartType.LAW || parentPartType == documentPartType) {
            return false;
        }

        switch (parentPartType) {
            case LAW:
            case DIVIDER:
                if (documentPartType != DocumentPartType.SECTION_LIST_ITEM &&
                        documentPartType != DocumentPartType.SUB_HEADING) {
                    return true;
                }
                break;
            case HEADING:
                if (documentPartType != DocumentPartType.DIVIDER &&
                        documentPartType != DocumentPartType.SECTION_LIST_ITEM &&
                        documentPartType != DocumentPartType.CHAPTER) {
                    return true;
                }
                break;
            case CHAPTER:
                if (documentPartType != DocumentPartType.SECTION_LIST_ITEM &&
                        documentPartType != DocumentPartType.DIVIDER &&
                        documentPartType != DocumentPartType.SUB_HEADING) {
                    return true;
                }
                break;
            case PARAGRAPH:
                if (documentPartType != DocumentPartType.SECTION_LIST_ITEM &&
                        documentPartType != DocumentPartType.DIVIDER &&
                        documentPartType != DocumentPartType.CHAPTER &&
                        documentPartType != DocumentPartType.HEADING &&
                        documentPartType != DocumentPartType.SUB_HEADING) {
                    return true;
                }
                break;
            case SECTION:
                if (documentPartType == DocumentPartType.SECTION_LIST_ITEM) {
                    return true;
                }
                break;
            case SECTION_LIST_ITEM:
                return false;
            case SUB_HEADING:
                if (documentPartType != DocumentPartType.DIVIDER &&
                        documentPartType != DocumentPartType.SECTION_LIST_ITEM &&
                        documentPartType != DocumentPartType.HEADING &&
                        documentPartType != DocumentPartType.CHAPTER) {
                    return true;
                }
                break;
            case PARAGRAPH_DEPRECATED:
                if (documentPartType == DocumentPartType.PARAGRAPH_DEPRECATED) {
                    return true;
                }
                break;
        }
        return false;
    }

}

