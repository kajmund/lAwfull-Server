package lawscraper.server.service;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
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
        assertEquals("<table><tbody><tr><td><h1> ()</h1><dl>" +
                "<dt>Departement</dt><dd></dd>" +
                "<dt>Utfärdad</dt><dd></dd>" +
                "<dt>Källa</dt><dd></dd>" +
                "<dt>Senast hämtad</dt><dd></dd>" +
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
        LawDocumentPart divider = new LawDocumentPart();
        divider.setLawPartType(LawDocumentPartType.DIVIDER);
        law.addDocumentPartChild(divider);
        String html = renderer.renderToHtml(law);
        assertEquals("<table><tbody><tr><td><h1>Law (FS)</h1><dl>" +
                "<dt>Departement</dt><dd>Creator</dd>" +
                "<dt>Utfärdad</dt><dd>RelDate</dd>" +
                "<dt>Källa</dt><dd>Publisher</dd>" +
                "<dt>Senast hämtad</dt><dd>FetchDate</dd>" +
                "</dl></td></tr></tbody></table>"
                , html);
    }
}
