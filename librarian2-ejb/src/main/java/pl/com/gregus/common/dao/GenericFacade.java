/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.common.dao;

import java.io.Serializable;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;

/**
 * Opis podstawowych operacji na danych -- LeazyDataModel.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <T>
 * @param <PK>
 * @created 2013-05-21 14:30:00
 */
public interface GenericFacade<T extends Identifiable<PK>, PK extends Long> extends Serializable {

    /**
     * find.
     *
     * @param criteria
     * @return
     */
    PagedList<T> find(BaseSearchCriteria criteria);

    /**
     * getById.
     *
     * @param Id
     * @return
     */
    T getById(PK Id);

    /**
     * deleteById.
     *
     * @param Id
     */
    void deleteById(PK Id);

    /**
     * save.
     *
     * @param to
     */
    void save(T to);
}
