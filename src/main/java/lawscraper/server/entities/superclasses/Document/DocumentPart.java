package lawscraper.server.entities.superclasses.Document;

import lawscraper.server.entities.superclasses.EntityBase;

import javax.persistence.MappedSuperclass;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/21/12
 * Time: 9:48 AM
 */
@MappedSuperclass
public class DocumentPart extends EntityBase {
    int order;
    String type;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
