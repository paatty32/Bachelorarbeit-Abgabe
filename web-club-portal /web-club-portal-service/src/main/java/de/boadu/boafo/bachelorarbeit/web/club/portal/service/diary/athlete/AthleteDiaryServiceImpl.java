package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.athlete;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.repository.AthleteDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository.AppUserRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryEntryRepository;
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

@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AthleteDiaryServiceImpl implements AthleteDiaryService {

    private final AthleteDiaryRepository athleteDiaryRepository;

    private final AppUserRepository appUserRepository;

    private final TrainingsDiaryEntryRepository trainingsDiaryEntryRepository;

    @Override
    public List<AppUser> getAthletesByTrainer(Long userId) {
        List<AthleteDiaryDto> diaryByTrainerId = this.getAthleteDiaryRepository().getDiaryByTrainerId(userId);

        List<AppUserDTO> athletesBuffer = new ArrayList<>();

        List<AppUser> athletes = new ArrayList<>();

        for (AthleteDiaryDto entry: diaryByTrainerId) {

            AppUserDTO personById = this.getAppUserRepository().findPersonById(entry.getAthleteId());
            athletesBuffer.add(personById);

        }
        
        athletes.addAll(athletesBuffer);

        return athletes;

    }

    @Override
    public void createAthleteDiary(Long groupId, Long adminId, Long requesterId) {

        AthleteDiaryDto athleteDiaryDto = AthleteDiaryDto.builder()
                .groupId(groupId)
                .athleteId(requesterId)
                .trainerId(adminId)
                .build();

        this.getAthleteDiaryRepository().save(athleteDiaryDto);

    }

    @Override
    public List<TrainingDiaryEntry> getEntriesFromAthlete(Long clickedPersonId, Long trainerId) {

        AthleteDiaryDto athleteDiary = this.getAthleteDiaryRepository()
                .getDiaryByTrainerIdAndAthleteId(trainerId, clickedPersonId);

        List<TrainingDiaryEntry> athleteEntries = new ArrayList<>();

        Set<TrainingDiaryEntryDTO> athleteEntriesBuffer = athleteDiary.getAthleteEntries();
        athleteEntries.addAll(athleteEntriesBuffer);

        return athleteEntries;

    }

    @Override
    public void addAthleteEntry(Set<AppUser> trainer, Long athleteId, TrainingDiaryEntry clickedEntry1) {

        Set<AppUser> clickedTrainerBuffer = new HashSet<>();
        clickedTrainerBuffer.addAll(trainer);

        for (AppUser clickedTrainer: clickedTrainerBuffer) {

            Long trainerId = clickedTrainer.getId();

            AthleteDiaryDto diaryByTrainerIdAndAthleteId = this.getAthleteDiaryRepository().getDiaryByTrainerIdAndAthleteId(trainerId, athleteId);
            diaryByTrainerIdAndAthleteId.getAthleteEntries().add((TrainingDiaryEntryDTO) clickedEntry1);

            this.getAthleteDiaryRepository().save(diaryByTrainerIdAndAthleteId);

            TrainingDiaryEntryDTO trainingEntry = (TrainingDiaryEntryDTO) clickedEntry1;

            trainingEntry.getAthleteDiaries().add(diaryByTrainerIdAndAthleteId);

            this.getTrainingsDiaryEntryRepository().save(trainingEntry);

        }
    }

    @Override
    public AthleteDiary getAthleteDiaryById(Long userId, Long trainerId) {

        AthleteDiaryDto diaryByTrainerIdAndAthleteId = this.getAthleteDiaryRepository().getDiaryByTrainerIdAndAthleteId(trainerId, userId);

        return diaryByTrainerIdAndAthleteId;
    }


}
