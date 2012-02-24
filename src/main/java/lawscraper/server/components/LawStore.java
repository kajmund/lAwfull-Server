package lawscraper.server.components;

import lawscraper.server.entities.law.Law;

import java.util.Collection;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 9:43 AM
 */
public interface LawStore {
    Law persistLaw(Law law);

    Law findOne(Long id);

    Collection<Law> findAllLaws();
}
