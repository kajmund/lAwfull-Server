package lawscraper.server.entities.superclasses;

import javax.persistence.MappedSuperclass;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/2/12
 * Time: 8:02 PM
 */
@MappedSuperclass
public class NameId extends EntityBase{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
