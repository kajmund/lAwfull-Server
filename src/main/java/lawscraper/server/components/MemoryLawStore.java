package lawscraper.server.components;

import lawscraper.server.entities.law.Law;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 9:45 AM
 */

@Component
public class MemoryLawStore implements LawStore {
    Map<Long, Law> lawMap = new HashMap<Long, Law>();
    long id = 0;

    @Override
    public Law persistLaw(Law law) {
        lawMap.put(id++, law);
        law.setId(id);
        return law;
    }

    @Override
    public Law findOne(Long id) {
        return lawMap.get(id);
    }

    @Override
    public Collection<Law> findAllLaws(){
        return lawMap.values();
    }
}
