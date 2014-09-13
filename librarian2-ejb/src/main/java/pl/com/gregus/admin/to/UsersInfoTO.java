package pl.com.gregus.admin.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import pl.com.gregus.admin.entity.User;
import pl.com.gregus.dao.Identifiable;

/**
 * UsersInfo.
 *
 * @author Grzegorz Guściora
 */
public class UsersInfoTO implements Identifiable<Long>, Serializable {

    private Long id;
    private String login; //unique
    private String password;
    private String email; //unique
    private String firstName;
    private String lastName;
    private Date lastLogonDate;
    private User.STATUS status;
    private List<UserGroupTO> groups;

    public List<UserGroupTO> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroupTO> groups) {
        this.groups = groups;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User.STATUS getStatus() {
        return status;
    }

    public void setStatus(User.STATUS status) {
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isIdSet() {
        return null != id;
    }

    public Date getLastLogonDate() {
        return lastLogonDate;
    }

    public void setLastLogonDate(Date lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }

    @Override
    public String toString() {
        return "UsersInfo{" + "login=" + login + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", lastLogonDate=" + lastLogonDate + ", status=" + status + '}';
    }
    
}
