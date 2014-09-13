/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.gregus.admin.to;

import java.util.List;
import java.util.Objects;
import pl.com.gregus.dao.Identifiable;

/**
 * UserGroupTO.
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2014-01-25 15:31:18
 */
public class UserGroupTO implements Identifiable<Long> {

    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private List<UsersInfoTO> user;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<UsersInfoTO> getUser() {
        return user;
    }

    public void setUser(List<UsersInfoTO> user) {
        this.user = user;
    }

    @Override
    public boolean isIdSet() {
        return id != null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
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
        final UserGroupTO other = (UserGroupTO) obj;
        return Objects.equals(this.name, other.name);
    }
}
