package de.boadu.boafo.bachelorarbeit.web.club.portal.service.group;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;

import java.util.List;
import java.util.Set;

public interface GroupService {
    Group createTraininGroup(MutableGroup newGroupToCreate);

    List<Group> getAllTrainingGroups();

    void addGroupRequest(Long groupId, MutableGroupRequest request);

    List<GroupRequest> getGroupRequestByAdmin(Long adminId);

    Group getTrainingGroupById(Long groupId);

    void deleteGroupequestById(Long id, Long groupId);

    List<Group> getTrainingGroupsByAdmin(Long userId);

    void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry);

    void deleteTrainingPlanGroupEntry(TrainingPlanEntry deleteEntryId);

    void deleteGroupTrainingPlanEntry(Set<GroupDTO> groupId, TrainingPlanEntry entry);

    Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId);

}
