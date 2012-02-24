package lawscraper.server.scraper;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;

/**
 * TODO Document this.
 */
public class DummyPartFactory implements PartFactory {
    @Override
    public LawDocumentPart createpart(LawDocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        return part;
    }
}
