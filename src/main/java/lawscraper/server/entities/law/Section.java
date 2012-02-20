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
 * Time: 8:32 PM
 */

@NodeEntity
public class Section extends EntityBase{
    String sectionKey;
    String sectionNo;
    String sectionText = "";

    @RelatedTo(type = "sectionlistitem", elementClass = SectionListItem.class, direction = Direction.OUTGOING)
    List<SectionListItem> sectionListItems;

    public Section() {

    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public String getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public List<SectionListItem> getSectionListItems() {
        return sectionListItems;
    }

    public void setSectionListItems(List<SectionListItem> sectionListItems) {
        this.sectionListItems = sectionListItems;
    }
}
