package pl.com.gregus.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.admin.dao.UsersFacade;
import pl.com.gregus.admin.model.EventLogLM;
import pl.com.gregus.admin.to.EventLogSC;
import pl.com.gregus.bean.helper.AbstractListBean;
import pl.com.gregus.bean.helper.BeanHelper;
import pl.com.gregus.date.utils.DateUtils;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.Groups;
import pl.com.gregus.service.dao.EventLogFacade;
import pl.com.gregus.service.to.EventLogTO;

/**
 *
 * @author Grzegorz Guściora
 */
@ViewScoped
@ManagedBean(name = "eventLogBean")
public class EventLogMBean extends AbstractListBean<EventLogSC> implements Serializable {

    @EJB
    private transient EventLogFacade service;
//    @EJB
//    private transient InstitutionFacade institutionService;
//    @EJB
//    private transient BasePeriodFacade basePeriodService;
    @EJB
    private transient UsersFacade usersService;
    private EventLogTO eventLogSelected;
    private List<String> institutionDiscriminants;
    private Groups[] groups = Groups.values();
    private EventFunction[] functions = EventFunction.values();
//    private List<BasePeriodTO> monthBases;
    private LazyDataModel modelLazy;
    private List<String> createdBy;
    private final Collection<Object> selectedReportData = new ArrayList<>();
    private final Map<String, Object> selectedReportParameters = new HashMap();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogMBean.class);

    @PostConstruct
    void init() {
        LOGGER.info("EventLogBean.init");
        LOGGER.debug("EventLogBean.init");
        LOGGER.warn("EventLogBean.init");
        LOGGER.error("EventLogBean.init");
        criteria = new EventLogSC();
        defaultCriteria();
        modelLazy = new EventLogLM(service, criteria);
    }

    public void reset() {
        criteria.clear();
        defaultCriteria();
    }

    private void defaultCriteria() {
        criteria.getFiltrSort().setSortField("dateTime");
        criteria.getFiltrSort().setSortOrder(SortOrder.DESCENDING);
        Calendar calTo = new GregorianCalendar();
        calTo.setTime(new Date());
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        criteria.setDateTimeEnd(calTo.getTime());
        Date lastWorkingDay = DateUtils.getLastWorkingDay(new Date());
        Calendar calFrom = new GregorianCalendar();
        calFrom.setTime(lastWorkingDay);
        calFrom.set(Calendar.HOUR_OF_DAY, 24);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.add(Calendar.DAY_OF_MONTH, -1);
        criteria.setDateTimeStart(calFrom.getTime());
    }

    public EventLogTO getEventLogSelected() {
        return eventLogSelected;
    }

    public void setEventLogSelected(EventLogTO eventLogSelected) {
        this.eventLogSelected = eventLogSelected;
    }

//    public List<String> getInstitutionDiscriminants() {
//        if (institutionDiscriminants == null || institutionDiscriminants.isEmpty()) {
//            List<InstitutionTO> institutions = institutionService.getAll();
//            institutionDiscriminants = new ArrayList<>(institutions.size());
//            for (InstitutionTO inst : institutions) {
//                if (!institutionDiscriminants.contains(inst.getDiscriminant())) {
//                    institutionDiscriminants.add(inst.getDiscriminant());
//                }
//            }
//            Collections.sort(institutionDiscriminants);
//        }
//        return institutionDiscriminants;
//    }

//    public List<BasePeriodTO> getMonthBases() {
//        if (monthBases == null || monthBases.isEmpty()) {
//            BaseSearchCriteria bpCriteria = new BaseSearchCriteria();
//            bpCriteria.setFirstResult(0);
//            bpCriteria.setMaxResults(-1);
//            bpCriteria.getFiltrSort().setSortOrder(SortOrder.ASCENDING);
//            bpCriteria.getFiltrSort().setSortField("monthBaseOfReserve");
//            monthBases = basePeriodService.find(bpCriteria).getData();
//        }
//        return monthBases;
//    }

    public LazyDataModel getModelLazy() {
        return modelLazy;
    }

    public void setModelLazy(LazyDataModel modelLazy) {
        this.modelLazy = modelLazy;
    }

    private void prepareReportData() {
        selectedReportParameters.clear();
        prepareReportHeader();
        selectedReportData.clear();
        int maxResults = criteria.getMaxResults();
        int firstResult = criteria.getFirstResult();
        criteria.setMaxResults(-1);
        criteria.setFirstResult(0);
        List<EventLogTO> data = service.find(criteria).getData();
        for (EventLogTO log : data) {
            Map<String, Object> fields = new HashMap<>();
            fields.put("DATE_TIME", log.getDateTime());
            fields.put("GROUP", log.getGroup() == null ? null : BeanHelper.getMessage(log.getGroup().getMsgKey()));
            fields.put("FUNCTION", log.getFunction() == null ? null : BeanHelper.getMessage(log.getFunction().getMsgKey()));
            fields.put("OPERATION", log.getOperation() == null ? null : BeanHelper.getMessage(log.getOperation().getMsgKey()));
            fields.put("MONTH_BASE", log.getMonthBase());
            fields.put("DISCRIMINANT", log.getInstitutionDiscriminant());
            fields.put("CREATED_BY", log.getCreatedBy() == null ? null : log.getCreatedBy().getLogin());
            fields.put("DESCRIBE", log.getDescribe());
//            selectedReportData.add(new DataItem(fields, 1));
        }
        criteria.setMaxResults(maxResults);
        criteria.setFirstResult(firstResult);
    }

    private void prepareReportHeader() {
        StringBuilder headerDetails = new StringBuilder();
        if (criteria.getDateTimeStart() != null) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.dateTime"));
            headerDetails.append(" ");
            headerDetails.append(BeanHelper.getMessage("commons.period.from"));
            headerDetails.append(": ");
            headerDetails.append(DateUtils.formatDate(criteria.getDateTimeStart(), DateUtils.DATE_TIME_FORMAT_HH_MM));
            headerDetails.append("  ");
        }
        if (criteria.getDateTimeEnd() != null) {
            headerDetails.append(BeanHelper.getMessage("commons.period.to"));
            headerDetails.append(": ");
            headerDetails.append(DateUtils.formatDate(criteria.getDateTimeEnd(), DateUtils.DATE_TIME_FORMAT_HH_MM));
            headerDetails.append("  ");
        }
        if (criteria.getGroup() != null) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.group"));
            headerDetails.append(": ");
            headerDetails.append(BeanHelper.getMessage(criteria.getGroup().getMsgKey()));
            headerDetails.append("  ");
        }
        if (criteria.getFunction() != null) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.function"));
            headerDetails.append(": ");
            headerDetails.append(BeanHelper.getMessage(criteria.getFunction().getMsgKey()));
            headerDetails.append("  ");
        }
        if (criteria.getCreatedBy() != null) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.createdBy"));
            headerDetails.append(": ");
            headerDetails.append(criteria.getCreatedBy());
            headerDetails.append("  ");
        }
        if (criteria.getInstitutionDiscriminant() != null) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.institutionDiscriminant"));
            headerDetails.append(": ");
            headerDetails.append(criteria.getInstitutionDiscriminant());
            headerDetails.append("  ");
        }
//        if (criteria.getMonthBase() != null) {
//            headerDetails.append(BeanHelper.getMessage("admin.eventLog.monthBase"));
//            headerDetails.append(": ");
//            headerDetails.append(DateUtils.formatDate(criteria.getMonthBase().getMonthBaseOfReserve(), DateUtils.DATA_MONTH_FORMAT));
//            headerDetails.append("  ");
//        }
        if (criteria.getDescribe() != null && !criteria.getDescribe().isEmpty()) {
            headerDetails.append(BeanHelper.getMessage("admin.eventLog.describe"));
            headerDetails.append(": ");
            headerDetails.append(criteria.getDescribe());
            headerDetails.append("  ");
        }
        selectedReportParameters.put("headerDetails", headerDetails.toString());
    }

    public Collection<Object> getSelectedReportData() {
        prepareReportData();
        return selectedReportData;
    }

    public Map<String, Object> getSelectedReportParameters() {
        return selectedReportParameters;
    }

    public Collection<Object> getSelectedEventData() {
        selectedReportParameters.clear();
        Collection<Object> selectedEventData = new ArrayList<>(1);
        Map<String, Object> fields = new HashMap<>();
        fields.put("DATE_TIME", eventLogSelected.getDateTime());
        fields.put("GROUP", BeanHelper.getMessage(eventLogSelected.getGroup().getMsgKey()));
        fields.put("FUNCTION", BeanHelper.getMessage(eventLogSelected.getFunction().getMsgKey()));
        fields.put("OPERATION", BeanHelper.getMessage(eventLogSelected.getOperation().getMsgKey()));
        fields.put("MONTH_BASE", eventLogSelected.getMonthBase());
        fields.put("DISCRIMINANT", eventLogSelected.getInstitutionDiscriminant());
        fields.put("CREATED_BY", eventLogSelected.getCreatedBy() == null ? null : eventLogSelected.getCreatedBy().getLogin());
        fields.put("DESCRIBE", eventLogSelected.getDescribe());
//        selectedEventData.add(new DataItem(fields, 1));
        return selectedEventData;
    }

    public Map<String, Object> getSelectedEventParameters() {
        return selectedReportParameters;
    }

    public Groups[] getGroups() {
        return groups;
    }

    public void setGroups(Groups[] groups) {
        this.groups = groups;
    }

    public EventFunction[] getFunctions() {
        return functions;
    }

    public void setFunctions(EventFunction[] functions) {
        this.functions = functions;
    }

    public List<String> getCreatedBy() {
        if (createdBy == null || createdBy.isEmpty()) {
            createdBy = usersService.getLogins();
        }
        return createdBy;
    }

    public void setCreatedBy(List<String> createdBy) {
        this.createdBy = createdBy;
    }
}
