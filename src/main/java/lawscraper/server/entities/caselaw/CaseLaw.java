package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.shared.DocumentListItem;
import lawscraper.shared.DocumentPartType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 6/13/12
 * Time: 6:42 PM
 */
@Entity
@Table(name = "caseLaw")
public class CaseLaw extends CaseLawDocumentPart implements DocumentListItem {
    private String publicationYear;
    private String publisher;
    private String creator;
    private String relation;
    private String caseIdentifier;
    private String casePublication;
    private String caseNumber;
    private String decisionDate;


    private String pageNumber;

    public String getPublicationYear() {
        return publicationYear;
    }

    public CaseLaw() {
        this.setPartType(DocumentPartType.CASELAW);
    }

    @Column(length = 255)
    public String getCreator() {
        return creator;
    }

    @Column(length = 1024)
    public String getRelation() {
        return relation;
    }

    @Column(length = 255)
    public String getCaseIdentifier() {
        return caseIdentifier;
    }

    @Column(length = 255)
    public String getCasePublication() {
        return casePublication;
    }

    @Column(length = 255)
    public String getDecisionDate() {
        return decisionDate;
    }

    @Column(length = 20)
    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Column(length = 255)
    public String getPublisher() {
        return publisher;
    }

    @Column(length = 255)
    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setCaseIdentifier(String caseIdentifier) {
        this.caseIdentifier = caseIdentifier;
    }

    public void addSubject(DocumentPart subject) {
        subject.addDocumentReference(this);
    }

    public void setCasePublication(String casePublication) {
        this.casePublication = casePublication;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public void addCaseLawDocumentPart(CaseLawDocumentPart part) {
        if (getParts() == null) {
            setParts(new HashSet<CaseLawDocumentPart>());
        }

        part.setListOrder(getParts().size());
        getParts().add(part);
    }
}
