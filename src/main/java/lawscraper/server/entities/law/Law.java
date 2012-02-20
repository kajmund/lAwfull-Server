package lawscraper.server.entities.law;

import lawscraper.server.entities.entitybase.EntityBase;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 7:47 PM
 */

@NodeEntity
public class Law extends EntityBase{
    String title;
    String fsNumber;
    String latestFetchFromGov;
    String releaseDate;
    String publisher;
    String creator;

    List<String> consolidations = new ArrayList<String>();
    List<String> propositions = new ArrayList<String>();

    @Fetch
    @RelatedTo(type = "chapter", elementClass = Chapter.class, direction = Direction.OUTGOING)
    List<Chapter> chapters;

    //used if no chapters exists
    @RelatedTo(type = "paragraph", elementClass = Paragraph.class, direction = Direction.OUTGOING)
    List<Paragraph> paragraphs;

    //used with inkomstskattelagen for example
    @RelatedTo(type = "divider", elementClass = Divider.class, direction = Direction.OUTGOING)
    List<Divider> dividers;

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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Divider> getDividers() {
        return dividers;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void setDividers(List<Divider> dividers) {
        this.dividers = dividers;
    }
}
