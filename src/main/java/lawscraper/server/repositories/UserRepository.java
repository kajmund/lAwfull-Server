package lawscraper.server.repositories;

import lawscraper.server.entities.user.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 11/13/11
 * Time: 11:16 AM
 */
@Repository("UserRepository")
public interface UserRepository extends GraphRepository<User>, NamedIndexRepository<User> {


}
