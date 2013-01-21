package lawscraper.shared.scraper;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 9:51 AM
 */
public class ScraperStatus {
    private int scrapedLaws = 0;

    public ScraperStatus() {
    }

    public void increaseScrapedLaws() {
        scrapedLaws++;
    }

    public int getScrapedLaws() {
        return scrapedLaws;
    }

    public void setScrapedLaws(int scrapedLaws) {
        this.scrapedLaws = scrapedLaws;
    }
}
