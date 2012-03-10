package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;

/**
 * TODO Document this.
 */
public class DummyPartFactory implements PartFactory {
    long id = 0;

    @Override
    public LawDocumentPart createpart(LawDocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        part.setId(id++);
        return part;
    }
}
