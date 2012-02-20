package lawscraper.server.service;

import lawscraper.server.entities.law.Law;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 8:53 PM
 */
public interface LawService {

    @Transactional
    void scrapeAll();
    Law findLaw(Long id);
    Law find(Long id);

    Law find(Class<? extends Law> clazz, Long id);
}
