package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplan;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.*;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingPlanServiceImpl implements TrainingPlanService{

    private final TrainingPlanRepository trainingPlanRepository;

    private final TrainingPlanEntryRepository trainingPlanEntryRepository;

    @Override
    public void addNewTrainingPlanEntry(Long userId, MutableTrainingPlanEntry newEntry) {

        DiaryId diaryId = this.buidTrainingPlanId(userId);

        if(diaryId.getDiaryType().equals(DiaryType.TRAININGPLAN)) {

            TrainingPlanDTO trainingPlanById = this.getTrainingPlanDiaryByUser(diaryId);
            trainingPlanById.getEntries().add((TrainingPlanEntryDTO) newEntry);

            this.getTrainingPlanRepository().save(trainingPlanById);

        }

    }

    @Override
    public List<TrainingPlanEntry> getTrainingPlanEntries(Long userId) {

        List<TrainingPlanEntry> trainingPlanEntries = new ArrayList<>();

        DiaryId diaryId = buidTrainingPlanId(userId);

        TrainingPlanDTO trainingPlanDiaryByUser = this.getTrainingPlanDiaryByUser(diaryId);
        List<TrainingPlanEntryDTO> entries = trainingPlanDiaryByUser.getEntries();

        if(entries != null){

            trainingPlanEntries.addAll(entries);

        }
        return trainingPlanEntries;


    }

    @Override
    public void deleteTrainingPlanEntry(Long userId, Long deleteEntryId) {

        DiaryId diaryId = this.buidTrainingPlanId(userId);

        TrainingPlanDTO trainingPlanDiaryByUser = this.getTrainingPlanDiaryByUser(diaryId);

        TrainingPlanEntryDTO entryById = this.getTrainingPlanEntryRepository().findEntryById(deleteEntryId);

        int entryIndex = trainingPlanDiaryByUser.getEntries().indexOf(entryById);

        if(entryIndex != -1){

            trainingPlanDiaryByUser.getEntries().remove(entryIndex);

        }

        this.getTrainingPlanRepository().save(trainingPlanDiaryByUser);


    }

    private DiaryId buidTrainingPlanId(Long userId) {
        return DiaryId.builder()
                .userId(userId)
                .diaryType(DiaryType.TRAININGPLAN)
                .build();
    }

    private TrainingPlanDTO getTrainingPlanDiaryByUser(DiaryId diaryId) {

        return this.getTrainingPlanRepository().findTrainingPlanById(diaryId);

    }
}
