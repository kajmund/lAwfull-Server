package lawscraper.server.entities.superclasses;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.io.Serializable;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/2/12
 * Time: 10:41 PM
 */
@NodeEntity
public class EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @GraphId
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
