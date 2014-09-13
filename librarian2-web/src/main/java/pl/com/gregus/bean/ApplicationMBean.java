package pl.com.gregus.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.jar.Manifest;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.com.gregus.bean.helper.BeanHelper;
import pl.com.gregus.date.utils.DateUtils;

/**
 *
 * @author Grzegorz Guściora
 */
@ManagedBean(name = "applicationBean")
@ApplicationScoped
public class ApplicationMBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATATABLE_PAGINATOR_TEMPLATE = "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown} {CurrentPageReport} ";
    private static final String DATATABLE_ROWS_PER_TABLE_TEMPLATE = "5,10,15";
    private static final String DATATABLE_ROWS = "10";
    private static final String PAGINATOR_POSITION = "bottom";
    private static final String FACES_MESSAGES_FORM_ID = ":facesMessageSeverity:facesMessageForm:facesMessage";
    public String lastDataTableRows; //zapamietuje ostatni wybór
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMBean.class);
    private final transient Manifest manifest = new Manifest();
    private static final Calendar END_WORLD = new GregorianCalendar(9000, 11, 31);

    public String getDataTablePaginatorTemplate() {
        return DATATABLE_PAGINATOR_TEMPLATE;
    }

    /**
     * @return the currentpageReportTemplate
     */
    public String getCurrentpageReportTemplate() {
        return BeanHelper.getMessage("table.totalCount");
    }

    public String getDataTableRowsPerTableTemplate() {
        return DATATABLE_ROWS_PER_TABLE_TEMPLATE;
    }

    public String getPaginatorPosition() {
        return PAGINATOR_POSITION;
    }

    public String getDataTableRows() {
        return DATATABLE_ROWS;
    }

    public String getDateFormat() {
        return DateUtils.DATE_FORMAT;
    }

    public String getDateYearFormat() {
        return DateUtils.DATA_YEAR_FORMAT;
    }

    public String getMonthDateFormat() {
        return DateUtils.DATA_MONTH_FORMAT;
    }

    public String getDateTimeFormat() {
        return DateUtils.DATE_TIME_FORMAT;
    }

    public String getDateTimeWithoutSecondsFormat() {
        return DateUtils.DATE_TIME_FORMAT_HH_MM;
    }

    public String getTimeWithoutSecondsFormat() {
        return DateUtils.TIME_FORMAT_WITHOUT_SECONDS;
    }

    public TimeZone getTimeZone() {
        return DateUtils.TIMEZONE;
    }

    public String getTimeFormat() {
        return DateUtils.TIME_FORMAT;
    }

//    public Date getToday() {
//        return DateUtils.getDayBegin(new Date());
//    }
    public final String getDetailIcon() {
        return "ui-icon-clipboard";
    }

    public final String getPrintIcon() {
        return "ui-icon-print";
    }

    public final String getSignIcon() {
        return "ui-icon-pencil";
    }

    public final String getTransferIcon() {
        return "ui-icon-arrowthick-1-e";
    }

    public final String getAcceptIcon() {
        return "ui-icon-check";
    }

    public final String getRejectIcon() {
        return "ui-icon-closethick";
    }

    public final String getCancelIcon() {
        return "ui-icon-closethick";
    }

    public final String getDeleteIcon() {
        return "ui-icon-trash";
    }

    public final String getNewMoneyReceiptIcon() {
        return "ui-icon-document";
    }

    public final String getNewMoneySendIcon() {
        return "ui-icon-document";
    }

    public final String getNewOrderIcon() {
        return "ui-icon-document";
    }

    public final String getEditIcon() {
        return "ui-icon-gear";
    }

    public final String getFacesMessagesFormID() {
        return FACES_MESSAGES_FORM_ID;
    }

    public Date getDateToday() {
        return new Date();
    }

    public void setLastDataTableRows(String dataTableRows) {
        this.lastDataTableRows = dataTableRows;
    }

    public String getLastDataTableRows() {
        if (lastDataTableRows == null) {
            lastDataTableRows = getDataTableRows();
        }
        return lastDataTableRows;
    }

    public String getManifestKeyValue(String key) {
        return manifest.getMainAttributes().getValue(key);
    }

    public final boolean isSsoVersion() {
        String manifestKeyValue = getManifestKeyValue("SSO-Profile");
        return Boolean.valueOf(manifestKeyValue);
    }

    public Date isWorldEnd(Date date) {
        if (date != null) {
            if (date.before(END_WORLD.getTime())) {
                return date;
            }
        }
        return null;
    }

    public final String formatDate(Date date, String pattern) {
        return DateUtils.formatDate(date, pattern);
    }

    public Manifest loadManifest() {
        try {
            InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF");
            manifest.read(is);
        } catch (IOException ioe) {
            LOGGER.error("Unable to read the Manifest file from classpath", ioe);
        }
        return manifest;
    }

    public ApplicationMBean() {
    }

    @PostConstruct
    public void init() {
//        loadManifest();
    }
}
