/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.bean.helper;

import org.primefaces.model.SortOrder;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @param <C> BaseSearchCriteria
 * @created 2014-03-03 09:00:00
 */
public abstract class AbstractListBean<C extends BaseSearchCriteria> {

    protected C criteria;
    protected String sortField;
    protected SortOrder sortOrder;

    public C getCriteria() {
        return criteria;
    }

    public void setCriteria(C criteria) {
        this.criteria = criteria;
    }

    public String getSortField() {
        if (null != criteria) {
            return criteria.getFiltrSort().getSortField().get(0);
        } else {
            return null;
        }
    }

    public void setSortField(String sortField) {
        if (null != criteria) {
            criteria.getFiltrSort().getSortField().add(sortField);
        }
    }

    public SortOrder getSortOrder() {
        if (null != criteria) {
            return criteria.getFiltrSort().getSortOrder();
        }
        return null;
    }

    public void setSortOrder(SortOrder sortOrder) {
        if (null != criteria) {
            criteria.getFiltrSort().setSortOrder(sortOrder);
        }
    }

}
