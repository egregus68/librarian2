/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.criteria;

import java.io.Serializable;
import pl.com.gregus.dao.utils.FilterSortTO;

/**
 * Klasa obsługująca kryteria wyszukiwania.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2014-03-03 09:00:00
 */
public class BaseSearchCriteria implements Serializable {

    /**
     * Porządek sortowania kolumn.
     */
    protected FilterSortTO filtrSort = new FilterSortTO();
    /**
     * Numer paczki do pobrania.
     */
    protected int firstResult;
    /**
     * Rozmiar paczki do pobrania.
     */
    protected int maxResults;
    private boolean distinct;

    public int getFirstResult() {
        return firstResult;
    }

    public BaseSearchCriteria() {
        super();
        distinct = false;
//        setFirstResult(0);
//        setFitrSort(null);
//        setMaxResults(-1);
    }

    public final void setFirstResult(final int value) {
        this.firstResult = value;
    }

    public final int getMaxResults() {
        return maxResults;
    }

    public final void setMaxResults(final int value) {
        this.maxResults = value;
    }

    public FilterSortTO getFiltrSort() {
        return filtrSort;
    }

    public void setFiltrSort(FilterSortTO filtrSort) {
        this.filtrSort = filtrSort;
    }

    public void clear() {
        setFirstResult(0);
        setFiltrSort(new FilterSortTO());
        setMaxResults(-1);
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
}
