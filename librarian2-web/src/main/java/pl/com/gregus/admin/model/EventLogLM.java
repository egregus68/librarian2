/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.model;

import pl.com.gregus.admin.to.EventLogSC;
import pl.com.gregus.common.model.AbstractLazyDataModel;
import pl.com.gregus.service.dao.EventLogFacade;
import pl.com.gregus.service.to.EventLogTO;


/**
 *
 * @author Grzegorz Guściora
 */
public class EventLogLM extends AbstractLazyDataModel<EventLogTO, Long, EventLogSC> {

    public EventLogLM(EventLogFacade service) {
        super(service, new EventLogSC());
    }
    
    public EventLogLM(EventLogFacade service, EventLogSC criteria) {
        super(service, criteria);
    }
}