package lawscraper.server.service;

import lawscraper.server.entities.law.Law;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 8:53 PM
 */
public interface LawService {

    Law find(Long id);
    Law createOrUpdate(Law law);

}
