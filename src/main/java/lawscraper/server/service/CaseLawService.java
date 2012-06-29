package lawscraper.server.service;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.caselaw.CaseLawDocumentPart;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/26/12
 * Time: 10:54 AM
 */
public interface CaseLawService {
    List<CaseLaw> findCaseLawByQuery(String query);

    CaseLawDocumentPart findCaseLawDocumentPart(Long documentPartId);

    HTMLWrapper findCaseLawHTMLWrapped(Long id);

    CaseLaw find(Long id);
}
