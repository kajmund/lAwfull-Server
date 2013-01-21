package lawscraper.server.scrapers;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.lawscraper.LawScraper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Test that all laws are parseable without error.
 */
public class ParserMassTest {
    @Autowired
    private RepositoryBase<DocumentPart> documentPartRepository;

    @Test
    public void parseAllLaws() throws Exception {
        this.documentPartRepository.setEntityClass(DocumentPart.class);
        int lawCount = 0;
        int successCount = 0;
        for (ZipDataUtil.LawEntry lawEntry : ZipDataUtil.getAllLaws()) {
            lawCount++;
            LawScraper scraper = new LawScraper(documentPartRepository);
            try {
                scraper.parse(lawEntry.getInputStream());
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to parse " + lawEntry.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not parseable", lawCount, successCount);
    }

}
