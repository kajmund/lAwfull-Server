package lawscraper.server.service;


import lawscraper.server.components.LawRenderer;
import lawscraper.server.components.LawStore;
import lawscraper.server.entities.law.Law;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
    private LawRenderer lawRenderer;

    @Autowired
    public LawServiceImpl(LawStore lawStore, LawRenderer lawRenderer) {
        this.lawStore = lawStore;
        this.lawRenderer = lawRenderer;
    }

    @Override
    public LawWrapper find(Long id) {
        return new LawWrapper(lawStore.findOne(id), lawRenderer);
    }

    @Override
    public List<LawWrapper> findAll() {
        Collection<Law> allLaws = lawStore.findAllLaws();
        ArrayList<LawWrapper> wrappers = new ArrayList<LawWrapper>(allLaws.size());
        for (Law law : allLaws) {
            wrappers.add(new LawWrapper(law, lawRenderer));
        }
        return wrappers;
    }

    @Override
    public HTMLWrapper findLawHTMLWrapped(Long id){
        return new HTMLWrapper(lawRenderer.renderToHtml(lawStore.findOne(id)));
    }
}
