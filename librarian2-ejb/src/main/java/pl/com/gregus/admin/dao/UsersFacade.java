/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.gregus.admin.dao;

import java.util.List;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.common.dao.GenericFacade;

/**
 *
 * @author Grzegorz Guściora
 */
public interface UsersFacade extends GenericFacade<UsersInfoTO, Long> {
    
    UsersInfoTO getUserByLogin(String userName);

//    boolean isUsernamePasswordValid(String userName, String password);

//    FormAuthenticator getFormAuthenticator();

//    public boolean checkIfUserInDomain(String login);

//    public Map<String, String> getUserDetails(String login);

    public List<String> getLogins();

    public void activate(UsersInfoTO newUser);

    public void deactivate(UsersInfoTO newUser);

    public void login(UsersInfoTO newUser);

    public void logout(UsersInfoTO newUser);
    
}
