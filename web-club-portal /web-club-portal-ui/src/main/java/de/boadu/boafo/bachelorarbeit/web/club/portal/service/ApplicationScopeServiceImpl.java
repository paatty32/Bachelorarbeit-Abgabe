package de.boadu.boafo.bachelorarbeit.web.club.portal.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.CompetitionDiaryEntryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.MutableCompetitionDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@SpringComponent
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationScopeServiceImpl implements ApplicationScopeService{

    private final ApplicationFacade applicationFacade;

    @Override
    public void createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception {

        this.getApplicationFacade().createUser(createPerson, clickedRoles);

    }

    @Override
    public List<TrainingDiaryEntry> getTrainingsDiaryEntriesByUser(Long userId) {

        List<TrainingDiaryEntry> trainingsDiaryEntriesByUser = this.getApplicationFacade().getTrainingsDiaryEntriesByUser(userId);

        return trainingsDiaryEntriesByUser;

    }

    @Override
    public void addNewTrainingDiaryEntry(long userId, TrainingDiaryEntry newEntry) {

        this.getApplicationFacade().addNewTrainingDiaryEntry(userId, newEntry);

    }

    @Override
    public void updateEntry(TrainingDiaryEntry updatedEntry) {

        this.getApplicationFacade().updateEntry(updatedEntry);

    }

    @Override
    public void deleteTrainingEntry(Long userId, Long entryId) {

        this.getApplicationFacade().deleteTrainingEntry(userId, entryId);

    }

    @Override
    public void addNewDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry) {

        this.getApplicationFacade().addNewCompetitionDiaryEntry(userId, newEntry);
        
    }

    @Override
    public List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId) {

        List<CompetitionDiaryEntry> competitionDiaryEntriesByUser = this.getApplicationFacade().getCompetitionDiaryEntriesByUser(userId);

        return competitionDiaryEntriesByUser;
    }

    @Override
    public CompetitionDiaryEntryDto upadeEntry(MutableCompetitionDiaryEntry updatedEntry) {

        CompetitionDiaryEntryDto updatedCompEntry = this.getApplicationFacade().updateCompetitionEntry(updatedEntry);

        return updatedCompEntry;

    }

    @Override
    public void deleteEntry(Long currentPersonId, Long entry) {

        this.getApplicationFacade().deleteEntry(currentPersonId, entry);
    }

    @Override
    public List<TrainingPlanEntry> getTrainingPlanEntriesByUser(Long userId) {
        return this.getApplicationFacade().getTrainingPlanEntries(userId);
    }

    @Override
    public void addnewTrainingPlanEntry(Long id, MutableTrainingPlanEntry newEntry) {

        this.getApplicationFacade().addNewTrainingPlanEntry(id, newEntry);

    }

    @Override
    public void updateEntry(MutableTrainingPlanEntry entry) {
        this.getApplicationFacade().updateTrainingPlanEntry(entry);
    }

    @Override
    public void deleteTrainingPlanEntry(Long userId, Long deleteEntryId) {
        this.getApplicationFacade().deleteTrainingPlanEntry(userId, deleteEntryId);
    }

    @Override
    public Group createTrainingGroup(MutableGroup newGroupToCreate) {

        Group group = this.getApplicationFacade().createTrainingGroup(newGroupToCreate);

        return group;
    }

    @Override
    public List<Group> getTrainingGroups() {
        return this.getApplicationFacade().getTrainingGroups();
    }

    @Override
    public void addGroupRequest(Long groupId, MutableGroupRequest request) {
        this.getApplicationFacade().addGroupRequest(groupId, request);
    }

    @Override
    public List<GroupRequest> getGroupRequestByTrainer(Long userId) {
        return this.getApplicationFacade().getGroupRequestByTrainer(userId);
    }

    @Override
    public Group getTrainingGroupById(Long groupId) {
        return this.getApplicationFacade().getTrainingGroupById(groupId);
    }

    @Override
    public void deleteGroupRequestById(Long id, Long groupId) {
        this.getApplicationFacade().deleteGroupRequestById(id, groupId);
    }

    @Override
    public List<Group> getTrainingGroupByAdmin(Long userId) {
        return this.getApplicationFacade().getTrainingGroupByAdmin(userId);
    }

    @Override
    public void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry) {
        this.getApplicationFacade().addTrainingPlanEntry(groupId, newEntry);
    }


    @Override
    public void deleteGroupTraininingPlanEntry(Set<GroupDTO> groups, TrainingPlanEntry entry) {
        this.getApplicationFacade().deleteGroupTrainingPlanEntry(groups, entry);
    }

    @Override
    public void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup) {

        this.getApplicationFacade().addNewGroupToUser(userId, newTrainingGroup);

    }

    @Override
    public Set<Group> getUserGroups(Long userId) {
        return this.getApplicationFacade().getUserGroups(userId);
    }

    @Override
    public Set<AppUser> getUserTrainer(Long userId) {
        return this.getApplicationFacade().getUserTrainer(userId);
    }


    @Override
    public Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId) {
        return this.getApplicationFacade().getTrainingPlanByGroup(groupId);
    }

    @Override
    public TrainingPlanEntry getEntry(Long entryId) {
        return this.getApplicationFacade().getTrainingPlanEntry(entryId);
    }

    @Override
    public List<AppUser> getAthletesByTrainer(Long userId) {
        return this.getApplicationFacade().getAthletesByTrainer(userId);
    }

    @Override
    public void createAthleteDiary(Long groupId, Long adminId, Long requesterId) {
        this.getApplicationFacade().createAthleteDiary(groupId, adminId, requesterId);
    }

    @Override
    public List<TrainingDiaryEntry> getEntriesFromAthlete(Long clickedPersonId, Long trainerId) {
        return this.getApplicationFacade().getEntriesFromAthlete(clickedPersonId, trainerId);
    }

    @Override
    public void addAthleteEntry(Set<AppUser> trainer, Long athleteId, TrainingDiaryEntry clickedEntry1) {
        this.getApplicationFacade().addAthleteEntry(trainer, athleteId, clickedEntry1);
    }
}
