package lawscraper.server.components;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.superclasses.Document.TextElement;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.shared.DocumentPartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO Document this.
 */
@Component
@Transactional(readOnly = true)
public class PartFactoryImpl implements PartFactory {
    private RepositoryBase<LawDocumentPart> repository;
    private RepositoryBase<TextElement> textRepository;

    @Autowired
    public PartFactoryImpl(@Qualifier("repositoryBaseImpl") RepositoryBase<LawDocumentPart> repository,
                           @Qualifier("repositoryBaseImpl") RepositoryBase<TextElement> textRepository) {
        this.repository = repository;
        this.textRepository = textRepository;
        repository.setEntityClass(LawDocumentPart.class);
    }

    @Override
    @Transactional(readOnly = false)
    public LawDocumentPart createpart(DocumentPartType type) {
        LawDocumentPart part = new LawDocumentPart();
        part.setLawPartType(type);
        return repository.save(part);
    }

    @Override
    @Transactional(readOnly = false)
    public TextElement persistTextElement(TextElement textElement) {
        return textRepository.save(textElement);
    }
}
