package lawscraper.server.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 10/1/12
 * Time: 8:09 AM
 */

@Scope("prototype")
@Repository
public class RepositoryBaseImpl<T> implements RepositoryBase<T> {
    @PersistenceContext
    private EntityManager entityManager;

    Class<T> entityClass;

    public RepositoryBaseImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public RepositoryBaseImpl() {
    }

    @Override
    public T save(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public T findOne(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public Set<T> findByPropertyValue(String property, String value) {
        Set<T> result;

        if (property == null || value == null) {
            return null;
        }

        String queryString = "select l from " + entityClass.getSimpleName() + " l where l." + property + " = ?1";

        Query query = entityManager.createQuery(queryString);
        query.setParameter(1, value);

        try {
            result = new HashSet<T>(query.getResultList());
        } catch (NoResultException e) {
            result = null;
        }
        return result;

    }

    @Override
    public Iterable<T> findAll() {
        Query query = entityManager.createQuery("select a from " + entityClass.getSimpleName() + " a");
        return new HashSet<T>(query.getResultList());
    }

    @Override
    public Iterable<T> findAllByQuery(String property, String queryRequest) {
        Set<T> result;

        if (property == null || queryRequest == null) {
            return null;
        }
        String queryString =
                "from " + entityClass.getSimpleName() + " l where l." + property + " like '" + queryRequest + "%'";

        Query query = entityManager
                .createQuery(
                        queryString);

        //query.setParameter(1, "'" + queryRequest + "'");

        System.out.println("Trying to get it!");
        result = new HashSet<T>(query.getResultList());

        System.out.println("Got it!!");
        return result;
    }

    @Override
    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
}
