package lawscraper.server.entities.law;

import lawscraper.server.entities.superclasses.NameId;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/2/12
 * Time: 8:11 PM
 */
@Entity
@Table(name = "proposition")
public class Proposition extends NameId {
    private Law laws;

    public Proposition(String value) {
        this.setName(value);
    }

    public Proposition() {
    }
}
