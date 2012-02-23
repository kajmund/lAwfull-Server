package lawscraper.server.service;


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
@Transactional(readOnly = true)
public class LawServiceImpl implements LawService {
    LawRepository lawRepository = null;

    @Autowired
    public LawServiceImpl(LawRepository lawRepository) {
        this.lawRepository = lawRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Law createOrUpdate(Law law) {
        return lawRepository.save(law);
    }

    @Override
    public Law find(Long id) {
        return lawRepository.findOne(id);
    }
}
