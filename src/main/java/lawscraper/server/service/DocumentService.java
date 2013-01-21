package lawscraper.server.service;

import lawscraper.server.entities.superclasses.Document.DocumentPart;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/15/12
 * Time: 1:19 PM
 */
public interface DocumentService {
    List<DocumentPart> getDocumentsByLegalResearch(Long id);

    HTMLWrapper getDocumentCommentary(String documentId);
}
