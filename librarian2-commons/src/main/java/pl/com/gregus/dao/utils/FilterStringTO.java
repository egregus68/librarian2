/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.dao.utils;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-06-13 08:04:44
 */
public class FilterStringTO extends AbstractFilterTO {

    private Object value;
    private Boolean ignoreLetterCase;

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value) {
        return valueOf(viewId, field, comparator, value, null, false);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value, Boolean ignoreLetterCase) {
        return valueOf(viewId, field, comparator, value, null, ignoreLetterCase);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value, String primaryKey) {
        return valueOf(viewId, field, comparator, value, primaryKey, Boolean.FALSE);
    }

    public static AbstractFilterTO valueOf(String viewId, String field, String comparator, String value, String primaryKey, Boolean ignoreLetterCase) {
        FilterStringTO newItem = new FilterStringTO();
        newItem.setPrimaryKey(primaryKey);
        newItem.setViewId(viewId);
        newItem.setField(field);
        newItem.setComparator(comparator);
        newItem.setValue(value);
        newItem.setIgnoreLetterCase(ignoreLetterCase);

        return newItem;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    // Piotr Andrzejewski 27.11.2013 - w przypadku ignorowania wielkości liter zamieniamy na duże litery
    @Override
    public Object getValue() {
        return value;
    }

    public Boolean isIgnoreLetterCase() {
        return ignoreLetterCase;
    }

    public void setIgnoreLetterCase(Boolean ignoreLetterCase) {
        this.ignoreLetterCase = ignoreLetterCase;
    }

    @Override
    public String toString() {
        String result = "(FilterString" + (ignoreLetterCase ? " IGNORE LETTER CASE " : "") + ")" + getField() + " ( " + getComparator() + ") '" + getValue() + "'";

        return result;
    }
}
