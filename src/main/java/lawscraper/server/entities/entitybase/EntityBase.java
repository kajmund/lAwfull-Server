package lawscraper.server.entities.entitybase;

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

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }
}
