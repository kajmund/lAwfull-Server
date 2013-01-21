package lawscraper.server.service;

import lawscraper.server.entities.documentbookmark.DocumentBookMark;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.legalresearch.LegalResearch;
import lawscraper.server.entities.user.User;

import lawscraper.server.repositories.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 8:33 PM
 */

@Service("LegalResearchService")
@Transactional(readOnly = true)
public class LegalResearchServiceImpl implements LegalResearchService {

    private RepositoryBase<LegalResearch> legalResearchRepository = null;
    private RepositoryBase<LawDocumentPart> lawPartRepository = null;
    private UserService userService;


    @Autowired
    public LegalResearchServiceImpl(RepositoryBase<LegalResearch> legalResearchRepository,
                                    @Qualifier("repositoryBaseImpl") RepositoryBase<LawDocumentPart> lawPartRepository,
                                    UserService userService
                                   ) {
        this.legalResearchRepository = legalResearchRepository;
        this.lawPartRepository = lawPartRepository;
        this.userService = userService;

        this.legalResearchRepository.setEntityClass(LegalResearch.class);
        this.lawPartRepository.setEntityClass(LawDocumentPart.class);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<LegalResearch> findLegalResearchByLoggedInUser() {
        List<LegalResearch> ret = null;
        User user = userService.getCurrentUser();

        if (user != null) {
            ret = new ArrayList<LegalResearch>(user.getLegalResearch());
        }
        return ret;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void update(LegalResearch legalResearch) {
        legalResearchRepository.save(legalResearch);
    }

    @Override
    public LegalResearch find(Long id) {
        //todo: add new exception
        return legalResearchRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    //@PreAuthorize("isAuthenticated()")
    public void addLegalResearch(String title, String description) {
        LegalResearch legalResearch = new LegalResearch(title, description);
        User user = userService.getCurrentUser();
        //legalResearch = legalResearchRepository.save(legalResearch);

        /*
        if (user.getLegalResearch().size() == 0) {
            user.setActiveLegalResearch(legalResearch);
            userService.updateUser(user);
        }
        */

        user.addLegalResearch(legalResearch);
        //userService.updateUser(user);
    }

    @Override
    public void setLegalResearchActive(Long legalResearchId) {
        LegalResearch legalResearch = legalResearchRepository.findOne(legalResearchId);
        User user = userService.getCurrentUser();
        user.setActiveLegalResearch(legalResearch);
        userService.updateUser(user);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("isAuthenticated()")
    public void updateLegalResearch(LegalResearch legalResearch) {
        legalResearchRepository.save(legalResearch);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("isAuthenticated()")
    public void addDocumentBookMark(Long legalResearchId, Long lawDocumentPartId, String title, String description) {
        LegalResearch legalResearch = legalResearchRepository.findOne(legalResearchId);
        //todo: add new exception
        LawDocumentPart lawDocumentPart = lawPartRepository.findOne(lawDocumentPartId);
        //todo: add new exception
        legalResearch.addBookMark(new DocumentBookMark(lawDocumentPart, title, description));
        //todo: add new exception
        legalResearchRepository.save(legalResearch);
    }
}
