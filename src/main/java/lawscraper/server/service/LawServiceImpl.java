package lawscraper.server.service;


import lawscraper.server.components.renderers.lawrenderer.LawRenderer;
import lawscraper.server.components.LawStore;
import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.repositories.LawPartRepository;
import lawscraper.server.repositories.LawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/13/11
 * Time: 8:44 AM
 */

@Service("lawServiceImpl")
@Transactional(readOnly = true)
public class LawServiceImpl implements LawService {
    LawStore lawStore = null;

    @Autowired
    private LawRenderer lawRenderer;
    @Autowired
    private LawPartRepository lawPartRepository;

    @Autowired
    private LawRepository lawRepository;

    @Autowired
    public LawServiceImpl(LawStore lawStore) {
        this.lawStore = lawStore;
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
        return new HTMLWrapper(lawRenderer.renderToHtml(lawRepository.findOne(id)));
    }

    @Override
    public List<Law> findLawByQuery(String query) {
        List<Law> result = new ArrayList<Law>();
        /* todo: fix paging */
        String queryString = "*" + query + "*";
        Iterable<Law> foundLaw = lawRepository.findAllByQuery("title", queryString);
        int i = 20;
        for (Law law : foundLaw) {

            if (i-- == 0) {
                break;
            }
            result.add(law);
        }
        return result;
    }

    @Override
    public LawDocumentPart findLawDocumentPart(Long documentPartId) {
        return lawPartRepository.findOne(documentPartId);
    }
}
