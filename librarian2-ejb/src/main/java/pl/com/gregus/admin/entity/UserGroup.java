package pl.com.gregus.admin.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import pl.com.gregus.admin.to.UserGroupTO;
import pl.com.gregus.dao.Identifiable;

/**
 * Grupowanie uprawnien (rol z punktu widzenia autoryzavji).
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-08-13 15:01:39
 */
@Entity
@Table(name = "L2_GROUP")
public class UserGroup implements Identifiable<Long> {

    private static final long serialVersionUID = 1L;

    public UserGroup() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "group_seq_gen")
    @SequenceGenerator(name = "group_seq_gen", sequenceName = "SQ_L2_GROUP", allocationSize = 1, initialValue = 100)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 350)
    private String description;

    @Column(name = "IS_ACTIVE", columnDefinition = "int")
    private Boolean isActive = Boolean.TRUE;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> user;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public UserGroup(UserGroupTO groupTO) {
        this.id = groupTO.getId();
        this.description = groupTO.getDescription();
        this.isActive = groupTO.getIsActive();
        this.name = groupTO.getName();
    }

    public UserGroupTO createTO() {
        UserGroupTO groupTO = new UserGroupTO();
        groupTO.setId(id);
        groupTO.setName(name);
        groupTO.setDescription(description);
        groupTO.setIsActive(isActive);
        return groupTO;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserGroup other = (UserGroup) obj;
        return Objects.equals(this.name, other.name);
    }

}
