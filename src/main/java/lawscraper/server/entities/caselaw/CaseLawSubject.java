package lawscraper.server.entities.caselaw;

import lawscraper.server.entities.superclasses.NameId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 10/2/12
 * Time: 9:29 PM
 */
@Entity
@Table(name = "caseLawSubject")
public class CaseLawSubject extends NameId {
    private CaseLaw caseLaw;

    public CaseLawSubject(String subject) {
        setName(subject);
    }

    public CaseLawSubject() {
    }

    @ManyToOne(optional = false)
    public CaseLaw getCaseLaw() {
        return caseLaw;
    }

    public void setCaseLaw(CaseLaw caseLaw) {
        this.caseLaw = caseLaw;
    }
}
