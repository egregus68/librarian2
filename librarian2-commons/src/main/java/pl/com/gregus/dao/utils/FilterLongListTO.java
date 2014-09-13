/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.gregus.dao.utils;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:05:40
 */
@XmlRootElement(name="FilterLongListTO")
public class FilterLongListTO extends AbstractFilterTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5722634332339766052L;
	private Object value;

    public static FilterLongListTO valueOf(String viewId, String field, String comparator, List value) {
    	FilterLongListTO newItem = new FilterLongListTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

 

    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null ) {
            return;
        }


        if ((value instanceof List)) {
            this.value = value;
        } 
    }

    @Override
    public String toString() {
        String result = "(FilterIntegerTO) " + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
