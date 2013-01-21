package lawscraper.server.service;

import lawscraper.server.components.renderers.caselawrenderer.CaseLawRenderer;
import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.caselaw.CaseLawDocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 6/26/12
 * Time: 10:53 AM
 */
@Service("caseLawServiceImpl")
@Transactional(readOnly = true)
public class CaseLawServiceImpl implements CaseLawService {


    RepositoryBase<CaseLaw> caseLawRepository;
    CaseLawRenderer caseLawRenderer;

    @Autowired
    public CaseLawServiceImpl(RepositoryBase<CaseLaw> caseLawRepository, CaseLawRenderer caseLawRenderer) {
        this.caseLawRepository = caseLawRepository;
        this.caseLawRenderer = caseLawRenderer;
        this.caseLawRepository.setEntityClass(CaseLaw.class);
    }

    @Override
    public CaseLaw find(Long id) {
        return caseLawRepository.findOne(id);
    }

    @Override
    public List<CaseLaw> findCaseLawByQuery(String query) {
        List<CaseLaw> result = new ArrayList<CaseLaw>();
        /* todo: fix paging */
        String queryString = query;
        Iterable<CaseLaw> foundCaseLaw = caseLawRepository.findAllByQuery("description", queryString);
        int i = 20;
        for (CaseLaw caseLaw : foundCaseLaw) {

            if (i-- == 0) {
                break;
            }
            result.add(caseLaw);
        }
        return result;
    }

    @Override
    public CaseLawDocumentPart findCaseLawDocumentPart(Long documentPartId) {
        return caseLawRepository.findOne(documentPartId);
    }

    @Override
    public HTMLWrapper findCaseLawHTMLWrapped(Long id) {
        CaseLaw caseLaw = caseLawRepository.findOne(id);
        return new HTMLWrapper(caseLaw.getCaseIdentifier(), caseLaw.getCaseIdentifier(),
                               caseLawRenderer.renderToHtml(caseLaw));
    }

    @Override
    public HTMLWrapper findCaseLawHTMLWrapped(String key) {
        CaseLaw caseLaw = caseLawRepository.findByPropertyValue("documentKey", key).iterator().next();
        return new HTMLWrapper(caseLaw.getKey().replace("_", " "), caseLaw.getKey(),
                               caseLawRenderer.renderToHtml(caseLaw));
    }

    @Override
    public List<CaseLaw> getCaseLawsByYearAndCourt(String year, String court) {
        List<CaseLaw> result = new ArrayList<CaseLaw>();
        Iterable<CaseLaw> foundCaseLaw =
                caseLawRepository.findByPropertyValues(Arrays.asList("publicationYear", "casePublication"),
                                                       Arrays.asList(year, court));

        int i = 20;
        for (CaseLaw caseLaw : foundCaseLaw) {
            if (i-- == 0) {
                break;
            }
            result.add(caseLaw);
        }
        return result;
    }
}
