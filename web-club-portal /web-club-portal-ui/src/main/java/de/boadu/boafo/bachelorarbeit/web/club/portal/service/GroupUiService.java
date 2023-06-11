package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;

import java.util.List;
import java.util.Set;

public interface GroupUiService {

    Group createTrainingGroup(MutableGroup newGroupToCreate);

    List<Group> getTrainingGroups();

    void addGroupRequest(Long groupId, MutableGroupRequest request);

    List<GroupRequest> getGroupRequestByTrainer(Long userId);

    Group getTrainingGroupById(Long groupId);

    void deleteGroupRequestById(Long id, Long groupId);

    List<Group> getTrainingGroupByAdmin(Long userId);

    void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry);

    void deleteGroupTraininingPlanEntry(Set<GroupDTO> groupId, TrainingPlanEntry entry);

    Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId);
}
