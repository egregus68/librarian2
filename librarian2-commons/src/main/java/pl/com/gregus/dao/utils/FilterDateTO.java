/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:00:10
 */
public class FilterDateTO extends AbstractFilterTO {

    private Object value;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, Date value) {
        FilterDateTO newItem = new FilterDateTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);
        return newItem;
    }

    public static AbstractFilterTO valueOf(String viewId, List<String> field, String comparator, Date value) {
        FilterDateTO newItem = new FilterDateTO();
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
        String result = "(FilterDateTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
