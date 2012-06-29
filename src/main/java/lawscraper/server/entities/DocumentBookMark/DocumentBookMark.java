package lawscraper.server.entities.documentbookmark;

import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.superclasses.EntityBase;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/17/12
 * Time: 9:55 AM
 */
public class DocumentBookMark extends EntityBase {
    @Fetch
    @RelatedTo(direction = Direction.INCOMING, type = "BM_LAW_PART")

    LawDocumentPart lawDocumentPart;
    String description;
    String title;

    public DocumentBookMark() {
    }

    public DocumentBookMark(LawDocumentPart lawDocumentPart, String title, String description) {
        this.lawDocumentPart = lawDocumentPart;
        this.title = title;
        this.description = description;
    }

    public Long getDocumentId() {
        return this.lawDocumentPart.getId();
    }

    public LawDocumentPart getLawDocumentPart() {
        return lawDocumentPart;
    }

    public void setLawDocumentPart(LawDocumentPart lawDocumentPart) {
        this.lawDocumentPart = lawDocumentPart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
