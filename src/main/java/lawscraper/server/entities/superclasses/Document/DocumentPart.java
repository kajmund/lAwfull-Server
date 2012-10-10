package lawscraper.server.entities.superclasses.Document;

import lawscraper.server.entities.superclasses.EntityBase;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/21/12
 * Time: 9:48 AM
 */

@MappedSuperclass
public class DocumentPart extends EntityBase {
    private Integer listOrder = 0;
    private String type;
    private String documentKey;

    @Column(nullable = false, length = 20, unique = true)
    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }


    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        if (listOrder == null) {
            this.listOrder = 0;
        } else {
            this.listOrder = listOrder;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
