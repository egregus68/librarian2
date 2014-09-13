/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.gregus.dao.utils;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:00:30
 */
public class FilterIntegerTO extends AbstractFilterTO {
    private Object value;

    public static FilterIntegerTO valueOf(String viewId, String field, String comparator, Integer value) {
        FilterIntegerTO newItem = new FilterIntegerTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public Integer getIntValue() {
        Object val = getValue();

        if (val instanceof String) {
            int nVal = 0;

            try {
                nVal = Integer.parseInt((String) val);
            } catch (NumberFormatException exc) {
                // ignore
                nVal = 0;
            }

            return  Integer.valueOf(nVal);
        } else if (val instanceof Integer) {
            return (Integer) val;
        } 
        
        return null;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null ) {
            return;
        }


        if ((value instanceof String) || (value instanceof Integer)) {
            this.value = value;
        } 
    }

    @Override
    public String toString() {
        String result = "(FilterIntegerTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}

