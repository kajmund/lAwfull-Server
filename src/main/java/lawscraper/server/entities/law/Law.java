package lawscraper.server.entities.law;

import lawscraper.shared.DocumentPartType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 10/10/11
 * Time: 7:47 PM
 */
@Entity
@Table(name = "law")
public class Law extends LawDocumentPart {
    private String latestFetchFromGov = "";
    private String releaseDate = "";
    private String publisher = "";
    private String creator = "";

    public Law() {
        this.setLawPartType(DocumentPartType.LAW);
    }

    @Column(length = 255)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(length = 20)
    public String getLatestFetchFromGov() {
        return latestFetchFromGov;
    }

    public void setLatestFetchFromGov(String latestFetchFromGov) {
        this.latestFetchFromGov = latestFetchFromGov;
    }

    @Column(length = 20)
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(length = 255)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
