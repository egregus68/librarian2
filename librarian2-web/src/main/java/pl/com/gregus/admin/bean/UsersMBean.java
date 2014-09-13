/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.admin.dao.UserGroupFacade;
import pl.com.gregus.admin.dao.UsersFacade;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.admin.model.UsersAdminLM;
import pl.com.gregus.admin.to.UserGroupTO;
import pl.com.gregus.admin.to.UserInfoSC;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.bean.helper.BeanHelper;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.EventOperation;
import pl.com.gregus.enums.Groups;
import pl.com.gregus.service.dao.EventLogFacade;
import pl.com.gregus.service.to.EventLogTO;


/**
 *
 * @author Grzegorz Guściora
 */
@ViewScoped
@ManagedBean(name = "usersBean")
public class UsersMBean implements Serializable {

    @EJB
    private UsersFacade service;
    @EJB
    private UserGroupFacade userGroupFacade;
    @EJB
    private transient EventLogFacade evantLogFacade;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersMBean.class);
    private LazyDataModel modelLazy;
    private String user;
    private List<String> grant;// = Arrays.asList("Administrator", "Kontrolujący", "Użytkownik DSP", "Monitorujący");
    private List<UserGroupTO> availableGroups;
    private String selectedGrant;
    private List<User.STATUS> status = Arrays.asList(User.STATUS.values());
    private User.STATUS selectedStatus;
    private final Map<UserGroupTO, Boolean> checked = new HashMap<>();
    private UserInfoSC criteria;
    private UsersInfoTO newUser;
    private Map<UserGroupTO, Boolean> newUserGroups;
    private final Collection<Object> selectedReportData = new ArrayList<>();
    private final Map<String, Object> selectedReportParameters = new HashMap<>();
    private String errorMessage = "admin.user.error.useralready";

    @PostConstruct
    void init() {
        criteria = new UserInfoSC();
        modelLazy = new UsersAdminLM(service, criteria);
        availableGroups = userGroupFacade.findAll();
        grant = new ArrayList<>(availableGroups.size());
        newUserGroups = new HashMap<>();
        for (UserGroupTO group : availableGroups) {
            grant.add(group.getName());
            newUserGroups.put(group, false);
        }
        newUser = new UsersInfoTO();
        find();
    }

    public void reset() {
        user = null;
        selectedGrant = null;
        selectedStatus = null;
        find();
    }

    public String message(User.STATUS status) {
        return status.getValue();
    }

    public String message(String statusName) {
        return User.STATUS.valueOf(statusName).getValue();
    }

    public void add() {
        UsersInfoTO userByLogin = service.getUserByLogin(newUser.getLogin());
        if (userByLogin != null) {
            errorMessage = "admin.user.error.useralready";
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("errorInfo");
            requestContext.execute("errorDialog.show()");
            return;
        }
//        if (!service.checkIfUserInDomain(newUser.getLogin())) {
//            errorMessage = "admin.user.error.nouser";
//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.update("errorInfo");
//            requestContext.execute("errorDialog.show()");
//            return;
//        }
//        Map<String, String> userDetails = service.getUserDetails(newUser.getLogin());
//        for (Map.Entry<String, String> detail : userDetails.entrySet()) {
//            switch (detail.getKey()) {
//                case LdapAttribute.GIVEN_NAME:
//                    newUser.setFirstName(detail.getValue());
//                    break;
//                case LdapAttribute.SN:
//                    newUser.setLastName(detail.getValue());
//                    break;
//                case LdapAttribute.MAIL:
//                    newUser.setEmail(detail.getValue());
//            }
//        }
        List<UserGroupTO> newGroups = new ArrayList<>();
        for (Map.Entry<UserGroupTO, Boolean> grupa : newUserGroups.entrySet()) {
            // You would think that .getValue() of the entry is Boolean?
            // You wold be so much wrong!
            // It's a String!
            // System.out.println("Value class is " + ((Object) grupa.getValue()).getClass() + ", and is \"" + grupa.getValue() + "\"");
            if (grupa.getValue() != null && grupa.getValue()) {
                newGroups.add(grupa.getKey());
            }
        }
        newUser.setGroups(newGroups);
        newUser.setStatus(User.STATUS.ACTIVE);
        service.save(newUser);
        newUser = new UsersInfoTO();
        resetNewUserGroups();
    }

    public void modify() {
        LOGGER.debug("modify");
        List<UserGroupTO> newGroups = new ArrayList<>();
        for (Map.Entry<UserGroupTO, Boolean> grupa : newUserGroups.entrySet()) {
            // You would think that .getValue() of the entry is Boolean?
            // You wold be so much wrong!
            // It's a String!
            // System.out.println("Value class is " + ((Object) grupa.getValue()).getClass() + ", and is \"" + grupa.getValue() + "\"");
            if (grupa.getValue() != null && grupa.getValue()) {
                newGroups.add(grupa.getKey());
            }
        }
        newUser.setGroups(newGroups);
        newUser.setStatus(User.STATUS.ACTIVE);
        service.save(newUser);
        UsersInfoTO createdBy = service.getUserByLogin(BeanHelper.getCurrentUser());
        evantLogFacade.logEvent(new EventLogTO(Groups.admin, EventFunction.USERS, EventOperation.MODIFY, null, null, createdBy, "Użytkownik: " + newUser.getLogin()));
        newUser = new UsersInfoTO();
        resetNewUserGroups();
    }

    public void activate() {
        service.activate(newUser);
        //UsersInfo createdBy = service.getUserByLogin(BeanHelper.getCurrentUser());
        //evantLogFacade.logEvent(new EventLogTO(Groups.admin, EventFunction.USERS, EventOperation.MODIFY, null, null, createdBy, "Aktywacja użytkownika: " + newUser.getLogin()));
        newUser = new UsersInfoTO();
        resetNewUserGroups();
    }

    public void deActivate() {
        service.deactivate(newUser);
        //UsersInfo createdBy = service.getUserByLogin(BeanHelper.getCurrentUser());
        //evantLogFacade.logEvent(new EventLogTO(Groups.admin, EventFunction.USERS, EventOperation.MODIFY, null, null, createdBy, "Blokowanie użytkownika: " + newUser.getLogin()));
        newUser = new UsersInfoTO();
        resetNewUserGroups();
    }

    public void delete(Long userID) {
        service.deleteById(userID);
        UsersInfoTO createdBy = service.getUserByLogin(BeanHelper.getCurrentUser());
        evantLogFacade.logEvent(new EventLogTO(Groups.admin, EventFunction.USERS, EventOperation.REMOVE, null, null, createdBy, "Użytkownik: " + newUser.getLogin()));
    }

    public void print() {
    }

    public Map<UserGroupTO, Boolean> getChecked() {
        return checked;
    }

    public String getSelectedGrant() {
        return selectedGrant;
    }

    public void setSelectedGrant(String selectedGrant) {
        this.selectedGrant = selectedGrant;
    }

    public void selectedUser(UsersInfoTO selectedUser) {
        if (selectedUser == null) {
            newUser = new UsersInfoTO();
            resetNewUserGroups();
        } else {
            newUser = selectedUser;
            for (Map.Entry<UserGroupTO, Boolean> g : newUserGroups.entrySet()) {
                if (newUser.getGroups().contains(g.getKey())) {
                    g.setValue(Boolean.TRUE);
                } else {
                    g.setValue(Boolean.FALSE);
                }
            }
        }

//        this.user = selectedUser.getLogin();
//        List<UserGroupTO> selectedGrants;
//        selectedGrants = selectedUser.getGroups();
//        //this.selectedStatus = selectedUser.getStatus();
//        checked.clear();
//        for (UserGroupTO grant : selectedGrants) {
//            checked.put(grant, true);
//        }
    }

    public UsersFacade getService() {
        return service;
    }

    public void setService(UsersFacade service) {
        this.service = service;
    }

    public LazyDataModel getModelLazy() {
        return modelLazy;
    }

    public void setModelLazy(LazyDataModel modelLazy) {
        this.modelLazy = modelLazy;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getGrant() {
        return grant;
    }

    public void setGrant(List<String> grant) {
        this.grant = grant;
    }

    public List<User.STATUS> getStatus() {
        return status;
    }

    public void setStatus(List<User.STATUS> status) {
        this.status = status;
    }

    public User.STATUS getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(User.STATUS selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public void find() {
        criteria.clear();
        if (user != null && !user.isEmpty()) {
            criteria.setLogin(String.format("%%%s%%", user));
        }
        if (selectedStatus != null) {
            criteria.setStatus(selectedStatus);
        }
        if (selectedGrant != null && !selectedGrant.isEmpty()) {
            criteria.setGroup(selectedGrant);
        }
        criteria.getFiltrSort().setSortField("login");
        criteria.getFiltrSort().setSortOrder(SortOrder.ASCENDING);

        prepareReportParameters();
    }

    public List<UserGroupTO> getAvailableGroups() {
        return availableGroups;
    }

    public UsersInfoTO getNewUser() {
        return newUser;
    }

    public void setNewUser(UsersInfoTO newUser) {
        this.newUser = newUser;
    }

    public UserInfoSC getCriteria() {
        return criteria;
    }

    public void setCriteria(UserInfoSC criteria) {
        this.criteria = criteria;
    }

    public Map<UserGroupTO, Boolean> getNewUserGroups() {
        return newUserGroups;
    }

    public void resetNewUserGroups() {
        for (Map.Entry<UserGroupTO, Boolean> g : newUserGroups.entrySet()) {
            g.setValue(Boolean.FALSE);
        }
    }

    public Collection<Object> getSelectedReportData() {
        return selectedReportData;
    }

    public Map<String, Object> getSelectedReportParameters() {
        return selectedReportParameters;
    }

    public Collection<Object> prepareReportData() {
        selectedReportData.clear();
        int maxResults = criteria.getMaxResults();
        int firstResult = criteria.getFirstResult();
        criteria.setMaxResults(-1);
        criteria.setFirstResult(0);
        List<UsersInfoTO> data = service.find(criteria).getData();
        for (UsersInfoTO usersInfo : data) {
            Map<String, Object> fields = new HashMap<>();
            fields.put("LOGIN", usersInfo.getLogin());
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (UserGroupTO g : usersInfo.getGroups()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(g.getDescription());
            }
            fields.put("GROUPS", sb.toString());
            fields.put("STATUS", BeanHelper.getMessage(usersInfo.getStatus().getValue()));
//            selectedReportData.add(new DataItem(fields, 1));
        }
        criteria.setMaxResults(maxResults);
        criteria.setFirstResult(firstResult);

        return selectedReportData;
    }

    public void prepareReportParameters() {
        selectedReportParameters.clear();
        //selectedReportParameters.put("createdBy", BeanHelper.getCurrentUser());
        if (user != null && !user.isEmpty()){
            selectedReportParameters.put("criteriaUser", "Użytkownik: " + user);
        }
        if (selectedStatus != null) {
            selectedReportParameters.put("criteriaStatus", "Status: " + BeanHelper.getMessage(selectedStatus.toString()));
        }
        if (selectedGrant != null) {
            for (UserGroupTO group : availableGroups) {
                if (group.getName().equals(selectedGrant)) {
                    selectedReportParameters.put("criteriaGroup", "Uprawnienia: " + group.getDescription());
                }
            }
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isLoggedUser(String login) {
        return BeanHelper.getCurrentUser().equals(login);
    }
}
