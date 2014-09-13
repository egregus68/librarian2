/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.enums;

/**
 *
 * @author Grzegorz Guściora
 */
public enum EventOperation {

    ADD("commons.operation.add"),
    REMOVE("commons.operation.remove"),
    MODIFY("commons.operation.modify"),
    CLOSE_PERIOD("commons.operation.close_period"),
    BLOCK_PERIOD("commons.operation.block_period"),
    ACTIVATE_PERIOD("commons.operation.activate_period"),
    CHANGE_VALID_DATE("commons.operation.change_valid_date"),
    ADD_DATA_VERSION("commons.operation.add_data_version"),
    MODIFY_DATA_VERSION("commons.operation.modify_data_version"),
    REMOVE_DATA_VERSION("commons.operation.remove_data_version"),
    DATA_IMPORT("commons.operation.data_import"),
    DEACTIVATE("commons.operation.deactivate"),
    ACTIVATE("commons.operation.activate"),
    DATA_IMPORT_EXECUTION("commons.operation.data_import_execution"),
    LOGIN("commons.operation.login"),
    LOGOUT("commons.operation.logout"),
    CREATE("commons.operation.create"),
    ACCEPT("commons.operation.accept"),
    UPDATE("commons.operation.update"),
    CREATE_RECIPIENT_FILE("commons.operation.create_recipient_file"),
    SEND_REPORT("commons.operation.send_report"),
    SEND_INFORMATION("commons.operation.send_information");
    private final String msgKey;

    private EventOperation(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
