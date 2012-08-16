package lawscraper.server.entities.caselaw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/13/12
 * Time: 6:42 PM
 */

public class    CaseLaw extends CaseLawDocumentPart {
    private String publicationYear;
    private String publisher;
    private String creator;
    private String relation;
    private String caseIdentifier;
    private List<String> subjectList = new ArrayList<String>();
    private String casePublication;
    private String caseNumber;
    private String decisionDate;
    private String description;
    private String identifier;
    private List<String> lawReferenceList = new ArrayList<String>();
    private String pageNumber;

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getCreator() {
        return creator;
    }

    public String getRelation() {
        return relation;
    }

    public String getCaseIdentifier() {
        return caseIdentifier;
    }

    public String getCasePublication() {
        return casePublication;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setSubjectList(List<String> subjectList) {
        this.subjectList = subjectList;
    }

    public void setLawReferenceList(List<String> lawReferenceList) {
        this.lawReferenceList = lawReferenceList;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }


    public String getPublisher() {
        return publisher;
    }

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
        this.getSubjectList().add(data);
    }

    public List<String> getSubjectList() {
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
        this.getLawReferenceList().add(data);
    }

    public List<String> getLawReferenceList() {
        return lawReferenceList;
    }

    public void addCaseLawDocumentPart(CaseLawDocumentPart part) {
        if (parts == null) {
            parts = new HashSet<CaseLawDocumentPart>();
        }

        part.setOrder(parts.size());
        parts.add(part);
    }

}
