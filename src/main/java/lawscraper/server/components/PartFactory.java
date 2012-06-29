package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.shared.DocumentPartType;
import lawscraper.server.entities.superclasses.Document.TextElement;

/**
 * TODO Document this.
 */
public interface PartFactory {
    
    LawDocumentPart createpart(DocumentPartType type);

    TextElement persistTextElement(TextElement textElement);
}
