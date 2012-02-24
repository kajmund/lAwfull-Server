package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;

/**
 * TODO Document this.
 */
public interface PartFactory {
    
    LawDocumentPart createpart(LawDocumentPartType type);
}
