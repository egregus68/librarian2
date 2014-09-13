package pl.com.gregus.dao.utils;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:00:47
 */
public class FilterObjectTO extends AbstractFilterTO {

    private Object value;

    public static FilterObjectTO valueOf(String viewId, String field,
            String comparator, Object value) {
        FilterObjectTO newItem = new FilterObjectTO();

        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);

        return newItem;
    }

    public Object getValue() {
        return value;
    }
    Enum enum1;

    @Override
    public void setValue(Object value) {
        if (value == null) {
            return;
        }
        this.value = value;

    }

    @Override
    public String toString() {
        String result = "(FilterObjectTO) " + getField() + " ( "
                + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
