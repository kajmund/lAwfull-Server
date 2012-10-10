package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.superclasses.NameId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/2/12
 * Time: 9:29 PM
 */
@Entity
@Table(name = "caseLawSubject")
public class CaseLawSubject extends NameId {
    private CaseLaw caseLaw;

    @ManyToOne(optional = false)
    public CaseLaw getCaseLaw() {
        return caseLaw;
    }

    public void setCaseLaw(CaseLaw caseLaw) {
        this.caseLaw = caseLaw;
    }
}
