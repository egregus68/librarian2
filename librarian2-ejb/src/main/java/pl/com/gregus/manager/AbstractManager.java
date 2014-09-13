package pl.com.gregus.manager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.com.gregus.dao.Identifiable;

/**
 * Implementacja CRUD.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <E>
 * @param <PK>
 * @created 2013-05-22 14:26:00
 */
public abstract class AbstractManager<E extends Identifiable<PK>, PK extends Long> extends AbstractManagerTemplete<E , PK > {

    @PersistenceContext(unitName = "librarian2PU")
    protected EntityManager entityManager;
    /**
     * The persistent class.
     */
  
    /**
     * The persistent class.
     * @return
     */
    @Override
    public EntityManager getEntityManager() { 
       return entityManager;
    }
    
}
