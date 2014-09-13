package pl.com.gregus.admin.to;

import java.util.Arrays;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.dao.criteria.BaseSearchCriteria;
import pl.com.gregus.dao.utils.FilterEnumTO;
import pl.com.gregus.dao.utils.FilterStringTO;

/**
 * UserInfoSC.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-08-14 16:00:16
 */
public class UserInfoSC extends BaseSearchCriteria {

    private String login; //unique
    private User.STATUS status;
    private String group;

    public String getLogin() {
        return login;
    }

    public User.STATUS getStatus() {
        return status;
    }

    public void setLogin(String login) {
        getFiltrSort().getFilters().add(FilterStringTO.valueOf("", "login", "like", login));
        this.login = login;
    }

    public void setStatus(User.STATUS selectedStatus) {
        getFiltrSort().getFilters().add(FilterEnumTO.valueOf("", "status", "IN", Arrays.asList(selectedStatus)));
        this.status = selectedStatus;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        getFiltrSort().getFilters().add(FilterStringTO.valueOf("", "groups.name", "=", group));
        this.group = group;
    }
}
