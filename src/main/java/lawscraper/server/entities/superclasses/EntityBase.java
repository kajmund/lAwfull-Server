package lawscraper.server.entities.superclasses;

import org.springframework.data.neo4j.annotation.GraphId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 10:41 PM
 */
@MappedSuperclass
public class EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @GraphId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
