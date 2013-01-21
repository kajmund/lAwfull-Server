package lawscraper.server.service;


import lawscraper.server.entities.documentbookmark.DocumentBookMark;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.legalresearch.LegalResearch;
import lawscraper.server.entities.user.User;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.shared.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Service("documentBookMarkServiceImpl")
@Transactional(readOnly = true)
public class DocumentBookMarkServiceImpl implements DocumentBookMarkService {
    private RepositoryBase<DocumentBookMark> documentBookMarkRepository;

    private LawService lawService;

    private UserService userService;

    private RepositoryBase<LegalResearch> legalResearchRepository;


    @Autowired
    public DocumentBookMarkServiceImpl(RepositoryBase<DocumentBookMark> documentBookMarkRepository,
                                       UserService userService,
                                       LawService lawService, RepositoryBase<LegalResearch> legalResearchRepository) {
        this.documentBookMarkRepository = documentBookMarkRepository;
        this.userService = userService;
        this.lawService = lawService;
        this.legalResearchRepository = legalResearchRepository;
        this.legalResearchRepository.setEntityClass(LegalResearch.class);
    }

    @Override
    public DocumentBookMark find(Long id) {
        return documentBookMarkRepository.findOne(id);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = false)
    public void addBookMark(Long documentPartId) {
        User user = userService.getCurrentUser();

        //get active legal research
        LegalResearch legalResearch = user.getActiveLegalResearch();

        //add bookmark to legalresearch
        if (legalResearch != null) {

            //get clicked documentpart
            LawDocumentPart documentLawPart = lawService.findLawDocumentPart(documentPartId);

            //create bookmark
            DocumentBookMark documentBookMark = new DocumentBookMark();
            documentBookMark.setDescription(documentLawPart.getKey());
            documentBookMark.setTitle(documentLawPart.getKey());

            documentBookMark.setDocumentPart(documentLawPart);

            documentBookMark = documentBookMarkRepository.save(documentBookMark);

            legalResearch.addBookMark(documentBookMark);
            legalResearchRepository.save(legalResearch);
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = false)
    public void removeBookMark(Long documentPartId) {
        User user = userService.getCurrentUser();

        //get active legal research
        LegalResearch legalResearch = user.getActiveLegalResearch();
        if (legalResearch != null) {
            legalResearch.removeBookMark(documentPartId);
            legalResearchRepository.save(legalResearch);
        }
    }

    @Override
    public List<DocumentBookMark> findAll() {
        List<DocumentBookMark> allBookMarksList = new ArrayList<DocumentBookMark>();
        Iterable<DocumentBookMark> allBookMarks = documentBookMarkRepository.findAll();
        while (allBookMarks.iterator().hasNext()) {
            DocumentBookMark documentBookMark = allBookMarks.iterator().next();
            allBookMarksList.add(documentBookMark);
        }
        return allBookMarksList;
    }

    @Override
    public List<DocumentBookMark> findBookMarksByLawId(Long lawId) {
        User user = userService.getCurrentUser();
        List<DocumentBookMark> ret = new ArrayList<DocumentBookMark>();
        if (user.getUserRole() == UserRole.Anonymous) {
            return ret;
        }

        //get active legal research
        /*
        LegalResearch legalResearch = user.getActiveLegalResearch();
        for (DocumentBookMark documentBookMark : legalResearch.getBookMarks()) {
            if (documentBookMark.getLawDocumentPart().getBelongsToLaw().getId().equals(lawId)) {
                ret.add(documentBookMark);
            }
        }
        */
        return ret;
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public List<DocumentBookMark> findBookMarksByLawKey(String lawKey) {
        User user = userService.getCurrentUser();
        List<DocumentBookMark> ret = new ArrayList<DocumentBookMark>();
        if (user.getUserRole() == UserRole.Anonymous) {
            return ret;
        }

        /*
        //get active legal research
        LegalResearch legalResearch = user.getActiveLegalResearch();
        if (legalResearch != null) {
            for (DocumentBookMark documentBookMark : legalResearch.getBookMarks()) {
                Law law = lawService.find(documentBookMark.getDocumentPart().getBelongsToLaw().getId());
                if (law.getKey().equals(lawKey)) {
                    ret.add(documentBookMark);
                }
            }
        }

        */
        return ret;
    }

}
