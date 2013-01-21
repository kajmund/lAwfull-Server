package lawscraper.server.entities.superclasses;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 1/2/12
 * Time: 10:41 PM
 */
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public class EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
