package lawscraper.server.service;

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

        assertEquals("<table><tbody><tr><td><h1 class=\"lawTitle\"> ()</h1><dl>" +
                "<dt class=\"metaTitle\">Departement</dt><dd class=\"metaText\"></dd>" +
                "<dt class=\"metaTitle\">Utfärdad</dt><dd class=\"metaText\"></dd>" +
                "<dt class=\"metaTitle\">Källa</dt><dd class=\"metaText\"></dd>" +
                "<dt class=\"metaTitle\">Senast hämtad</dt><dd class=\"metaText\"></dd>" +
                "</dl></td></tr></tbody></table>"
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

        assertEquals("<table><tbody><tr><td><h1 class=\"lawTitle\">Law (FS)</h1><dl>" +
                "<dt class=\"metaTitle\">Departement</dt><dd class=\"metaText\">Creator</dd>" +
                "<dt class=\"metaTitle\">Utfärdad</dt><dd class=\"metaText\">RelDate</dd>" +
                "<dt class=\"metaTitle\">Källa</dt><dd class=\"metaText\">Publisher</dd>" +
                "<dt class=\"metaTitle\">Senast hämtad</dt><dd class=\"metaText\">FetchDate</dd>" +
                "</dl></td></tr>" +
                "<tr><td><h2 class=\"part_divider\">Divider</h2></td></tr>" +
                "<tr><td><h2 class=\"part_chapter\">Chapter</h2></td></tr>" +
                "<tr><td><h2 class=\"part_heading\">Heading</h2></td></tr>" +
                "<tr><td><h3 class=\"part_heading\">Heading2</h3></td></tr>" +
                "<tr><td>" +
                "<p><span class=\"part_paragraph\">Paragraph</span>" +
                "<span class=\"part_section\">Section</span>" +
                "<ul class=\"part_section_list_item\"><li>SectionListItem</li></ul></p>" +
                "</td></tr>" +
                "<tr><td><h2 class=\"part_deprecated\">Deprecated</h2></td></tr>" +
                "</tbody></table>"
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
