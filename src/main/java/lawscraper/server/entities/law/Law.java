package lawscraper.server.entities.law;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.shared.DocumentPartType;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 7:47 PM
 */
@Entity
@Table(name = "law")
public class Law extends LawDocumentPart {
    String title = "";
    String latestFetchFromGov = "";
    String releaseDate = "";
    String publisher = "";
    String creator = "";
    private Collection<CaseLaw> caseLaws;

    Set<Consolidation> consolidations = new HashSet<Consolidation>();
    Set<Proposition> propositions = new HashSet<Proposition>();

    public Law() {
        this.setLawPartType(DocumentPartType.LAW);
    }

    @Column(nullable = false, length = 255)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Index(name = "titleIndex")
    @Column(nullable = false, length = 2048)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(unique = false, nullable = false, length = 20)
    public String getLatestFetchFromGov() {
        return latestFetchFromGov;
    }

    public void setLatestFetchFromGov(String latestFetchFromGov) {
        this.latestFetchFromGov = latestFetchFromGov;
    }

    @Column(nullable = false, length = 20)
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(nullable = false, length = 255)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Consolidation> getConsolidations() {
        return consolidations;
    }

    public void setConsolidations(Set<Consolidation> consolidations) {
        this.consolidations = consolidations;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(Set<Proposition> propositions) {
        this.propositions = propositions;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "lawReferenceList")
    public Collection<CaseLaw> getCaseLaws() {
        return caseLaws;
    }

    public void setCaseLaws(Collection<CaseLaw> caseLaws) {
        this.caseLaws = caseLaws;
    }
}
