package pl.com.gregus.resources;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * MultipleFilesResourceBundle.
 *
 * @author Grzegorz Guściora
 */
public class MultipleFilesResourceBundle extends ResourceBundle implements Serializable {

    //private static final Logger LOGGER = LoggerFactory.getLogger(MultipleFilesResourceBundle.class);
    protected static final String BUNDLE_NAME = "messages";
    protected static final String BUNDLE_EXTENSION = "properties";
    protected static final Control MRF_CONTROL = new MultipleFilesResourceBundleControl(BUNDLE_EXTENSION);

    /**
     * Konstruktor bez parametrowy.
     */
    public MultipleFilesResourceBundle() {
        this(BUNDLE_NAME);
    }

    /**
     * Konstruktor parametrowy.
     *
     * @param bundleName .
     */
    public MultipleFilesResourceBundle(final String bundleName) {
        super();
        ResourceBundle newParent = ResourceBundle.getBundle(bundleName, MRF_CONTROL);
        setParent(newParent);
    }

    /**
     * Pobieranie włąsciwego tłumaczenia etykiety.
     *
     * @param key klucz etykiety.
     * @return tłumaczenie etykiety.
     */
    @Override
    protected Object handleGetObject(final String key) {
        return (null != key && null != parent) ? parent.getObject(key) : "";
    }

    /**
     * Pobieranie listy etykiet.
     *
     * @return lista etykiet.
     */
    @Override
    public Enumeration getKeys() {
        return parent.getKeys();
    }

    /**
     * Pobieranie message z paramterami.
     *
     * @param bundle
     * @param key message key.
     * @param params params message.
     * @return String message.
     */
    public static String getMessageResourceString(final ResourceBundle bundle, final String key, final Object params[]) {
        String text;
        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }
        if (params != null && params.length > 0) {
            MessageFormat mf = new MessageFormat(text);
            text = mf.format(params, new StringBuffer(), null).toString();
        }
        return text;
    }
}
