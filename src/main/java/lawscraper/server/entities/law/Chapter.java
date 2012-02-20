package lawscraper.server.entities.law;

import lawscraper.server.entities.entitybase.EntityBase;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 8:25 PM
 */

@NodeEntity
public class Chapter extends EntityBase{
    
    String key;
    String headLine;
    String number;

    @Fetch
    @RelatedTo(type = "paragraph", elementClass = Paragraph.class, direction = Direction.OUTGOING)
    List<Paragraph> paragraphs;

    public Chapter() {
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
