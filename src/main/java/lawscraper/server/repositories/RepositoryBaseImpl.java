package lawscraper.server.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
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
    public T findOne(String value) {
        String queryString = "select l from " + entityClass.getSimpleName() + " l where l." + "documentKey" + " = ?1";

        Query query = entityManager.createQuery(queryString);
        query.setParameter(1, value);

        HashSet<T> result;
        result = new HashSet<T>(query.getResultList());
        try {
            return result.iterator().next();

        } catch (NoSuchElementException e) {
            return null;
        }
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

        System.out.println(result.size() + ":Got it!!");
        return result;
    }

    @Override
    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Iterable<T> findByPropertyValues(List<String> properties, List<String> values) {
        Set<T> result;

        if (properties == null || values == null) {
            return null;
        }
        String queryString =
                "from " + entityClass.getSimpleName() + " l where";

        int i = 0;
        for (String property : properties) {
            queryString += " l." + property + " = '" + values.get(i) + "'";
            if (i < properties.size() - 1) {
                queryString += " and ";
            }
            i++;
        }

        Query query = entityManager
                .createQuery(
                        queryString);

        //query.setParameter(1, "'" + queryRequest + "'");

        System.out.println("Trying to get it!");
        result = new HashSet<T>(query.getResultList());

        System.out.println(result.size() + ": Got it!!");
        return result;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
}
