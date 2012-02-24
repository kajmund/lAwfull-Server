package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.repositories.LawPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO Document this.
 */
@Component
public class PartFactoryImpl implements PartFactory {

    @Autowired
    private LawPartRepository repository;

    @Override
    public LawDocumentPart createpart(LawDocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        repository.save(part);
        return part;
    }
}
