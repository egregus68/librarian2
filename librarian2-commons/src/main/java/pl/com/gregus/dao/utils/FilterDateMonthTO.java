/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

import java.util.Date;

/**
 *
 * @author Grzegorz Guściora
 */
public class FilterDateMonthTO extends AbstractFilterTO {

    private Object value;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value) {
        return valueOf(viewId, field, comparator, value, null);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value, String primaryKey) {
        FilterDateMonthTO newItem = new FilterDateMonthTO();
        newItem.setPrimaryKey(primaryKey);
        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);
        return newItem;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String result = "(FilterDateMonthTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
