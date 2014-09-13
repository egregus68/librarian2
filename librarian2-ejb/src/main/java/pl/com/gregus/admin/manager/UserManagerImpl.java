/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.manager;

import javax.ejb.Local;
import javax.ejb.Stateless;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.admin.to.UserInfoSC;
import pl.com.gregus.dao.criteria.PagedList;
import pl.com.gregus.manager.AbstractManager;

/**
 * UserManagerImplImpl.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-08-14 15:37:09
 */
@Stateless
@Local(UserManager.class)
public class UserManagerImpl extends AbstractManager<User, Long> implements UserManager {

    @Override
    public User getUserByLogin(String userName) {
        UserInfoSC criteria = new UserInfoSC();
        criteria.setLogin(userName);
        PagedList<User> result = this.find(criteria);
        return result.getData().isEmpty() ? null : result.getData().get(0);
    }

}
