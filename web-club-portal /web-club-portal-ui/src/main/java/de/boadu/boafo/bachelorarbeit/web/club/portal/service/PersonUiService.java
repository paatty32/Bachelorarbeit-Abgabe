package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.MutableGroup;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.Group;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;

import java.util.Set;

public interface PersonUiService {
    void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup);

    Set<Group> getUserGroups(Long userId);

    Set<AppUser> getUserTrainer(Long userId);
}
