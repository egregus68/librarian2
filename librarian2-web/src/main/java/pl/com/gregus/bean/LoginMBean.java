package pl.com.gregus.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.admin.dao.UsersFacade;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.bean.helper.BeanHelper;
import pl.com.gregus.enums.SessionKey;
import pl.com.gregus.jsf.util.JSFUtils;

/**
 * Obsługa logowania
 *
 * @author Grzegorz Guściora
 */
@ManagedBean (name = "loginBean")
@ViewScoped
public class LoginMBean implements Serializable {

    private static final long serialVersionUID = 23423523456234L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginMBean.class);
    private static final String LOGIN_SUCCESS = "scheduledTask";
    private static final String LOGIN_BAD_CREDENTIALS = "login.badCredentials";
    private static final String LOGIN_NO_RULES = "login.noRules";
    public static final String USER_SESSION = "USER_SESSION";
    private UsersInfoTO user;
//    private FormAuthenticator authenticator;

    @EJB
    private UsersFacade usersFacade;
    /**
     * Nazwa użytkownika.
     */
    private String username;
    /**
     * Wprowadzone hasło użytkownika.
     */
    private String password;

////    @PostConstruct
////    public void init() {
////        authenticator = usersFacade.getFormAuthenticator();
////    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Logowanie do aplikacji.
     *
     * @return redirectPage
     */
//    public final String login() {
//        //spradzamy autentykacje z LDAP'em
//        if (authenticate()) {
//            user = usersFacade.getUserByLogin(username);
//            if (null != user) {
//                user.setLastLogonDate(new Date());
//                usersFacade.login(user);
//                LOGGER.info("Login action OK by {}", username);
//                return LOGIN_SUCCESS;
//            } else {
//                JSFUtils.addErrorMessage(LOGIN_NO_RULES);
//                return null;
//            }
//        }
//        JSFUtils.addErrorMessage(LOGIN_BAD_CREDENTIALS);
//        LOGGER.info("Login action ERROR by {}", username);
//        return null;
//    }
    
    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            String navigateString = "";
            request.login(username, password);
            Principal principal = request.getUserPrincipal();
            if (request.isUserInRole("ADMIN")) {
                navigateString = "/pages/home.xhtml";
            } else if (request.isUserInRole("Manager")) {
                navigateString = "/manager/ManagerHome.xhtml";
            } else if (request.isUserInRole("User")) {
                navigateString = "/user/UserHome.xhtml";
            }
            if (!navigateString.isEmpty()) {
                try {
                    user = usersFacade.getUserByLogin(username);

                    user.setLastLogonDate(new Date());
//                    user.setPassword(password);                    
                    usersFacade.login(user);
                    
    
                    //LOGGER.info("User ("+getUsername()+") loging in #" + DateUtility.getCurrentDateTime(), request.getUserPrincipal().getName());                    
                    LOGGER.info("Login action OK by {}", username);
                    context.getExternalContext().redirect(request.getContextPath() + navigateString);
                } catch (IOException ex) {
                    LOGGER.error("IOException, Login Controller" + "Username : " + principal.getName(), ex);
                    //context.addMessage(null, new FacesMessage("Błąd!", "Wystąpił wyjątek"));
                    JSFUtils.addErrorMessage(LOGIN_NO_RULES);
                }
        }
        } catch (ServletException e) {
            LOGGER.error( "Błąd! Nazwa użytkownika i/lub hasło są złe.");
            //context.addMessage(null, new FacesMessage("Błąd!", "Nazwa użytkownika i/lub hasło są złe."));
            JSFUtils.addErrorMessage(LOGIN_BAD_CREDENTIALS);
        }
    }

    private boolean authenticate() {
        if (authenticateInContainer(username, password)) {
            addLoggedUserToSession();
            addLastUserActionTime();
            //add login user to session object
            FacesContext context = FacesContext.getCurrentInstance();
            final ExternalContext extCon = context.getExternalContext();
            final HttpSession session = (HttpSession) extCon.getSession(false);
            session.setAttribute(USER_SESSION, user);
            return true;
        }
        return false;
    }

    /**
     * Autentykacja w kontenerze web i ejb.
     *
     * @param loginx
     * @param passwd
     * @return
     */
    private boolean authenticateInContainer(String login, String passwd) {
        //logowanie do web i jaas
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        // Where request is the httpservletrequest object.
//        if (authenticator.authenticate(login, passwd, request, response)) {
//            LOGGER.info("uzytkownik zalogowany poprawnie.");
//            return true;
//        }
        return false;
    }

    /**
     * Wylogowanie z aplikacji.
     *
     * @return redirectPage
     */
    public final String logout() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        try {
            // Where request is the httpservletrequest object.
//            authenticator.logout(request, response);
            user = usersFacade.getUserByLogin(BeanHelper.getCurrentUser());
            usersFacade.logout(user);

//        } catch (LoginException ex) {
//            java.util.logging.Logger.getLogger(LoginMBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        request.getSession(false).invalidate();
        return "login";
    }

    public void reset() {
        username = "";
        password = "";
    }

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
    }

    public UsersInfoTO getUser() {
        return user;
    }

    public void setUser(UsersInfoTO user) {
        this.user = user;
    }

    private void addLoggedUserToSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        sessionMap.put(SessionKey.USER.name(), user);
    }

    /**
     * Dodanie czasu ostaneij akcji.
     */
    private void addLastUserActionTime() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.setAttribute(SessionKey.LAST_ACTION_TIMESTAMP.name(), (Long) System.currentTimeMillis());
        }
    }

    public String getCurrentUser() {
        return BeanHelper.getCurrentUser();
    }

    public boolean isUserInRole(String role) {
        List<String> roleList = new ArrayList<>();
        roleList.add(role);
        return BeanHelper.isUserInRole(roleList);
    }

}
