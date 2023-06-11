package de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;

import java.util.Set;

public interface AppUserService {

    AppUserDTO createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception;

    void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup);

    Set<Group> getUserGroups(Long userId);

    Set<AppUser> getUserTrainer(Long userId);
}
