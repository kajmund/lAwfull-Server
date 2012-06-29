package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.shared.DocumentPartType;
import lawscraper.server.entities.superclasses.Document.TextElement;

/**
 * TODO Document this.
 */
public class DummyPartFactory implements PartFactory {
    long id = 0;

    @Override
    public LawDocumentPart createpart(DocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        part.setId(id++);
        return part;
    }

    @Override
    public TextElement persistTextElement(TextElement textElement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return textElement;
    }
}
