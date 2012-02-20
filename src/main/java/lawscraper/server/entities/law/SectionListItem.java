package lawscraper.server.entities.law;

import lawscraper.server.entities.entitybase.EntityBase;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/10/11
 * Time: 8:33 PM
 */

@NodeEntity
public class SectionListItem extends EntityBase{

    String listItemKey;
    String listItemString = "";

    public String getListItemString() {
        return listItemString;
    }

    public void setListItemString(String listItemString) {
        this.listItemString = listItemString;
    }

    public String getListItemKey() {
        return listItemKey;
    }

    public void setListItemKey(String listItemKey) {
        this.listItemKey = listItemKey;
    }
}
