package lawscraper.server.entities.law;

import org.springframework.data.neo4j.annotation.Indexed;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 7:47 PM
 */

public class Law extends LawDocumentPart {
    @Indexed
    String title = "";
    String fsNumber = "";
    String latestFetchFromGov = "";
    String releaseDate = "";
    String publisher = "";
    String creator = "";

    Set<String> consolidations = new HashSet<String>();
    Set<String> propositions = new HashSet<String>();

    public Law() {
        this.setLawPartType(LawDocumentPartType.LAW);
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFsNumber() {
        return fsNumber;
    }

    public void setFsNumber(String fsNumber) {
        this.fsNumber = fsNumber;
    }

    public String getLatestFetchFromGov() {
        return latestFetchFromGov;
    }

    public void setLatestFetchFromGov(String latestFetchFromGov) {
        this.latestFetchFromGov = latestFetchFromGov;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Set<String> getConsolidations() {
        return consolidations;
    }

    public void setConsolidations(Set<String> consolidations) {
        this.consolidations = consolidations;
    }

    public Set<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(Set<String> propositions) {
        this.propositions = propositions;
    }
}
