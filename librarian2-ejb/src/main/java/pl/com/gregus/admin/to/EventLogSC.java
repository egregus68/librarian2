/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.to;

import java.util.Arrays;
import java.util.Date;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.utils.FilterDateTO;
import pl.com.gregus.dao.utils.FilterEnumTO;
import pl.com.gregus.dao.utils.FilterStringTO;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.EventOperation;
import pl.com.gregus.enums.Groups;


/**
 *
 * @author Grzegorz Guściora
 */
public class EventLogSC extends BaseSearchCriteria {

    private Date dateTimeStart;
    private Date dateTimeEnd;
    private Groups group;
    private EventFunction function;
    private EventOperation process;
//    private BasePeriodTO monthBase;
    private String institutionDiscriminant;
    private String createdBy;
    private String describe;

    @Override
    public void clear() {
        super.clear();
        dateTimeStart = null;
        dateTimeEnd = null;
        group = null;
        function = null;
        process = null;
//        monthBase = null;
        institutionDiscriminant = null;
        createdBy = null;
        describe = null;
    }

    public Date getDateTimeStart() {
        return dateTimeStart;
    }

    public Date getDateTimeEnd() {
        return dateTimeEnd;
    }

    public Groups getGroup() {
        return group;
    }

    public EventFunction getFunction() {
        return function;
    }

    public EventOperation getProcess() {
        return process;
    }

//    public BasePeriodTO getMonthBase() {
//        return monthBase;
//    }

    public String getInstitutionDiscriminant() {
        return institutionDiscriminant;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDescribe() {
        return describe;
    }

    public void setInstitutionDiscriminant(String institutionDiscriminant) {
        getFiltrSort().removeFilterByName("institutionDiscriminant", "=");
        getFiltrSort().getFilters().add(FilterStringTO.valueOf("", "institutionDiscriminant", "=", institutionDiscriminant));
        this.institutionDiscriminant = institutionDiscriminant;
    }

    public void setDateTimeStart(Date dateTimeStart) {
        getFiltrSort().removeFilterByName("dateTime", ">=");
        getFiltrSort().getFilters().add(FilterDateTO.valueOf("", "dateTime", ">=", dateTimeStart));
        this.dateTimeStart = dateTimeStart;
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        getFiltrSort().removeFilterByName("dateTime", "<=");
        getFiltrSort().getFilters().add(FilterDateTO.valueOf("", "dateTime", "<=", dateTimeEnd));
        this.dateTimeEnd = dateTimeEnd;
    }

    public void setGroup(Groups group) {
        getFiltrSort().removeFilterByName("group");
        getFiltrSort().getFilters().add(FilterEnumTO.valueOf("", "group", "IN", Arrays.asList(group)));
        this.group = group;
    }

    public void setFunction(EventFunction function) {
        getFiltrSort().removeFilterByName("function");
        getFiltrSort().getFilters().add(FilterEnumTO.valueOf("", "function", "IN", Arrays.asList(function)));
        this.function = function;
    }

    public void setProcess(EventOperation process) {
        getFiltrSort().removeFilterByName("process");
        getFiltrSort().getFilters().add(FilterEnumTO.valueOf("", "process", "IN", Arrays.asList(process)));
        this.process = process;
    }

//    public void setMonthBase(BasePeriodTO monthBase) {
//        getFiltrSort().removeFilterByName("monthBase");
//        if (monthBase != null) {
//            getFiltrSort().getFilters().add(FilterDateMonthTO.valueOf("", "monthBase", "=", (Date) monthBase.getMonthBaseOfReserve()));
//        }
//        this.monthBase = monthBase;
//    }

    public void setCreatedBy(String createdBy) {
        getFiltrSort().removeFilterByName("createdBy");
        getFiltrSort().getFilters().add(FilterStringTO.valueOf("", "createdBy.login", "=", createdBy));
        this.createdBy = createdBy;
    }

    public void setDescribe(String describe) {
        getFiltrSort().removeFilterByName("describe");
        if (describe != null && !describe.isEmpty()) {
            getFiltrSort().getFilters().add(FilterStringTO.valueOf("", "describe", "like", "%" + describe + "%"));
        }
        this.describe = describe;
    }
}
