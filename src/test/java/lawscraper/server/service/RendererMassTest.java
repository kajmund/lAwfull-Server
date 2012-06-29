package lawscraper.server.service;

import lawscraper.server.components.DummyPartFactory;
import lawscraper.server.components.renderers.lawrenderer.LawRendererImpl;
import lawscraper.server.scrapers.lawscraper.LawScraper;
import lawscraper.server.scrapers.ZipDataUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that all laws are rendable without error.
 */
public class RendererMassTest {

    @Test
    public void renderAllLaws() throws Exception {
        LawRendererImpl renderer = new LawRendererImpl();
        int lawCount = 0;
        int successCount = 0;
        DummyPartFactory partFactory = new DummyPartFactory();
        for (ZipDataUtil.LawEntry lawEntryData : ZipDataUtil.getAllLaws()) {
            lawCount++;
            LawScraper scraper = new LawScraper(partFactory);
            try {
                scraper.parse(lawEntryData.getInputStream());
                renderer.renderToHtml(scraper.getLaw());
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to render " + lawEntryData.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not rendable", lawCount, successCount);
    }

}
