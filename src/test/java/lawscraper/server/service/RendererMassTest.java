package lawscraper.server.service;

import lawscraper.server.scraper.Scraper;
import lawscraper.server.scraper.TestDataUtil;
import lawscraper.server.scraper.TextServiceDummyImpl;
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
        for (TestDataUtil.Law lawData : TestDataUtil.getAllLaws()) {
            lawCount++;
            Scraper scraper = new Scraper(new TextServiceDummyImpl());
            try {
                renderer.renderToHtml(scraper.parseLaw(lawData.getInputStream()));
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to render " + lawData.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not rendable", lawCount, successCount);
    }

}
