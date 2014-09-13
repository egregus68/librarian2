/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.service.to;

import java.io.Serializable;
import java.util.Date;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.EventOperation;
import pl.com.gregus.enums.Groups;

/**
 *
 * @author Grzegorz Guściora
 */
public class EventLogTO implements Identifiable<Long>, Serializable {

    private Long id;
    private Date dateTime;
    private Groups group;
    private EventFunction function;
    private EventOperation operation;
    private Date monthBase;
    private String institutionDiscriminant;
    private UsersInfoTO createdBy;
    private String describe;

    public EventLogTO(Groups group, EventFunction function, EventOperation operation, Date monthBase, String institutionDiscriminant, UsersInfoTO createdBy, String describe) {
        super();
        this.group = group;
        this.function = function;
        this.operation = operation;
        this.monthBase = monthBase;
        this.institutionDiscriminant = institutionDiscriminant;
        this.createdBy = createdBy;
        this.describe = describe;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public EventFunction getFunction() {
        return function;
    }

    public void setFunction(EventFunction function) {
        this.function = function;
    }

    public EventOperation getOperation() {
        return operation;
    }

    public void setOperation(EventOperation operation) {
        this.operation = operation;
    }

    public Date getMonthBase() {
        return monthBase;
    }

    public void setMonthBase(Date monthBase) {
        this.monthBase = monthBase;
    }

    public String getInstitutionDiscriminant() {
        return institutionDiscriminant;
    }

    public void setInstitutionDiscriminant(String institutionDiscriminant) {
        this.institutionDiscriminant = institutionDiscriminant;
    }

    public UsersInfoTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UsersInfoTO createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public boolean isIdSet() {
        return null != id;
    }
}
