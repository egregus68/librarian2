/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

import java.util.List;

/**
 *
 * @author Grzegorz Guściora
 */
public class FilterEnumTO extends AbstractFilterTO {

    private Object value;

    public static FilterEnumTO valueOf(String viewId, String field, String comparator, List values) {
        FilterEnumTO enumTO = new FilterEnumTO();
        enumTO.setViewId(viewId);
        enumTO.setField(field);
        enumTO.setValue(values);
        enumTO.setComparator(comparator);
        return enumTO;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }

        if ((value instanceof List)) {
            List valRet = (List) value;
            if (valRet.size() > 0 && valRet.get(0) != null) {
                this.value = value;
            }
        }
    }

    @Override
    public Object getValue() {
        return value;
    }
}
