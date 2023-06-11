package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;

import java.util.Set;

public interface RegistrationUiService {
    void createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception;

}
