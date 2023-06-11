package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.athlete;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;

import java.util.List;
import java.util.Set;

public interface AthleteDiaryService {
    List<AppUser> getAthletesByTrainer(Long userId);

    void createAthleteDiary(Long groupId, Long adminId, Long requesterId);

    List<TrainingDiaryEntry> getEntriesFromAthlete(Long clickedPersonId, Long trainerId);

    void addAthleteEntry(Set<AppUser> trainer, Long athleteId, TrainingDiaryEntry clickedEntry1);

    AthleteDiary getAthleteDiaryById(Long userId, Long trainerId);
}
