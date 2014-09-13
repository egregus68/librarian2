package pl.com.gregus.dao.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.primefaces.model.SortOrder;

public class FilterSortTO implements Serializable {

    public enum SortFieldType {
        STRING,//tylko dla stringow, sortuje neizaleznie od wielkosci litery
        NORMAL,//normalny
        CASTSTRINGTOINT;//dla stringow bedacych int (sortuj String jak wartosci numeryczne)
    }
    private String primaryKey;
    private List<AbstractFilterTO> filters;
    private List<String> sortField;
    private SortOrder sortOrder = SortOrder.UNSORTED;
    private SortFieldType sortFieldType;

    public FilterSortTO() {
        sortFieldType = SortFieldType.NORMAL;
    }

    public void addFilter(AbstractFilterTO filterTO) {
        for (AbstractFilterTO abstractFilterTO : getFilters()) {
            if (abstractFilterTO.containsField(filterTO.getField())) {
                abstractFilterTO.setComparator(filterTO.getComparator());
                abstractFilterTO.setValue(filterTO.getValue());
                return;
            }
        }
        getFilters().add(filterTO);
    }

    public void removeFilter(AbstractFilterTO filterTO) {
        AbstractFilterTO removeFilter = null;
        for (AbstractFilterTO abstractFilterTO : getFilters()) {
            if (abstractFilterTO.containsField(filterTO.getField())) {
                removeFilter = abstractFilterTO;
                break;
            }
        }
        if (null != removeFilter) {
            getFilters().remove(removeFilter);
        }
    }

    public void removeFilterByName(String fieldName) {
        AbstractFilterTO removeFilter = null;
        for (AbstractFilterTO abstractFilterTO : getFilters()) {
            if (abstractFilterTO.getField().contains(fieldName)) {
                removeFilter = abstractFilterTO;
                break;
            }
        }
        if (null != removeFilter) {
            getFilters().remove(removeFilter);
        }
    }

    public void removeFilterByName(String fieldName, String comparator) {
        AbstractFilterTO removeFilter = null;
        for (AbstractFilterTO abstractFilterTO : getFilters()) {
            if (abstractFilterTO.getField().contains(fieldName) && abstractFilterTO.getComparator().equalsIgnoreCase(comparator)) {
                removeFilter = abstractFilterTO;
                break;
            }
        }
        if (null != removeFilter) {
            getFilters().remove(removeFilter);
        }
    }

    public List<AbstractFilterTO> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        return filters;
    }

    public void setFilters(List<AbstractFilterTO> filters) {
        this.filters = filters;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<String> getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = new ArrayList<>();
        if (sortField.contains(".")) {
            String[] split = sortField.split("\\.");
            this.sortField = Arrays.asList(split);
        } else {
            this.sortField.add(sortField);
        }
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrderSQL() {
        switch (sortOrder) {
            case ASCENDING:
                return "ASC";
            case DESCENDING:
                return "DESC";
            default:
                return "";
        }
    }

    public SortFieldType getSortFieldType() {
        return sortFieldType;
    }

    public void setSortFieldType(SortFieldType sortFieldType) {
        this.sortFieldType = sortFieldType;
    }
}
