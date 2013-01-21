package lawscraper.server.service;


import lawscraper.server.entities.legalresearch.LegalResearch;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 8:34 PM
 */

public interface LegalResearchService {

    void updateLegalResearch(LegalResearch legalResearch);

    LegalResearch find(Long id);

    void addLegalResearch(String title, String description);

    void addDocumentBookMark(Long legalResearchId, Long lawDocumentPartId, String title, String description);

    List<LegalResearch> findLegalResearchByLoggedInUser();

    void update(LegalResearch legalResearch);

    void setLegalResearchActive(Long legalResearchId);
}
