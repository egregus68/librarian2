package pl.com.gregus.manager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;
import pl.com.gregus.dao.util.DaoUtil;

/**
 * Implementacja CRUD.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <E>
 * @param <PK>
 * @created 2013-05-22 14:26:00
 */
public abstract class AbstractManagerTemplete<E extends Identifiable<PK>, PK extends Long> implements GenericManager<E, PK> {

    /**
     * The entity manager.
     */
//    @PersistenceContext(unitName = "librarian2")
//    protected EntityManager entityManager;
    @Resource
    private SessionContext context;
    /**
     * The persistent class.
     */
    private Class<E> persistentClass;
    /**
     * FindRefreshProperties.
     */
    private static Map<String, Object> findRefreshProperties = new HashMap<>();

    /**
     * Initializes dao.
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void initializeDAO() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        }

        findRefreshProperties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); // QueryHints.CACHE_RETRIEVE_MODE
        findRefreshProperties.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH); // QueryHints.CACHE_STORE_MODE

        //CacheRetrieveMode.BYPASS - cache is not used.
        //CacheRetrieveMode.USE - Read entity data from the cache: this is the default behavior.
        //CacheStoreMode.USE - new data in stored in the cache - but only for entity objects that are not in the cache already.
        //CacheStoreMode.REFRESH - new data is stored in the cache - refreshing entity objects that are already cached.
    }

    /**
     * Method getEntityManager, returns EntityManager.
     *
     * @return EntityManager
     */
    public abstract EntityManager getEntityManager();

    /**
     *
     * @param id
     * @return
     */
    @Override
    public E getById(PK id) {
        return getEntityManager().find(this.persistentClass, id, findRefreshProperties);
    }

    /**
     * Usuniecie encji po id.
     *
     * @param id
     */
    @Override
    public void deleteById(PK id) {
        E entity = getEntityManager().find(this.persistentClass, id);
        getEntityManager().remove(entity);
    }

    /**
     * Usuniecie encji.
     *
     * @param id
     */
    @Override
    public void delete(E entity) {
        if (!getEntityManager().contains(entity)) {
            entity = getEntityManager().merge(entity);
        }
        getEntityManager().remove(entity);
    }

    /**
     * Wyszukiwanie.
     *
     * @param entity
     * @param criteria
     * @return
     */
    @Override
    public PagedList<E> find(final BaseSearchCriteria criteria) {
        EntityManager eManager = getEntityManager();
        // dao utils
        DaoUtil<E> daoUtils = new DaoUtil<>();

        // create query
        final CriteriaQuery criteriaQuery = daoUtils.createWhere(criteria.getFiltrSort(), eManager.getCriteriaBuilder(), this.persistentClass);

        criteriaQuery.distinct(criteria.isDistinct());
        Query findQuery = eManager.createQuery(criteriaQuery);

        //count all
        final List resultAllList = findQuery.getResultList();

        //get page
        if (criteria.getMaxResults() > -1) {
            findQuery.setMaxResults(criteria.getMaxResults());
        }
        if (criteria.getFirstResult() > -1) {
            findQuery.setFirstResult(criteria.getFirstResult());
        }

        // Piotr Andrzejewski 28.11.2013 - w przypadku, gdy na aktualnie wybranej stronie nie znajdą się żadne rekordy wybieramy pierwszą stronę.
        if ((criteria.getMaxResults() > -1) && (criteria.getFirstResult() > -1)) {
            if (criteria.getFirstResult() > resultAllList.size()) {
                criteria.setFirstResult(0);
                findQuery.setFirstResult(criteria.getFirstResult());
            }
        }

        final List resultList = findQuery.getResultList();
        PagedList<E> result = new PagedList<>();
        result.setData(resultList);
        result.setPageNumber(criteria.getFirstResult());
        result.setRecordCounter(resultAllList.size());
        return result;
    }

    /**
     * Odświerzenie wartości enicji.
     *
     * @param entity
     * @return
     */
    @Override
    public E refresh(E entity) {
        if (getEntityManager().contains(entity)) {
            getEntityManager().refresh(entity);
            return entity;
        } else {
            Object id = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
            return getEntityManager().find(this.persistentClass, id, findRefreshProperties);
        }
    }

    /**
     * Zapis encji.
     *
     * @param entity E.
     */
    @Override
    public E save(final E entity) {
        E encja = getEntityManager().merge(entity);
        if (!context.getRollbackOnly()) {
            // flush() nie sprawdza czy transackja jest aktywna po tym jak wolene jest context.setRollbackOnly() i kazda operacja na encji powoduje exception
            getEntityManager().flush();
        }
        return encja;
    }

    /**
     * Zapis encji.
     *
     * @param entity E.
     */
    @Override
    public void save(final List<E> entity) {
        List<E> result = new ArrayList<>();
        for (E e : entity) {
            getEntityManager().merge(e);
        }
        getEntityManager().flush();
    }

    /**
     * Pobranie wszystkich encji.
     *
     * @return List<E>.
     */
    @Override
    public List<E> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
                .createQuery();
        cq.select(cq.from(this.persistentClass));

        return getEntityManager().createQuery(cq)
                .getResultList();
    }

}
