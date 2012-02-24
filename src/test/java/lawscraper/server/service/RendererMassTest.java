package lawscraper.server.service;

import lawscraper.server.scraper.DummyPartFactory;
import lawscraper.server.scraper.Scraper;
import lawscraper.server.scraper.TestDataUtil;
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
        for (TestDataUtil.Law lawData : TestDataUtil.getAllLaws()) {
            lawCount++;
            Scraper scraper = new Scraper(partFactory);
            try {
                scraper.parse(lawData.getInputStream());
                renderer.renderToHtml(scraper.getLaw());
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to render " + lawData.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not rendable", lawCount, successCount);
    }

}
