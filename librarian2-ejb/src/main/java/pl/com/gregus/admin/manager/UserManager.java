/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.gregus.admin.manager;

import pl.com.gregus.admin.entity.User;
import pl.com.gregus.manager.GenericManager;

/**
 *
 * @author Grzegorz Guściora
 */
public interface UserManager extends GenericManager<User, Long> {

    User getUserByLogin(String userName);
}
