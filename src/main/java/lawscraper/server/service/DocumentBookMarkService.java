package lawscraper.server.service;

import lawscraper.server.entities.documentbookmark.DocumentBookMark;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/30/12
 * Time: 9:15 PM
 */
public interface DocumentBookMarkService {
    DocumentBookMark find(Long id);

    List<DocumentBookMark> findAll();

    List<DocumentBookMark> findBookMarksByLawId(Long lawId);

    void removeBookMark(Long documentPartId);

    void addBookMark(Long documentPartId);

    List<DocumentBookMark> findBookMarksByLawKey(String lawKey);
}
