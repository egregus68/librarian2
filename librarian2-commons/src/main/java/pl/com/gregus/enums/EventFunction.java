/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.enums;

import java.util.Arrays;

/**
 *
 * @author Grzegorz Guściora
 */
public enum EventFunction {

    RO_PERIOD("commons.function.ro_period",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY,
        EventOperation.CLOSE_PERIOD,
        EventOperation.BLOCK_PERIOD,
        EventOperation.ACTIVATE_PERIOD
    }),
    RESERVE_SUBJECT("commons.function.reserve_subject",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.REMOVE,
        EventOperation.CHANGE_VALID_DATE,
        EventOperation.ADD_DATA_VERSION,
        EventOperation.MODIFY_DATA_VERSION,
        EventOperation.REMOVE_DATA_VERSION
    }),
    EXCHANGE_RATES("commons.function.exchange_rates",
    new EventOperation[]{
        EventOperation.DATA_IMPORT
    }),
    RESERVE_RATE("commons.function.reserve_rate",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY,
        EventOperation.REMOVE
    }),
    LOMBARD_RATE("commons.function.lombard_rate",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY,
        EventOperation.REMOVE
    }),
    USERS("commons.function.users",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY,
        EventOperation.DEACTIVATE,
        EventOperation.ACTIVATE
    }),
    BIS_IMPORT("commons.function.bis_import",
    new EventOperation[]{
        EventOperation.DATA_IMPORT_EXECUTION
    }),
    FINREP_IMPORT("commons.function.finrep_import",
    new EventOperation[]{
        EventOperation.DATA_IMPORT_EXECUTION
    }),
    ZSK_KG_IMPORT("commons.function.zsk_kg_import",
    new EventOperation[]{
        EventOperation.DATA_IMPORT_EXECUTION
    }),
    AUTHORIZATION("commons.function.authorization",
    new EventOperation[]{
        EventOperation.LOGIN,
        EventOperation.LOGOUT
    }),
    FINAL_RECORDS_SET("commons.function.final_records_set",
    new EventOperation[]{
        EventOperation.ACTIVATE
    }),
    SUBJECT_DATA_STATUS("commons.function.subject_data_status",
    new EventOperation[]{
        EventOperation.MODIFY
    }),
    PD000_STATUS("commons.function.pd000_status",
    new EventOperation[]{
        EventOperation.MODIFY
    }),
    DECLARATIONS("commons.function.declarations",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY
    }),
    FOREIGN_FUNDS_AGREEMENTS("commons.function.foreign_funds_agreements",
    new EventOperation[]{
        EventOperation.ADD,
        EventOperation.MODIFY,
        EventOperation.REMOVE
    }),
    COMPARATIVE_REPORTS("commons.function.comparative_reports",
    new EventOperation[]{
        EventOperation.CREATE
    }),
    CONSOLIDATED_COLLATION("commons.function.consolidated_collation",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.ACCEPT,
        EventOperation.REMOVE,
        EventOperation.UPDATE,
        EventOperation.CREATE_RECIPIENT_FILE
    }),
    NIK_COLLATION("commons.function.nik_collation",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.ACCEPT,
        EventOperation.REMOVE
    }),
    TIME_SERIES("commons.function.time_series",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.REMOVE
    }),
    PORTAL_SET_IMPORT("commons.function.portal_set_import",
    new EventOperation[]{
        EventOperation.DATA_IMPORT_EXECUTION
    }),
    SUBJECT_COMMUNICATION("commons.function.subject_communication",
    new EventOperation[]{
        EventOperation.SEND_REPORT,
        EventOperation.SEND_INFORMATION
    }),
    DAILY_COLLATION("commons.function.daily_collation",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.ACCEPT,
        EventOperation.UPDATE
    }),
    TIME_SERIES_HANDLING("commons.function.time_series_handling",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.REMOVE
    }),
    ANNUAL_COLLATION_HANDLING("commons.function.annual_collation_handling",
    new EventOperation[]{
        EventOperation.CREATE,
        EventOperation.REMOVE
    });
    private final String msgKey;
    private final EventOperation[] operations;

    private EventFunction(String msgKey, EventOperation[] operations) {
        this.msgKey = msgKey;
        this.operations = operations;
        Arrays.sort(this.operations);
    }

    public String getMsgKey() {
        return msgKey;
    }

    public EventOperation[] getOperations() {
        return operations;
    }
}
