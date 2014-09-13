/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.service.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;
import pl.com.gregus.service.entity.EventLog;
import pl.com.gregus.service.manager.EventLogManager;
import pl.com.gregus.service.to.EventLogTO;

@Stateless
@Local(EventLogFacade.class)
public class EventLogFacadeImpl implements EventLogFacade, Serializable {

    @EJB
    private transient EventLogManager eventLogManager;

    public EventLogFacadeImpl() throws ParseException {
        super();
    }

    public PagedList<EventLogTO> find(BaseSearchCriteria criteria) {
        PagedList<EventLog> found = eventLogManager.find(criteria);
        PagedList<EventLogTO> toReturn = new PagedList<>();
        toReturn.setPageNumber(found.getPageNumber());
        toReturn.setRecordCounter(found.getRecordCounter());
        toReturn.setData(new ArrayList<EventLogTO>(found.getData().size()));
        for (EventLog entity : found.getData()) {
            toReturn.getData().add(entity.createTO());
        }
        return toReturn;
    }

    public EventLogTO getById(Long Id) {
        return eventLogManager.getById(Id).createTO();
    }

    public void deleteById(Long Id) {
        throw new UnsupportedOperationException("Why would one delete an event log entry?");
    }

    @Deprecated
    public void save(EventLogTO to) {
        throw new UnsupportedOperationException("Use logEvent or addEvent instead.");
    }

    //@TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void logEvent(EventLogTO event) {
        eventLogManager.logEvent(new EventLog(event));
    }

    //@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void addEvent(EventLogTO event) {
        eventLogManager.addEvent(new EventLog(event));
    }
}
