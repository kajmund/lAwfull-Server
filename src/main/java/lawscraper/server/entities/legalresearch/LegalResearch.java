package lawscraper.server.entities.legalresearch;

import lawscraper.server.entities.documentbookmark.DocumentBookMark;
import lawscraper.server.entities.superclasses.EntityBase;
import lawscraper.server.entities.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/16/12
 * Time: 6:43 PM
 */
@Entity
@Table(name = "legalResearch")
public class LegalResearch extends EntityBase {
    Set<DocumentBookMark> bookMarks = new HashSet<DocumentBookMark>();

    private String title;
    private String description;
    private User user;

    public LegalResearch() {
    }

    public LegalResearch(String title, String description) {
        this.setTitle(title);
        this.setDescription(description);
    }

    @OneToMany
    public Set<DocumentBookMark> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(Set<DocumentBookMark> bookMarks) {
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
            if (bookMark.getDocumentPart().getId().equals(documentPartId)) {
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
