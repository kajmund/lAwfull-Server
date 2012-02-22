package lawscraper.server.service;

/**
 * TODO Document this.
 */
public class LawRenderContext {
    private int headerLevel = 0;

    public void increaseHeaderLevel() {
        headerLevel++;
    }

    public void decreaseHeaderLevel() {
        headerLevel--;
    }

    public int getHeaderLevel() {
        return headerLevel;
    }

    public void increaseLevel() {
    }

    public void decreaseLevel() {
    }
}
