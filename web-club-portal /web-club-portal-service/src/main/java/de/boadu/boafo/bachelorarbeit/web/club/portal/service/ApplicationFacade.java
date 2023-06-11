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

import java.util.List;
import java.util.Set;

public interface ApplicationFacade {

    void createUser(MutableAppUser createPerson, Set<String> clickedRoles) throws Exception;

    List<TrainingDiaryEntry> getTrainingsDiaryEntriesByUser(Long userId);

    void addNewTrainingDiaryEntry(long userId, TrainingDiaryEntry newEntry);

    void updateEntry(TrainingDiaryEntry updatedEntry);

    void deleteTrainingEntry(Long currentPersonId, Long selectedEntry);

    void addNewCompetitionDiaryEntry(Long userId, MutableCompetitionDiaryEntry newEntry);

    List<CompetitionDiaryEntry> getCompetitionDiaryEntriesByUser(Long userId);

    CompetitionDiaryEntryDto updateCompetitionEntry(MutableCompetitionDiaryEntry updatedEntry);

    void deleteEntry(Long userId, Long entry);

    void addNewTrainingPlanEntry(Long userId, MutableTrainingPlanEntry newEntry);

    List<TrainingPlanEntry> getTrainingPlanEntries(Long userId);

    void updateTrainingPlanEntry(MutableTrainingPlanEntry entry);

    void deleteTrainingPlanEntry(Long userId, Long deleteEntryId);

    Group createTrainingGroup(MutableGroup newGroupToCreate);

    List<Group> getTrainingGroups();

    void addNewGroupToUser(Long userId, MutableGroup newTrainingGroup);

    Set<Group> getUserGroups(Long userId);

    void addGroupRequest(Long groupId, MutableGroupRequest request);

    List<GroupRequest> getGroupRequestByTrainer(Long userId);

    Group getTrainingGroupById(Long groupId);

    void deleteGroupRequestById(Long id, Long groupId);

    Set<TrainingPlanEntry> getTrainingPlanByGroup(Long groupId);

    List<Group> getTrainingGroupByAdmin(Long userId);

    void addTrainingPlanEntry(Long groupId, MutableTrainingPlanEntry newEntry);

    TrainingPlanEntry getTrainingPlanEntry(Long entryId);

    void deleteGroupTrainingPlanEntry(Set<GroupDTO> groupId, TrainingPlanEntry entry);

    List<AppUser> getAthletesByTrainer(Long userId);

    void createAthleteDiary(Long groupId, Long adminId, Long requesterId);

    List<TrainingDiaryEntry> getEntriesFromAthlete(Long clickedPersonId, Long trainerId);

    Set<AppUser> getUserTrainer(Long userId);

    void addAthleteEntry(Set<AppUser> trainer, Long athleteId, TrainingDiaryEntry clickedEntry1);
}
