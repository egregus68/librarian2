package pl.com.gregus.admin.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.admin.manager.UserManager;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.bean.helper.BeanHelper;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;
import pl.com.gregus.enums.EventFunction;
import pl.com.gregus.enums.EventOperation;
import pl.com.gregus.enums.Groups;
import pl.com.gregus.service.entity.EventLog;
import pl.com.gregus.service.manager.EventLogManager;

/**
 *
 * @author Grzegorz Guściora
 */
@Stateless
@Local(UsersFacade.class)
public class UsersFacadeImpl implements UsersFacade, Serializable {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UsersFacadeImpl.class);
    @EJB
    private transient UserManager userManager;
    @EJB
    private transient EventLogManager eventLogManager;
    /**
     * Parametry systemowe.
     */
//    @EJB
//    private transient LdapConnector connector;

    public UsersFacadeImpl() {
        super();
    }

    @Override
    public UsersInfoTO getUserByLogin(String userName) {
        User user = userManager.getUserByLogin(userName);
        return user == null ? null : user.createTO();
    }

    /**
     * Metoda eksponowana poprzez zdalny interfejs, login i haslo sprawdzane w
     * bazie lub ldap
     *
     * @return
     */
//    @Override
//    public boolean isUsernamePasswordValid(String userName, String password) {
//        return checkUsernamePassword(userName, password) != null;
//    }

    @Override
    public PagedList<UsersInfoTO> find(BaseSearchCriteria criteria) {
        PagedList<User> found = userManager.find(criteria);
        PagedList<UsersInfoTO> toReturn = new PagedList<>();
        List<UsersInfoTO> list = new ArrayList<>(found.getRecordCounter());
        for (User user : found.getData()) {
            UsersInfoTO usersInfo = user.createTO();
            list.add(usersInfo);
        }
        toReturn.setData(list);
        toReturn.setPageNumber(found.getPageNumber());
        toReturn.setRecordCounter(found.getRecordCounter());
        return toReturn;
    }

    @Override
    public UsersInfoTO getById(Long Id) {
        return userManager.getById(Id).createTO();
    }

    @Override
    public void deleteById(Long Id) {
        userManager.deleteById(Id);
    }

    @Override
    public void save(UsersInfoTO to) {
        //TODO Jak najlepiej pobrac zalogowanego uzytkownika?
        User createdBy = userManager.getUserByLogin(BeanHelper.getCurrentUser());
        if (to.isIdSet()) {
            eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.MODIFY, null, null, createdBy, to.getLogin()));
        } else {
            eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.ADD, null, null, createdBy, to.getLogin()));
        }
        userManager.save(new User(to));
    }

    /**
     * Metoda eksponowana poprzez zdalny interfejs, login i haslo sprawdzane w
     * bazie lub ldap dodatkowo sychnronizacja roli z ldapa w momencie logowania
     *
     * @param userName
     * @param password
     */
//    private UserPrincipal checkUsernamePassword(String userName, String password) {
//        LOGGER.info("checkUsernamePassword - private");
//        UserPrincipal idmPrincipal = connector.checkUsernamePassword(userName, password);
//        return idmPrincipal;
//    }

//    @Override
//    public boolean checkIfUserInDomain(String login) {
//        return connector.checkIfUserInDomain(login);
//    }

//    @Override
//    public FormAuthenticator getFormAuthenticator() {
//        return new FormAuthenticatorImpl();
//    }

//    @Override
//    public Map<String, String> getUserDetails(String login) {
//        return connector.getUserDetails(login);
//    }

    @Override
    public List<String> getLogins() {
        List<User> findAll = userManager.findAll();
        List<String> logins = new ArrayList<>(findAll.size());
        for (User user : findAll) {
            logins.add(user.getLogin());
        }
        return logins;
    }

    public void activate(UsersInfoTO user) {
        user.setStatus(User.STATUS.ACTIVE);
        //TODO Jak najlepiej pobrac zalogowanego uzytkownika?
        User createdBy = userManager.getUserByLogin(BeanHelper.getCurrentUser());
        eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.ACTIVATE, null, null, createdBy, "Aktywacja użytkownika: " + user.getLogin()));
        userManager.save(new User(user));
    }

    @Override
    public void deactivate(UsersInfoTO user) {
        user.setStatus(User.STATUS.INACTIVE);
        //TODO Jak najlepiej pobrac zalogowanego uzytkownika?
        User createdBy = userManager.getUserByLogin(BeanHelper.getCurrentUser());
        eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.DEACTIVATE, null, null, createdBy, "Blokowanie użytkownika: " + user.getLogin()));
        userManager.save(new User(user));
    }

    @Override
    public void login(UsersInfoTO to) {
        LOGGER.info("UserFacade login: {}", to.getLogin());
        User createdBy = userManager.getUserByLogin(to.getLogin());
        LOGGER.info("UserFacade login createdBy: {}", createdBy);
        eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.LOGIN, null, null, createdBy, to.getLogin()));
        LOGGER.info("Login event added.");
        User user = new User(to);
        userManager.save(user);
    }

    @Override
    public void logout(UsersInfoTO to) {
        LOGGER.info("UserFacade logout...");
        if (null != to) {
            User createdBy = userManager.getUserByLogin(to.getLogin());
            LOGGER.info("UserFacade logout createdBy...", createdBy);
            eventLogManager.logEvent(new EventLog(Groups.admin, EventFunction.USERS, EventOperation.LOGOUT, null, null, createdBy, to.getLogin()));
            LOGGER.info("Logout event added.");
        }
    }

    }
