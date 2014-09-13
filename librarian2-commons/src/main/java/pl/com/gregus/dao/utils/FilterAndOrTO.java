/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

import java.util.List;
import pl.com.gregus.exception.LibrarianRuntimeException;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2014-03-03 09:00:00
 */
public class FilterAndOrTO extends AbstractFilterTO {

    private AbstractFilterTO[] value;

    public static AbstractFilterTO valueOf(String field, String comparator, AbstractFilterTO[] value) {
        FilterAndOrTO newItem = new FilterAndOrTO();
        newItem.setField(field);
        newItem.setValue(value);
        newItem.setComparator(comparator);

        return newItem;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof List) {
            try {
                this.value = (AbstractFilterTO[]) value;
            } catch (Exception ex) {
                throw new LibrarianRuntimeException(ex);
            }
        }
    }

    public void setValue(AbstractFilterTO[] value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = "(FilterOrTO) {";
        Boolean showComparator = false;

        if (value != null) {
            for (AbstractFilterTO filter : value) {
                result = result + filter.toString();

                if (showComparator) {
                    result = result + " " + getComparator() + " ";
                }

                showComparator = true;
            }
        }

        result = result + "}";

        return result;
    }
}
