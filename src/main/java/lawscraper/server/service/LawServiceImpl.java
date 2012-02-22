package lawscraper.server.service;


import lawscraper.server.scraper.Scraper;
import lawscraper.server.entities.law.Law;
import lawscraper.server.repositories.LawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/13/11
 * Time: 8:44 AM
 */

@Service("lawServiceImpl")
public class LawServiceImpl implements LawService {
    LawRepository lawRepository = null;
    TextService textService = null;

    @Autowired
    public LawServiceImpl(LawRepository lawRepository, TextService textService) {
        this.lawRepository = lawRepository;
        this.textService = textService;
    }

    @Transactional
    @Override
    public void scrapeAll() {
        Scraper scraper = new Scraper(textService);
        Law law = scraper.parseLaw("https://lagen.nu/1978:413.xht2");

        lawRepository.save(law);
    }

    @Override
    public Law findLaw(Long id) {
        return lawRepository.findAll().iterator().next();
    }

    @Override
    public Law find(Long id) {
        return lawRepository.findOne(id);
    }

    @Override
    public Law find(Class<? extends Law> clazz, Long id) {
        return lawRepository.findOne(id);
    }
}
