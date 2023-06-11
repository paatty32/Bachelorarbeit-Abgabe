package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.athlete.AthleteDiaryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiary.CompetitionDiaryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.competitiondiaryentry.CompetitionDiaryEntryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplan.TrainingPlanService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplanentry.TrainingPlanEntryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser.AppUserService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingDiary.TrainingDiaryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingdiaryentry.TrainingDiaryEntryService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.group.GroupService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationFacadeImpl implements ApplicationFacade{

    private final AppUserService appUserService;

    private final TrainingDiaryEntryService trainingDiaryEntryService;

    private final CompetitionDiaryEntryService competitionDiaryEntryService;

    private final CompetitionDiaryService competitionDiaryService;

    private final TrainingDiaryService trainingDiaryService;

    private final TrainingPlanService trainingPlanService;

    private final TrainingPlanEntryService trainingPlanEntryService;

    private final GroupService groupService;

    private final AthleteDiaryService athleteDiaryService;

    @Override
    public void createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception {

        this.getAppUserService().createUser(createPerson, clickedRoles);

    }

    @Override
    public List<TrainingDiaryEntry> getTrainingsDiaryEntriesByUser(Long userId) {
        return this.getTrainingDiaryService().getTrainingsDiaryEntriesByUser(userId);
    }


    @Override
    public void addNewTrainingDiaryEntry(long userid, TrainingDiaryEntry newEntry) {

        this.getTrainingDiaryService().addNewTrainingDiaryEntry(userid, newEntry);

    }

    @Override
    public void updateEntry(TrainingDiaryEntry updatedEntry) {

        this.getTrainingDiaryEntryService().updateEntry(updatedEntry);

    }

    @Override
    public void deleteTrainingEntry(Long userId, Long clickedEntryId) {

        this.getTrainingDiaryService().deleteEntry(userId, clickedEntryId);

    }

    @Override
    public void addNewCompetitionDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry) {

        this.getCompetitionDiaryService().addNewCompetitionDiaryEntry(userId, newEntry);

    }

    @Override
    public List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId) {

        List<CompetitionDiaryEntry> competitionDiaryEntriesByUser = this.getCompetitionDiaryService().getCompetitionDiaryEntriesByUser(userId);

        return competitionDiaryEntriesByUser;

    }

    @Override
    public CompetitionDiaryEntryDto updateCompetitionEntry(MutableCompetitionDiaryEntry updatedEntry) {

        CompetitionDiaryEntryDto updatedCompetitionEntry = this.getCompetitionDiaryEntryService().updateCompetitionEntry(updatedEntry);

        return updatedCompetitionEntry;

    }

    @Override
    public void deleteEntry(Long userId, Long clickedEntryId) {

        this.getCompetitionDiaryService().deleteEntry(userId, clickedEntryId);
    }

    @Override
    public void addNewTrainingPlanEntry(Long userId, MutableTrainingPlanEntry newEntry) {

        this.getTrainingPlanService().addNewTrainingPlanEntry(userId, newEntry);

    }

    @Override
    public List<TrainingPlanEntry> getTrainingPlanEntries(Long userId) {
        return this.getTrainingPlanService().getTrainingPlanEntries(userId);
    }

    @Override
    public void updateTrainingPlanEntry(MutableTrainingPlanEntry entry) {

        this.getTrainingPlanEntryService().updateEntry(entry);

    }

    @Override
    public void deleteTrainingPlanEntry(Long userId, Long deleteEntryId) {
        this.getTrainingPlanService().deleteTrainingPlanEntry(userId, deleteEntryId);
    }

    @Override
    public Group createTrainingGroup(MutableGroup newGroupToCreate) {

        Group traininGroup = this.getGroupService().createTraininGroup(newGroupToCreate);

        return traininGroup;
    }

    @Override
    public List<Group> getTrainingGroups() {
        return this.getGroupService().getAllTrainingGroups();
    }

    @Override
    public void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup) {
        this.getAppUserService().addNewGroupToUser(userId ,newTrainingGroup);
    }

    @Override
    public Set<Group> getUserGroups(Long userId) {
        return this.getAppUserService().getUserGroups(userId);
    }

    @Override
    public void addGroupRequest(Long groupId, MutableGroupRequest request) {

        this.getGroupService().addGroupRequest(groupId, request);

    }

    @Override
    public List<GroupRequest> getGroupRequestByTrainer(Long userId) {
        List<GroupRequest> groupRequestByAdmin = this.getGroupService().getGroupRequestByAdmin(userId);

        return groupRequestByAdmin;
    }

    @Override
    public Group getTrainingGroupById(Long groupId) {
        return this.getGroupService().getTrainingGroupById(groupId);
    }

    @Override
    public void deleteGroupRequestById(Long id, Long groupId) {
        this.getGroupService().deleteGroupequestById(id, groupId);
    }

    @Override
    public Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId) {
        return getGroupService().getTrainingPlanByGroup(groupId);
    }

    @Override
    public List<Group> getTrainingGroupByAdmin(Long userId) {
        return this.getGroupService().getTrainingGroupsByAdmin(userId);
    }

    @Override
    public void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry) {
        this.getGroupService().addTrainingPlanEntry(groupId, newEntry);
    }


    @Override
    public TrainingPlanEntry getTrainingPlanEntry(Long entryId) {
        return this.getTrainingPlanEntryService().getTrainingPlanEntry(entryId);
    }

    @Override
    public void deleteGroupTrainingPlanEntry(Set<GroupDTO> groups, TrainingPlanEntry entry) {
        this.getGroupService().deleteGroupTrainingPlanEntry(groups, entry);
    }

    @Override
    public List<AppUser> getAthletesByTrainer(Long userId) {
        return this.getAthleteDiaryService().getAthletesByTrainer(userId);
    }

    @Override
    public void createAthleteDiary(Long groupId, Long adminId, Long requesterId) {
        this.getAthleteDiaryService().createAthleteDiary(groupId, adminId, requesterId);
    }

    @Override
    public List<TrainingDiaryEntry> getEntriesFromAthlete(Long clickedPersonId, Long trainerId) {
        return this.getAthleteDiaryService().getEntriesFromAthlete(clickedPersonId, trainerId);
    }

    @Override
    public Set<AppUser> getUserTrainer(Long userId) {
        return this.getAppUserService().getUserTrainer(userId);
    }

    @Override
    public void addAthleteEntry(Set<AppUser> trainer, Long athleteId, TrainingDiaryEntry clickedEntry1) {
        this.getAthleteDiaryService().addAthleteEntry(trainer, athleteId, clickedEntry1);
    }


}
