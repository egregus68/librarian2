package pl.com.gregus.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * .
 * @author Grzegorz Guściora
 */
public class MultipleFilesResourceBundleControl extends ResourceBundle.Control implements Serializable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleFilesResourceBundleControl.class);
    /**
     * bundleExtension.
     */
    private final String bundleExtension;

    /**
     * .
     * @param bundleExtension
     */
    public MultipleFilesResourceBundleControl(String bundleExtension) {
        super();
        this.bundleExtension = bundleExtension;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, bundleExtension);

        ClassLoader localLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> it = localLoader.getResources(resourceName);

        ResourceBundle bundle = null;

        List<URL> list = Collections.list(it);

        //sortowanie resource, resource pochodzący z głównego archiwum jest na samym dole, reszta ma kolejność przypadkową
        Collections.sort(list, new ResourceBundleURLComparator(localLoader.getResource(resourceName)));

        //przenoszenie listy URL na listę InputStream
        byte[] newLine = System.getProperty("line.separator").getBytes();
        URL url;
        URLConnection connection;
        List<InputStream> inputStream = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            url = list.get(i);
            LOGGER.info("****************************** Get content from file : {}", url);
            connection = url.openConnection();
            if (connection != null) {
                connection.setUseCaches(false);
                if (i > 0) {
                    //dodanie nowej lini po kadym pliku
                    inputStream.add(new ByteArrayInputStream(newLine));
                }
                inputStream.add(connection.getInputStream());
            }
        }
        if (0 < inputStream.size()) {
            //składanie listy InoutStream w sekwencję
            try (InputStream stream = new SequenceInputStream(Collections.enumeration(inputStream))) {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
                if (null != stream) {
                    stream.close();
                }
            }
        }
        return bundle;
    }

}
