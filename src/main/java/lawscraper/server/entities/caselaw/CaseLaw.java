package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.law.Law;
import lawscraper.shared.DocumentPartType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/13/12
 * Time: 6:42 PM
 */
@Entity
@Table(name = "caseLaw")
public class CaseLaw extends CaseLawDocumentPart {
    private String publicationYear;
    private String publisher;
    private String creator;
    private String relation;
    private String caseIdentifier;
    private List<CaseLawSubject> subjectList = new ArrayList<CaseLawSubject>();
    private String casePublication;
    private String caseNumber;
    private String decisionDate;
    private String description;

    private List<Law> lawReferenceList = new ArrayList<Law>();
    private String pageNumber;

    public String getPublicationYear() {
        return publicationYear;
    }

    public CaseLaw() {
        this.setPartType(DocumentPartType.CASELAW);
    }

    @Column(length = 20)
    public String getCreator() {
        return creator;
    }

    @Column(length = 20)
    public String getRelation() {
        return relation;
    }

    @Column(length = 20)
    public String getCaseIdentifier() {
        return caseIdentifier;
    }

    @Column(length = 20)
    public String getCasePublication() {
        return casePublication;
    }

    @Column(length = 20)
    public String getDecisionDate() {
        return decisionDate;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setSubjectList(List<CaseLawSubject> subjectList) {
        this.subjectList = subjectList;
    }

    public void setLawReferenceList(List<Law> lawReferenceList) {
        this.lawReferenceList = lawReferenceList;
    }


    @Column(length = 20)
    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Column(length = 20)
    public String getPublisher() {
        return publisher;
    }

    @Column(length = 20)
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

    public void addSubject(String data) {
        //this.getSubjectList().add(data);
    }

    @OneToMany(mappedBy = "caseLaw", cascade = CascadeType.ALL)
    public List<CaseLawSubject> getSubjectList() {
        return subjectList;
    }

    public void setCasePublication(String casePublication) {
        this.casePublication = casePublication;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addLawReference(String data) {
        //this.getLawReferenceList().add(data);
    }

    @ManyToMany
    public List<Law> getLawReferenceList() {
        return lawReferenceList;
    }

    public void addCaseLawDocumentPart(CaseLawDocumentPart part) {
        if (parts == null) {
            parts = new HashSet<CaseLawDocumentPart>();
        }

        part.setListOrder(parts.size());
        parts.add(part);
    }

}
