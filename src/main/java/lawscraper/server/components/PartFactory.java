package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.entities.superclasses.Document.TextElement;

/**
 * TODO Document this.
 */
public interface PartFactory {
    
    LawDocumentPart createpart(LawDocumentPartType type);

    TextElement persistTextElement(TextElement textElement);
}
