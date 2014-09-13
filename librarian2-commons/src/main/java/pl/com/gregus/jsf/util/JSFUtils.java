package pl.com.gregus.jsf.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSFUtils.
 *
 * @author Grzegorz Guściora
 */
public class JSFUtils {

    private static final String title;
    private static final Logger LOGGER = LoggerFactory.getLogger(JSFUtils.class);

    static {
        title = getMessage("msg_title", getContext());
    }

    public static void addInfoMessage(final String key, final Object... params) {
        String message = getMessage(key, params);
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, message));
    }

    public static void addErrorMessage(final String key, final Object... params) {
        String message = getMessage(key, params);
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }

    /**
     * Pobranie wartosci dla klucza z pliku messages.properties
     *
     * @param key
     * @param params
     * @return
     */
    static public String getMessage(String key, final Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("pl.com.gregus.resources.MultipleFilesResourceBundle");
            String result;

            if (bundle.containsKey(key)) {
                result = bundle.getString(key);
            } else {
                for (Object p : params) {
                    key += " " + p;
                }
                result = key;
            }

            if (params != null) {
                result = MessageFormat.format(result, params);
            }

            return result;
        } catch (Exception ex) {
            LOGGER.error("Get Bundle error: {}", ex.getMessage());
            return key;
        }
    }

    /**
     * Uzyskiwanie adresu IP.
     *
     * @return Adres IP
     */
    public static String getIPAddress() {
        HttpServletRequest request = (HttpServletRequest) getContext().getExternalContext().getRequest();
        return request.getRemoteAddr();
    }

    private static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public static boolean inSet(String value, String set) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String[] values = set.split(",");
        for (String v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T getSubmittedValue(String componentId) {
        UIInput input = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
        if (input != null) {
            return (T) input.getSubmittedValue();
        }
        return null;
    }
}
