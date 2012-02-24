package lawscraper.server.service;

import lawscraper.server.components.LawRendererImpl;
import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.entities.superclasses.Document.TextElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO Document this.
 */
public class LawRendererImplTest {

    @Test
    public void testRenderToHtmlEmptyLaw() throws Exception {
        LawRendererImpl renderer = new LawRendererImpl();

        String html = renderer.renderToHtml(new Law());

        assertEquals("<div class=\"law\"><div class=\"lawMeta\">" +
                "<div class=\"lawTitle\"> ()</div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Departement</div><div class=\"metaText\"></div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Utfärdad</div><div class=\"metaText\"></div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Källa</div><div class=\"metaText\"></div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Senast hämtad</div><div class=\"metaText\"></div></div>" +
                "</div>" +
                "<div class=\"lawBody\"></div>" +
                "</div>"
                , html);
    }

    @Test
    public void testRenderToHtmlCompleteLaw() throws Exception {
        LawRendererImpl renderer = new LawRendererImpl();
        Law law = new Law();
        law.setTitle("Law");
        law.setFsNumber("FS");
        law.setCreator("Creator");
        law.setPublisher("Publisher");
        law.setReleaseDate("RelDate");
        law.setLatestFetchFromGov("FetchDate");

        LawDocumentPart sli = getPart("SectionListItem", LawDocumentPartType.SECTION_LIST_ITEM);
        LawDocumentPart s = getPart("Section", LawDocumentPartType.SECTION);
        LawDocumentPart p = getPart("Paragraph", LawDocumentPartType.PARAGRAPH);
        LawDocumentPart h = getPart("Heading", LawDocumentPartType.HEADING);
        LawDocumentPart h2 = getPart("Heading2", LawDocumentPartType.HEADING);
        LawDocumentPart c = getPart("Chapter", LawDocumentPartType.CHAPTER);
        LawDocumentPart d1 = getPart("Divider", LawDocumentPartType.DIVIDER);
        LawDocumentPart dep = getPart("Deprecated", LawDocumentPartType.DEPRECATED);

        d1.addDocumentPartChild(c);
        c.addDocumentPartChild(h);
        h.addDocumentPartChild(h2);
        h2.addDocumentPartChild(p);
        p.addDocumentPartChild(s);
        s.addDocumentPartChild(sli);
        law.addDocumentPartChild(d1);
        law.addDocumentPartChild(dep);

        String html = renderer.renderToHtml(law);

        assertEquals("<div class=\"law\"><div class=\"lawMeta\">" +
                "<div class=\"lawTitle\">Law (FS)</div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Departement</div><div class=\"metaText\">Creator</div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Utfärdad</div><div class=\"metaText\">RelDate</div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Källa</div><div class=\"metaText\">Publisher</div></div>" +
                "<div class=\"metaHeading\"><div class=\"metaTitle\">Senast hämtad</div><div class=\"metaText\">FetchDate</div></div>" +
                "</div>" +

                "<div class=\"lawBody\">" +
                "<div class=\"part_divider\">Divider" +
                "<div class=\"part_chapter\">Chapter" +
                "<div class=\"part_heading\">Heading" +
                "<div class=\"part_heading\">Heading2" +
                "<div class=\"part_paragraph\">Paragraph" +
                "<div class=\"part_section\">Section" +
                "<div class=\"part_section_list_item\">SectionListItem" +
                "</div></div></div></div></div></div></div>" +
                "<div class=\"part_deprecated\">Deprecated</div>" +
                "</div></div>"
                , html);
    }

    private LawDocumentPart getPart(String text, LawDocumentPartType type) {
        TextElement textElement = new TextElement();
        textElement.setText(text);
        LawDocumentPart divider = new LawDocumentPart();
        divider.setTextElement(textElement);
        divider.setLawPartType(type);
        return divider;
    }
}
