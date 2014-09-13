package pl.com.gregus.bean;

import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.resources.MultipleFilesResourceBundle;

/**
 * Inicjowanie zaczytania bundle'i każdego z dołączonych projektów do aplikacji.
 *
 * @author Grzegorz Guściora
 */
public class InitServlet extends HttpServlet {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitServlet.class);
    private final static String STARTUP_BUNDLE = "load.bundle";

    @Override
    public void init() {
        MultipleFilesResourceBundle mfrb = new MultipleFilesResourceBundle("messages");
        String loadBundle = mfrb.getString(STARTUP_BUNDLE);
        if (null != loadBundle && !loadBundle.contains("??")) {
            LOGGER.info(loadBundle);
        } else {
            LOGGER.error("----------------Nie załadowno plików message.proprties {} !!!!!----------------", loadBundle);
        }
    }
}
