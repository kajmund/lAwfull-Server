package lawscraper.server.service;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 8:53 PM
 */
public interface LawService {
    Law find(Long id);

    List<Law> findAll();

    HTMLWrapper findLawHTMLWrapped(Long id);

    List<Law> findLawByQuery(String query);

    LawDocumentPart findLawDocumentPart(Long documentPartId);
}
