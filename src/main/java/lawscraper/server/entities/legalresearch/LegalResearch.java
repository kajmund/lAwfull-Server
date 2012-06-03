package lawscraper.server.entities.legalresearch;

import lawscraper.server.entities.documentbookmark.DocumentBookMark;
import lawscraper.server.entities.superclasses.EntityBase;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/16/12
 * Time: 6:43 PM
 */
public class LegalResearch extends EntityBase {
    @RelatedTo(elementClass = DocumentBookMark.class, type = "LAWPART_BOOKMARK")
    @Fetch
    HashSet<DocumentBookMark> bookMarks = new HashSet<DocumentBookMark>();

    private String title;
    private String description;

    public LegalResearch() {
    }

    public LegalResearch(String title, String description) {
        this.setTitle(title);
        this.setDescription(description);
    }

    public HashSet<DocumentBookMark> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(HashSet<DocumentBookMark> bookMarks) {
        this.bookMarks = bookMarks;
    }

    public void addBookMark(DocumentBookMark documentBookMark) {
        getBookMarks().add(documentBookMark);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean removeBookMark(Long documentPartId) {
        DocumentBookMark documentBookMarkToRemove = null;
        for (DocumentBookMark bookMark : bookMarks) {
            if (bookMark.getLawDocumentPart().getId().equals(documentPartId)) {
                documentBookMarkToRemove = bookMark;
                break;
            }
        }

        if (documentBookMarkToRemove != null) {
            getBookMarks().remove(documentBookMarkToRemove);
            return true;
        }

        return false;
    }
}
