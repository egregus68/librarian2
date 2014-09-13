package pl.com.gregus.bean.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.validator.ValidatorException;
import pl.com.gregus.resources.MultipleFilesResourceBundle;

/**
 * @author Grzegorz Guściora
 */
public class BeanHelper implements Serializable {

    private static final long serialVersionUID = 8629042153365508346L;

    public static String getCurrentUser() {
        if (FacesContext.getCurrentInstance() == null) {
// akcja jest robiona przez scheduler nie ma facesContextu, wiec user jest null- to znaczy systemowy
            return null;
        } else {
            String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            if (null == user) {
                user = "";
            }
            return user;
        }
    }

    public static boolean isUserInRole(List<String> roles) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        for (String role : roles) {
            if (externalContext.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUserInRole(String role) {
        List<String> roles = new ArrayList<>();
        roles.add(role);
        return isUserInRole(roles);
    }

    public static void addInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addWarningMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void addErrorMessage(String message, String clientId) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    public static void addErrorMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addErrorMessage(msg);
    }

    public static void addErrorMessageFromResource(String key, String clientId, Object... params) {
        final String msg = getMessage(key, params);
        addErrorMessage(msg, clientId);
    }

    public static void addInfoMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addInfoMessage(msg);
    }

    public static void addWarningMessageFromResource(String key, Object... params) {
        final String msg = getMessage(key, params);
        addWarningMessage(msg);
    }

    public static String getMessage(String key, Object... params) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        return MultipleFilesResourceBundle.getMessageResourceString(ResourceBundle.getBundle("messages"), key, params);
    }

    public static void throwValidatorError(String msgKey, Object... params) {
        final String errMsg = getMessage(msgKey, params);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
        throw new ValidatorException(facesMessage);
    }

    public static void throwConverterError(String msgKey, Object... params) throws ConverterException {
        final String errMsg = getMessage(msgKey, params);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, errMsg);
        throw new ConverterException(facesMessage);
    }

//    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list, boolean addSelect, EnumLabelResolver<T> labelResolver) {
//        List<SelectItem> result = new ArrayList<>();
//        if (addSelect) {
//            result.add(new SelectItem(null, getMessage("select")));
//        }
//        for (T obj : list) {
//            String label;
//            if (labelResolver != null) {
//                label = labelResolver.getLabel(obj);
//            } else {
//                label = getMessage(obj.toString());
//            }
//            result.add(new SelectItem(obj, label));
//        }
//        return result;
//    }
//    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list, boolean addSelect) {
//        return fillSelectList(list, addSelect, null);
//    }
//
//    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list) {
//        return fillSelectList(list, true, null);
//    }
//    public static <T extends Enum<T>> List<SelectItem> fillSelectList(T[] list, EnumLabelResolver<T> labelResolver) {
//        return fillSelectList(list, true, labelResolver);
//    }
    /**
     * Resetowanie komponentow klasy UIINput
     *
     * @param parent komponent nadrzedny
     */
    public static void refreshInputs(UIComponent parent) {
        if (null != parent) {
            if (parent instanceof UIInput) {
                ((UIInput) parent).resetValue();
            }
            Iterator<UIComponent> it = parent.getFacetsAndChildren();
            while (it.hasNext()) {
                UIComponent component = it.next();
                if (component.getChildren().size() > 0 || component.getFacetCount() > 0) {
                    refreshInputs(component);
                }
                if (component instanceof UIInput) {
                    ((UIInput) component).resetValue();
                }
            }
        }
    }

    /**
     * Resetowanie komponentow klasy UIINput
     *
     * @param parentId komponent nadrzedny
     */
    public static void refreshInputs(String parentId) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent parent = fc.getViewRoot().findComponent(parentId);
        refreshInputs(parent);
    }

    /**
     * Pobranie loginu z principala.
     *
     * @return
     */
    public static String getUserPrincipal() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.getUserPrincipal() == null ? "null" : externalContext.getUserPrincipal().toString();
    }
}
