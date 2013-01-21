package lawscraper.server.service;

import lawscraper.server.components.LawStore;
import lawscraper.server.components.renderers.lawrenderer.LawRenderer;
import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 11/13/11
 * Time: 8:44 AM
 */

@Service("lawServiceImpl")
@Transactional(readOnly = true)
public class LawServiceImpl implements LawService {
    LawStore lawStore = null;

    private LawRenderer lawRenderer;
    private RepositoryBase<LawDocumentPart> lawPartRepository;
    private RepositoryBase<Law> lawRepository;
    private CaseLawService caseLawService;

    @Autowired
    public LawServiceImpl(LawStore lawStore, LawRenderer lawRenderer, RepositoryBase<LawDocumentPart> lawPartRepository,
                          RepositoryBase<Law> lawRepository, CaseLawService caseLawService) {
        this.lawStore = lawStore;
        this.lawRenderer = lawRenderer;
        this.lawPartRepository = lawPartRepository;
        this.lawRepository = lawRepository;
        this.caseLawService = caseLawService;
        this.lawRepository.setEntityClass(Law.class);
        this.lawPartRepository.setEntityClass(LawDocumentPart.class);
    }

    @Override
    public Law find(Long id) {
        return lawRepository.findOne(id);
    }

    @Override
    public List<Law> findAll() {
        List<Law> allLawsList = new ArrayList<Law>();
        Iterable<Law> allLaws = lawRepository.findAll();
        while (allLaws.iterator().hasNext()) {
            Law law = allLaws.iterator().next();
            allLawsList.add(law);
        }
        return allLawsList;
    }

    @Override
    public HTMLWrapper findLawHTMLWrapped(Long id) {
        Law law = lawRepository.findOne(id);
        return new HTMLWrapper(law.getTitle(), law.getKey(), lawRenderer.renderToHtml(law));
    }

    @Override
    public HTMLWrapper findLawHTMLWrappedByLawKey(String lawKey) {
        //todo: construct a new DocumentService, this looks ugly
        if (!isInteger(lawKey.substring(0, 1))) {
            ////fetch the caselaw
            return caseLawService.findCaseLawHTMLWrapped(lawKey);
        } else {
            //fetch the law
            //Law law = lawRepository.findByPropertyValue("documentKey", lawKey).iterator().next();
            Law law = lawRepository.findOne(lawKey);
            return new HTMLWrapper(law.getTitle(), law.getKey(), lawRenderer.renderToHtml(law));
        }
    }

    private boolean isInteger(String str) {
        return str.matches("^-?[0-9]+(\\.[0-9]+)?$");
    }


    @Override
    public List<Law> findLawByQuery(String query) {
        List<Law> result = new ArrayList<Law>();

        /* todo: fix paging */
        //Iterable<Law> foundLaws = lawRepository.findByPropertyValue("title", query);
        Iterable<Law> foundLaws = lawRepository.findAllByQuery("title", query);
        for (Law law : foundLaws) {
            result.add(law);
        }

        System.out.println(query + ":" + result.size());
        return result;
    }

    @Override
    public LawDocumentPart findLawDocumentPart(Long documentPartId) {
        return lawPartRepository.findOne(documentPartId);
    }
}
