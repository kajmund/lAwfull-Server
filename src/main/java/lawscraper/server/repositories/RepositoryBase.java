package lawscraper.server.repositories;

import java.util.List;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 10/1/12
 * Time: 7:51 AM
 */
public interface RepositoryBase<T> {
    T save(T entity);
    T findOne(Long id);
    T findOne(String id);
    Set<T> findByPropertyValue(String property, String value);
    Iterable<T> findAll();
    Iterable<T> findAllByQuery(String description, String queryString);

    Class<T> getEntityClass();

    void setEntityClass(Class<T> entityClass);

    Iterable<T> findByPropertyValues(List<String> properties, List<String> values);
}
