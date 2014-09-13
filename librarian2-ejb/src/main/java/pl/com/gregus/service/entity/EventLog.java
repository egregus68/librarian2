/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.service.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.dao.Identifiable;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.EventOperation;
import pl.com.gregus.enums.Groups;
import pl.com.gregus.service.to.EventLogTO;

/**
 *
 * @author Grzegorz Guściora
 */
@Entity
@Table(name = "L2_EVENT_LOG")
public class EventLog implements Identifiable<Long> {

    /**
     * Identyfikator systemowy
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "event_log_seq_gen")
    @SequenceGenerator(name = "event_log_seq_gen", sequenceName = "SQ_L2_EVENT_LOG", allocationSize = 1)
    private Long id;
    /**
     * Data/czas wystąpienie zdarzenia
     */
    @Column(name = "DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    /**
     * Obszar funkcjonalny
     */
    @Column(name = "EVENT_GROUP")
    @Enumerated(EnumType.STRING)
    private Groups group;
    /**
     * Funkcja realizowana w danym obszarze
     */
    @Column(name = "EVENT_FUNCTION")
    @Enumerated(EnumType.STRING)
    private EventFunction function;
    /**
     * Operacja realizowana w ramach funkcji
     */
    @Column(name = "EVENT_OPERATION")
    @Enumerated(EnumType.STRING)
    private EventOperation operation;
    /**
     * Miesiąc podstawy naliczania rezerwy okresu którego dotyczy funkcja. Pole
     * wypełnione wyłącznie dla zdarzeń dotyczących okresu rezerwy obowiązkowej.
     */
    @Column(name = "MONTH_BASE")
    @Temporal(TemporalType.DATE)
    private Date monthBase;
    /**
     * Wyróżnik podmiotu, którego dotyczy funkcja. Pole wypełnione wyłącznie dla
     * zdarzeń dotyczących pojedynczego podmiotu rezerwy
     */
    @Column(name = "DISCRIMINANT")
    private String institutionDiscriminant;
    /**
     * Login użytkownika, który wykonał operację lub wartość „System”, jeśli
     * zdarzenie zostało wykonane przez system.
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;
    /**
     * Opis zdarzenia (początek opisu w przypadku długiego opisu, zawierającego
     * np. parametry zestawienia).
     */
    @Column(name = "DESCRIPTION")
    private String describe;

    public EventLog() {
        super();
    }

    public EventLog(Groups group, EventFunction function, EventOperation operation, Date monthBase, String institutionDiscriminant, User createdBy, String describe) {
        super();
        this.group = group;
        this.function = function;
        this.operation = operation;
        this.monthBase = monthBase;
        this.institutionDiscriminant = institutionDiscriminant;
        this.createdBy = createdBy;
        this.describe = describe;
    }


    public EventLog(EventLogTO to) {
        super();
        id = to.getId();
        dateTime = to.getDateTime();
        group = to.getGroup();
        function = to.getFunction();
        operation = to.getOperation();
        monthBase = to.getMonthBase();
        institutionDiscriminant = to.getInstitutionDiscriminant();
        if (to.getCreatedBy() != null) {
            createdBy = new User(to.getCreatedBy());
        }
        describe = to.getDescribe();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isIdSet() {
        return null != id;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public EventLogTO createTO() {
        EventLogTO to = new EventLogTO(group, function, operation, monthBase, institutionDiscriminant, createdBy == null ? null : createdBy.createTO(), describe);
        to.setId(id);
        to.setDateTime(dateTime);
        return to;
    }
}
