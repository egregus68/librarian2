/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.model;

import pl.com.gregus.admin.dao.UsersFacade;
import pl.com.gregus.admin.to.UserInfoSC;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.common.model.AbstractLazyDataModel;

/**
 *
 * @author Grzegorz Guściora
 */
public class UsersAdminLM extends AbstractLazyDataModel<UsersInfoTO, Long, UserInfoSC> {

    public UsersAdminLM(UsersFacade service) {
        super(service, new UserInfoSC());
    }

    public UsersAdminLM(UsersFacade service, UserInfoSC criteria) {
        super(service, criteria);
    }
}
