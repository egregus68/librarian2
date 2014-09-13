/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

/**
 * FilterLongTO.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:00:30
 */
public class FilterLongTO extends AbstractFilterTO {

    private Object value;

    public static FilterLongTO valueOf(String viewId, String field, String comparator, Long value) {
        FilterLongTO newItem = new FilterLongTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public Long getLongValue() {
        Object val = getValue();

        if (val instanceof String) {
            long nVal = 0;

            try {
                nVal = Long.parseLong((String) val);
            } catch (NumberFormatException exc) {
                // ignore
                nVal = 0;
            }

            return Long.valueOf(nVal);
        } else if (val instanceof Long) {
            return (Long) val;
        }

        return null;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }


        if ((value instanceof String) || (value instanceof Long)) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        String result = "(FilterIntegerTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
