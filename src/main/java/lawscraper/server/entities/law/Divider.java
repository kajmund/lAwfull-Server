package lawscraper.server.entities.law;

import lawscraper.server.entities.entitybase.EntityBase;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/22/11
 * Time: 6:49 AM
 */
@NodeEntity
public class Divider extends EntityBase{

    String key;

    String headline;
    @RelatedTo(type = "chapter", elementClass = Chapter.class, direction = Direction.OUTGOING)
    private List<Chapter> chapters;


    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
