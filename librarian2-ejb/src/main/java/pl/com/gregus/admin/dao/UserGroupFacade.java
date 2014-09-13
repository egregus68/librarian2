/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.com.gregus.admin.entity.UserGroup;
import pl.com.gregus.admin.manager.UserGroupManager;
import pl.com.gregus.admin.to.UserGroupTO;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.criteria.PagedList;

/**
 *
 * @author Grzegorz Guściora
 */
@Stateless
public class UserGroupFacade implements Serializable {

    @EJB
    private transient UserGroupManager userGroupManager;

    public PagedList<UserGroupTO> find(BaseSearchCriteria criteria) {
        PagedList<UserGroup> found = userGroupManager.find(criteria);
        PagedList<UserGroupTO> toReturn = new PagedList<>();
        List<UserGroupTO> data = new ArrayList<>(found.getData().size());
        for (UserGroup userGroup : found.getData()) {
            data.add(userGroup.createTO());
        }
        toReturn.setData(data);
        toReturn.setPageNumber(found.getPageNumber());
        toReturn.setRecordCounter(found.getRecordCounter());
        return toReturn;
    }

    public UserGroupTO getById(Long Id) {
        return userGroupManager.getById(Id).createTO();
    }

    public void deleteById(Long Id) {
        userGroupManager.deleteById(Id);
    }

    public void save(UserGroupTO to) {
        UserGroup entity = new UserGroup(to);
        userGroupManager.save(entity);
    }

    public List<UserGroupTO> findAll() {
        List<UserGroup> found = userGroupManager.findAll();
        List<UserGroupTO> data = new ArrayList<>(found.size());
        for (UserGroup userGroup : found) {
            data.add(userGroup.createTO());
        }
        return data;
    }
}
