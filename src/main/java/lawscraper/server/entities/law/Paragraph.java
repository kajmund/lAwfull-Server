package lawscraper.server.entities.law;

import lawscraper.server.entities.entitybase.EntityBase;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 8:29 PM
 */

@NodeEntity
public class Paragraph extends EntityBase {
    String paragraphKey;
    String paragraphString;
    String paragraphNo;

    String isPartOf;

    @RelatedTo(type = "section", elementClass = Section.class, direction = Direction.OUTGOING)
    List<Section> sections;
    private boolean deprecated;

    public Paragraph() {
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getParagraphString() {
        return paragraphString;
    }

    public void setParagraphString(String paragraphString) {
        this.paragraphString = paragraphString;
    }

    public String getParagraphNo() {
        return paragraphNo;
    }

    public void setParagraphNo(String paragraphNo) {
        this.paragraphNo = paragraphNo;
    }

    public String getParagraphKey() {
        return paragraphKey;
    }

    public void setKey(String paragraphKey) {
        this.paragraphKey = paragraphKey;
    }

    public String getPartOf() {
        return isPartOf;
    }

    public void setPartOf(String partOf) {
        isPartOf = partOf;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public boolean isDeprecated() {
        return deprecated;
    }
}
