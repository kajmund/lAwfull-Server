package lawscraper.server.service;

import lawscraper.server.entities.documentbookmark.DocumentBookMark;
import lawscraper.server.entities.legalresearch.LegalResearch;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/15/12
 * Time: 1:18 PM
 */

@Service("DocumentPartServiceImpl")
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {
    private RepositoryBase<DocumentPart> documentPartRepository;
    private LegalResearchService legalResearchService;

    @Autowired
    public DocumentServiceImpl(RepositoryBase<DocumentPart> documentPartRepository,
                               LegalResearchService legalResearchService) {
        this.documentPartRepository = documentPartRepository;
        this.legalResearchService = legalResearchService;

        this.documentPartRepository.setEntityClass(DocumentPart.class);
    }

    @Override
    public List<DocumentPart> getDocumentsByLegalResearch(Long id) {
        List<DocumentPart> documentParts = new ArrayList<DocumentPart>();
        LegalResearch legalResearch = legalResearchService.find(id);
        for (DocumentBookMark documentBookMark : legalResearch.getBookMarks()) {
            documentParts.add(documentBookMark.getDocumentPart());
        }
        return documentParts;
    }

    @Override
    public HTMLWrapper getDocumentCommentary(String documentId) {
        DocumentPart document;
        try {
            document = documentPartRepository.findOne(documentId);
        } catch (NoSuchElementException e) {
            return null;
        }
        HTMLWrapper wrapper = new HTMLWrapper();
        wrapper.setHtml(document.getDescription());
        wrapper.setLawKey(documentId);
        wrapper.setName(document.getTitle());
        return wrapper;
    }

}
