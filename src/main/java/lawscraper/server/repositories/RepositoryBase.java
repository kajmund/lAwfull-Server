package lawscraper.server.repositories;

import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/1/12
 * Time: 7:51 AM
 */
public interface RepositoryBase<T> {
    T save(T entity);
    T findOne(Long id);
    Set<T> findByPropertyValue(String property, String value);
    Iterable<T> findAll();
    Iterable<T> findAllByQuery(String description, String queryString);

    Class<T> getEntityClass();

    void setEntityClass(Class<T> entityClass);
}
