package pl.com.gregus.manager;

import java.io.Serializable;
import java.util.List;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;

/**
 * Opis podstawowych operacji na danych -- CRUD.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <T>
 * @param <PK>
 * @created 2013-05-21 14:30:00
 */
public interface GenericManager<T extends Identifiable<PK>, PK extends Long> extends Serializable {

    /**
     * Return the persistent instance of {@link Person} with the given unique
     * property value username, or null if there is no such persistent instance.
     *
     * @param id the unique value
     * @return the corresponding {@link Person} persistent instance or null
     */
    T getById(PK Id);

    /**
     * Delete a T using the unique column.
     *
     * @param id PK
     */
    void deleteById(PK Id);

    /**
     * Delete Entity.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Refresh the passed entity with up to date data.
     *
     * @param entity the entity to refresh.
     */
    T refresh(T entity);

    /**
     * Save or update the passed entity.
     *
     * @param entity the entity to save or update.
     * @return
     */
    T save(T entity);

    /**
     * Save or update the passed entity.
     *
     * @param entity the entity to save or update.
     * @return
     */
    void save(List<T> entity);

    /**
     * Search.
     *
     * @param criteria
     * @return @link PagedList
     */
    PagedList<T> find(BaseSearchCriteria criteria);

    List<T> findAll();
}
