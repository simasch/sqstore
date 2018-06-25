package store.common.control.repository;

import store.common.entity.JpaEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base class for all repositories. Provides commonly used methods.
 *
 * @param <T>  Entity type
 * @param <ID> Id type
 */
public abstract class JpaRepository<T extends JpaEntity, ID> {

    /**
     * We need the type to avoid passing the type in every method
     */
    private final Class<T> entityType;

    /**
     * To get access to the {@link EntityManager} we use {@link PersistenceContext} annotation.
     * <p>
     * If there is only one persistence unit (e.g. persistence.xml) we don't need to declare a name.
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Primary constructor.
     *
     * @param entityType
     */
    protected JpaRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    /**
     * Returns the number of entities available.
     *
     * @return number of entities
     */
    public long count() {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        Root<T> root = query.from(entityType);
        query.select(em.getCriteriaBuilder().count(root));
        return em.createQuery(query).getSingleResult();
    }

    /**
     * Deletes one entity.
     *
     * @param entity
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     */
    public void delete(T entity) {
        em.remove(entity);
    }

    /**
     * Deletes an entity by id.
     *
     * @param id
     * @throws IllegalArgumentException if the entity with the given id does not exist
     */
    public void deleteById(ID id) {
        delete(findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("No %s entity with id %s exists!", entityType, id))));
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     */
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(entityType, id));
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    public List<T> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityType);

        Root<T> root = query.from(entityType);
        query.select(root);

        return em.createQuery(query).getResultList();
    }

    /**
     * Synchronize the persistence context to the underlying database.
     */
    public void flush() {
        em.flush();
    }

    /**
     * Either insert or update the passed customer.
     * <p>
     * It's important to distinguish between persist and merge.
     * Especially if you want to change the customer after save because merge will return a proxy object of the
     * entity and you MUST use that one. Never use the object that you pass to merge!
     *
     * @param entity
     * @return S
     */
    public <S extends T> S save(S entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    /**
     * Saves and returns a collection of entities
     *
     * @param entities
     * @param <S>
     * @return
     */
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }
}
