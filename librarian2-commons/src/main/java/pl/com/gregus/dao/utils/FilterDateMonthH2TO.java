/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

import java.util.Date;

/**
 * Do testowania FilterDateMonthTO z uwagi na baze H2 w testach.
 *
 * @author Grzegorz Guściora
 */
public class FilterDateMonthH2TO extends AbstractFilterTO {

    private static final long serialVersionUID = 3500661220218622651L;
    private Object value;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value) {
        return valueOf(viewId, field, comparator, value, null);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value, String primaryKey) {
        FilterDateMonthH2TO newItem = new FilterDateMonthH2TO();
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
        String result = "(FilterDateMonthH2TO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
