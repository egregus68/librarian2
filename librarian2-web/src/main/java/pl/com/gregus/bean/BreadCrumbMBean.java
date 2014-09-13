package pl.com.gregus.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasa obsługująca wyświetlanie ścieżki wyboru opcji w menu. 
 * @author Grzegorz Guściora
 */
@ManagedBean (name = "breadCrumbBean")
@SessionScoped
public class BreadCrumbMBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreadCrumbMBean.class);
    private List<String> pathItems = new ArrayList<String>();

    public boolean setPath(String path) {

        pathItems = new ArrayList<String>();
        if ("".equals(path) || path == null) {
            return true;
        }
        for (String item : path.split("\\.")) {
            pathItems.add(item);
        }
        return true;
    }
    
    
    public List<String> getPathItems() {
        return pathItems;
    }

    public void setPathItems(List<String> pathItems) {
        this.pathItems = pathItems;
    }

    public String returnToHome() {
        this.pathItems.clear();
        return "home";
    }
}