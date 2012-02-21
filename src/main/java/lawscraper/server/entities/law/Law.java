package lawscraper.server.entities.law;

import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 7:47 PM
 */

@NodeEntity
public class Law extends LawDocumentPart {
    String title;
    String fsNumber;
    String latestFetchFromGov;
    String releaseDate;
    String publisher;
    String creator;

    List<String> consolidations = new ArrayList<String>();
    List<String> propositions = new ArrayList<String>();

    public Law() {
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

    public List<String> getConsolidations() {
        return consolidations;
    }

    public void setConsolidations(List<String> consolidations) {
        this.consolidations = consolidations;
    }

    public List<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<String> propositions) {
        this.propositions = propositions;
    }
}
