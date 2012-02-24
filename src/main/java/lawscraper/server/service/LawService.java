package lawscraper.server.service;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 8:53 PM
 */
public interface LawService {
    LawWrapper find(Long id);
    List<LawWrapper> findAll();
    HTMLWrapper findLawHTMLWrapped(Long id);
}
