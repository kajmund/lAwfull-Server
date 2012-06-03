package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.repositories.LawPartRepository;
import lawscraper.server.repositories.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO Document this.
 */
@Component
@Transactional(readOnly = true)
public class PartFactoryImpl implements PartFactory {

    @Autowired
    private LawPartRepository repository;

    @Autowired
    private TextRepository textRepository;

    @Override
    @Transactional(readOnly = false)
    public LawDocumentPart createpart(LawDocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        LawDocumentPart savedPart = repository.save(part);
        return savedPart;
    }

    @Override
    @Transactional(readOnly = false)
    public TextElement persistTextElement(TextElement textElement) {
        return textRepository.save(textElement);
    }
}
