package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.AppUserRole;

import java.util.Set;

public interface MutableAppUser {

    void setName(String name);
    void setSurname(String name);
    void setPassword(String password);
    void setRoles(Set<AppUserRole> roles);

    void setEmail(String email);

}
