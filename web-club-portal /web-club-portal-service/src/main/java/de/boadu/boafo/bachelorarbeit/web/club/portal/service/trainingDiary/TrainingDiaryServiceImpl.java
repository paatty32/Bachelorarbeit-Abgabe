package de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingDiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.AthleteDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.athlete.repository.AthleteDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser.AppUserService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.athlete.AthleteDiaryService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Getter(AccessLevel.PRIVATE)
public class TrainingDiaryServiceImpl implements TrainingDiaryService{

    private final TrainingsDiaryRepository trainingsDiaryRepository;

    private final TrainingsDiaryEntryRepository trainingsDiaryEntryRepository;

    private final AthleteDiaryService athleteDiaryService;

    private final AthleteDiaryRepository athleteDiaryRepository;

    private final AppUserService appUserService;

    @Override
    public TrainingDiary getTrainingsDiaryByUser(DiaryId userId) {

        TrainingDiaryDto trainingDiaryById = this.getTrainingsDiaryRepository().findTrainingDiaryById(userId);

        return trainingDiaryById;
    }

    @Override
    public void addNewTrainingDiaryEntry(long userId, TrainingDiaryEntry newEntry) {

        DiaryId trainingDiaryIdByUser = buildTrainingDiaryId(userId);

        TrainingDiary diary = this.getTrainingsDiaryByUser(trainingDiaryIdByUser);

        diary.getEntry().add((TrainingDiaryEntryDTO) newEntry);

        this.getTrainingsDiaryRepository().save((TrainingDiaryDto) diary);
    }



    @Override
    public List<TrainingDiaryEntry> getTrainingsDiaryEntriesByUser(Long userId) {

        List<TrainingDiaryEntry> trainingDiaryEntries = new ArrayList<>();

        DiaryId trainingDiaryIdByUser = this.buildTrainingDiaryId(userId);

        TrainingDiary trainingsDiaryByUser = this.getTrainingsDiaryByUser(trainingDiaryIdByUser);

        List<TrainingDiaryEntryDTO> entry = trainingsDiaryByUser.getEntry();

        trainingDiaryEntries.addAll(entry);

        return trainingDiaryEntries;

    }

    @Override
    @Transactional
    public void deleteEntry(Long currentPersonId, Long selectedEntryId) {

        DiaryId trainingDiaryIdByUser = this.buildTrainingDiaryId(currentPersonId);

        TrainingDiary diary = this.getTrainingsDiaryByUser(trainingDiaryIdByUser);

        TrainingDiaryEntryDTO entryById = this.getTrainingsDiaryEntryRepository().findEntryById(selectedEntryId);

        int entryIndex = diary.getEntry().indexOf(entryById);

        if(entryIndex != -1) {

            Long userID = currentPersonId;

            TrainingDiaryEntryDTO trainingDiaryEntry = diary.getEntry().get(entryIndex);

            Set<AppUser> userTrainer = this.getAppUserService().getUserTrainer(userID);

            for (AppUser trainer:  userTrainer) {

                Long trainerId = trainer.getId();

                AthleteDiary athleteDiaryById = this.getAthleteDiaryService().getAthleteDiaryById(userID, trainerId);

                athleteDiaryById.removeEntry(trainingDiaryEntry);

                this.getAthleteDiaryRepository().save((AthleteDiaryDto) athleteDiaryById);
            }

            diary.getEntry().remove(entryIndex);

        }
        this.getTrainingsDiaryRepository().save((TrainingDiaryDto) diary);
    }

    private DiaryId buildTrainingDiaryId(long userId) {
        return DiaryId.builder()
                .userId(userId)
                .diaryType(DiaryType.TRAINING)
                .build();
    }

}
