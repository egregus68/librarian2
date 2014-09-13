/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.service.dao;

import pl.com.gregus.common.dao.GenericFacade;
import pl.com.gregus.service.to.EventLogTO;

/**
 *
 * @author Grzegorz Guściora
 */
public interface EventLogFacade extends GenericFacade<EventLogTO, Long> {

    public void logEvent(EventLogTO event);

    public void addEvent(EventLogTO event);

    @Override
    @Deprecated
    public void save(EventLogTO to);
}
