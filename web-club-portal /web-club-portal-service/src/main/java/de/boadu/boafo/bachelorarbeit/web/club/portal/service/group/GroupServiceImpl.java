package de.boadu.boafo.bachelorarbeit.web.club.portal.service.group;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.repository.GroupRequestRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.repository.GroupRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupRequestRepository groupRequestRepository;

    @Override
    public Group createTraininGroup(MutableGroup newGroupToCreate) {

        Set<TrainingPlanEntryDTO> trainingPlanEntry = new HashSet<>();
        newGroupToCreate.setTrainingPlanEntry(trainingPlanEntry);

        GroupDTO savedGroupDTO = this.getGroupRepository().save((GroupDTO) newGroupToCreate);

        return savedGroupDTO;

    }

    @Override
    public List<Group> getAllTrainingGroups() {

        List<GroupDTO> trainingGroup = this.getGroupRepository().findAll();

        List<Group> groups = new ArrayList<>();
        groups.addAll(trainingGroup);

        return groups;


    }

    @Override
    public void addGroupRequest(Long groupId, MutableGroupRequest request) {

        GroupDTO trainingGroupById = this.getGroupRepository().getTrainingGroupById(groupId);
        Set<GroupRequestsDTO> groupRequests = trainingGroupById.getRequests();
        groupRequests.add((GroupRequestsDTO) request);

        this.getGroupRepository().save(trainingGroupById);

    }

    @Override
    public List<GroupRequest> getGroupRequestByAdmin(Long adminId) {

        List<GroupRequestsDTO> groupRequestsByAdminId = this.getGroupRequestRepository().getGroupRequestsByAdminId(adminId);

        List<GroupRequest> groupRequests = new ArrayList<>();
        groupRequests.addAll(groupRequestsByAdminId);

        return groupRequests;

    }


    @Override
    public Group getTrainingGroupById(Long groupId) {

        GroupDTO trainingGroupById = this.getGroupRepository().getTrainingGroupById(groupId);

        return trainingGroupById;

    }

    @Override
    public void deleteGroupequestById(Long requesterId, Long groupId) {

        GroupDTO trainingGroupById = this.getGroupRepository().getTrainingGroupById(groupId);

        GroupRequestsDTO groupRequestsById = this.getGroupRequestRepository().getGroupRequestByRequesterIdAndGroupId(requesterId, groupId);
        trainingGroupById.getRequests().remove(groupRequestsById);

        this.getGroupRepository().save(trainingGroupById);

    }

    @Override
    public List<Group> getTrainingGroupsByAdmin(Long userId) {

        List<GroupDTO> trainingGroupByAdminIdBuffer = this.getGroupRepository().getTrainingGroupByAdminId(userId);

        List<Group> groupByAdminId = new ArrayList<>(trainingGroupByAdminIdBuffer);

        return groupByAdminId;

    }

    @Override
    public void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry) {

        Group trainingGroupById = this.getTrainingGroupById(groupId);
        trainingGroupById.getTrainingPlanEntry().add((TrainingPlanEntryDTO) newEntry);

        this.getGroupRepository().save((GroupDTO) trainingGroupById);

    }

    @Override
    public void deleteTrainingPlanGroupEntry(TrainingPlanEntry deleteEntryId) {


    }

    @Override
    public void deleteGroupTrainingPlanEntry(Set<GroupDTO> groupId, TrainingPlanEntry entry) {

        Set<GroupDTO> groups = new HashSet<>();
        groups.addAll(groupId);

        for (GroupDTO group: groups) {

            Long groupId1 = group.getId();
            GroupDTO trainingGroupById = this.getGroupRepository().getTrainingGroupById(groupId1);

            trainingGroupById.removeEntry((TrainingPlanEntryDTO) entry);

            this.getGroupRepository().save(trainingGroupById);
        }

    }

    @Override
    public Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId) {

        Group trainingGroupById = this.getTrainingGroupById(groupId);

        Set<TrainingPlanEntryDTO> trainingPlanEntryBuffer = trainingGroupById.getTrainingPlanEntry();

        Set<TrainingPlanEntry> trainingPlanEntry = new HashSet<>();
        trainingPlanEntry.addAll(trainingPlanEntryBuffer);


        return trainingPlanEntry;
    }


}
