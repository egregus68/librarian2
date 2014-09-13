package pl.com.gregus.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pl.com.gregus.admin.to.UserGroupTO;
import pl.com.gregus.admin.to.UsersInfoTO;
import pl.com.gregus.dao.Identifiable;

/**
 * User.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-08-13 14:40:24
 */
@Entity
@Table(name = "L2_USER")
public class User implements Identifiable<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "SQ_L2_USER", allocationSize = 1, initialValue = 100)
    private Long id;

    @Column(name = "LOGIN", length = 100, nullable = false)
    private String login; //unique
    
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password; //unique

    @Column(name = "EMAIL", length = 130)
    private String email; //unique

    @Column(name = "FIRST_NAME", length = 30)
    private String firstName;

    @Column(name = "LAST_NAME", length = 70)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private STATUS status;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGON_DATE")
    private Date lastLogonDate;

    @ManyToMany (cascade=CascadeType.ALL)
    @JoinTable(name = "L2_USER_GROUP", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "ID"))
    private List<UserGroup> groups;

    public enum STATUS {

        ACTIVE("combo.select.active", "ACTIVE"),
        INACTIVE("combo.select.inactive", "INACTIVE");

        private String value;
        private String description;

        private STATUS(String value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value;
        }

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

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isIdSet() {
        return null != id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastLogonDate() {
        return lastLogonDate;
    }

    public void setLastLogonDate(Date lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public User() {
    }

    public User(UsersInfoTO userTO) {
        this.id = userTO.getId();
        this.firstName = userTO.getFirstName();
        this.lastName = userTO.getLastName();
        this.email = userTO.getEmail();
        this.lastLogonDate = userTO.getLastLogonDate();
        this.login = userTO.getLogin();
        this.status = userTO.getStatus();
        this.password = userTO.getPassword();
        if (userTO.getGroups() != null) {
            this.groups = new ArrayList<>(userTO.getGroups().size());
            for (UserGroupTO groupTO : userTO.getGroups()) {
                this.groups.add(new UserGroup(groupTO));
            }
        }
    }

    public UsersInfoTO createTO() {
        UsersInfoTO userTO = new UsersInfoTO();
        userTO.setId(id);
        userTO.setEmail(email);
        userTO.setFirstName(firstName);
        userTO.setLastLogonDate(lastLogonDate);
        userTO.setLastName(lastName);
        userTO.setLogin(login);
        userTO.setPassword(password);
        userTO.setStatus(status);
        userTO.setGroups(new ArrayList<UserGroupTO>());
        for (UserGroup userGroup : groups) {
            userTO.getGroups().add(userGroup.createTO());
        }
        return userTO;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", status=" + status + ", lastLogonDate=" + lastLogonDate + '}';
    }

}
