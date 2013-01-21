package lawscraper.server.entities.documentbookmark;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.entities.superclasses.EntityBase;

import javax.persistence.*;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/17/12
 * Time: 9:55 AM
 */
@Entity
@Table(name = "documentBookMark")
public class DocumentBookMark extends EntityBase {
    public void setDocumentPart(DocumentPart documentPart) {
        this.documentPart = documentPart;
    }

    private DocumentPart documentPart;
    private String description;
    private String title;

    public DocumentBookMark() {
    }

    public DocumentBookMark(DocumentPart documentPart, String title, String description) {
        this.documentPart = documentPart;
        this.title = title;
        this.description = description;
    }

    @Transient
    public String getDocumentKey() {
        return this.documentPart.getKey();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public DocumentPart getDocumentPart() {
        return documentPart;
    }

    public void setDocumentPart(LawDocumentPart documentPart) {
        this.documentPart = documentPart;
    }

    @Column(nullable = false, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false, length = 1024)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
