package pl.com.gregus.common.model;

import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import pl.com.gregus.common.dao.GenericFacade;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;
import pl.com.gregus.dao.utils.FilterSortTO;

/**
 * Klasa obsługująca pobieranie rekordów.
 *
 * @param <T> Identifiable<PK>.
 * @param <PK> PK.
 * @param <S> BaseSearchCriteria.
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-05-21 22:05:59
 */
public abstract class AbstractLazyDataModel<T extends Identifiable<PK>, PK extends Long, S extends BaseSearchCriteria> extends LazyDataModel<T> {

    private static final long serialVersionUID = -27891531159872027L;
    private GenericFacade<T, PK> service;
    private S bsc;
    /**
     * Nazwa pola, po którym są sortowane dane.
     */
    private String sortField;
    /**
     * Porzadek sortowania danych.
     */
    private SortOrder sortOrder = SortOrder.UNSORTED;

    public AbstractLazyDataModel(GenericFacade<T, PK> service, S criteria) {
        super();
        this.service = service;
        bsc = criteria;
    }

    /**
     * GetRowData impl.
     *
     * @param rowKey
     * @return
     */
    @Override
    public T getRowData(String rowKey) {
        return service.getById((PK) Long.valueOf(rowKey));
    }

    /**
     * GetRowKey impl.
     *
     * @param report
     * @return
     */
    @Override
    public Object getRowKey(T report) {
        return report.getId();
    }

    /**
     * Load impl.
     *
     * @param first .
     * @param pageSize .
     * @param sortField .
     * @param sortOrder .
     * @param filters .
     * @return .
     */
    @Override
    public List<T> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, String> filters) {
        bsc.setFirstResult(first);
        bsc.setMaxResults(pageSize);

        if (sortField != null) {
            bsc.getFiltrSort().setSortField(sortField);
            bsc.getFiltrSort().setSortOrder(sortOrder);
            
            if (sortField.contains("discriminant") || sortField.contains("associationNo")) {
                bsc.getFiltrSort().setSortFieldType(FilterSortTO.SortFieldType.CASTSTRINGTOINT);
            } else if (sortField.contains("shortName") || sortField.contains("fullName")) {
                bsc.getFiltrSort().setSortFieldType(FilterSortTO.SortFieldType.STRING);
            } else {
                bsc.getFiltrSort().setSortFieldType(FilterSortTO.SortFieldType.NORMAL);
            }

        }
        PagedList<T> result = service.find(bsc);
        setRowCount(result.getRecordCounter());
        return result.getData();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    /**
     * getService.
     *
     * @return
     */
    public GenericFacade<T, PK> getService() {
        return service;
    }
}
