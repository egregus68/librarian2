package pl.com.gregus.enums;

import java.util.Arrays;

/**
 *
 * @author Grzegorz Guściora
 */
public enum Groups {

    monitor("commons.groups.monitor",
    new EventFunction[]{
        EventFunction.DAILY_COLLATION,
        EventFunction.TIME_SERIES_HANDLING,
        EventFunction.ANNUAL_COLLATION_HANDLING
    }),
    admin("commons.groups.admin",
    new EventFunction[]{
        EventFunction.RO_PERIOD,
        EventFunction.RESERVE_SUBJECT,
        EventFunction.EXCHANGE_RATES,
        EventFunction.RESERVE_RATE,
        EventFunction.LOMBARD_RATE,
        EventFunction.USERS,
        EventFunction.BIS_IMPORT,
        EventFunction.FINREP_IMPORT,
        EventFunction.ZSK_KG_IMPORT,
        EventFunction.AUTHORIZATION
    }),
    control("commons.groups.control",
    new EventFunction[]{
        EventFunction.FINAL_RECORDS_SET,
        EventFunction.SUBJECT_DATA_STATUS,
        EventFunction.PD000_STATUS,
        EventFunction.DECLARATIONS,
        EventFunction.FOREIGN_FUNDS_AGREEMENTS,
        EventFunction.COMPARATIVE_REPORTS,
        EventFunction.CONSOLIDATED_COLLATION,
        EventFunction.NIK_COLLATION,
        EventFunction.TIME_SERIES,
        EventFunction.PORTAL_SET_IMPORT,
        EventFunction.SUBJECT_COMMUNICATION
    });
    private final String msgKey;
    private final EventFunction[] functions;

    public String getMsgKey() {
        return msgKey;
    }

    public EventFunction[] getFunctions() {
        return functions;
    }

    private Groups(String msgKey, EventFunction[] functions) {
        this.msgKey = msgKey;
        this.functions = functions;
        Arrays.sort(this.functions);
    }
}
