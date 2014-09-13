/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.service.manager;

import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.com.gregus.manager.AbstractManager;
import pl.com.gregus.service.entity.EventLog;

/**
 *
 * @author Grzegorz Guściora
 */
@Stateless
public class EventLogManager extends AbstractManager<EventLog, Long> {

    @Deprecated
    public EventLog save(EventLog eventLog) {
        throw new UnsupportedOperationException("Use logEvent or addEvent instead.");
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void logEvent(EventLog eventLog) {
        addLogEvent(eventLog);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void addEvent(EventLog eventLog) {
        addLogEvent(eventLog);
    }

    private void addLogEvent(EventLog event) {
        if (event.getId() != null) {
            throw new IllegalArgumentException("Zdarzenie już zapisane w dzienniku");
        }
        event.setDateTime(new Date());
        super.save(event);
    }
}
