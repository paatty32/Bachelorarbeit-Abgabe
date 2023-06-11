package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplanentry;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanEntryRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingPlanEntryServiceImpl implements TrainingPlanEntryService{

    private final TrainingPlanEntryRepository trainingPlanEntryRepository;

    @Override
    public void updateEntry(MutableTrainingPlanEntry entry) {

        this.getTrainingPlanEntryRepository().save((TrainingPlanEntryDTO) entry);

    }

    @Override
    public TrainingPlanEntry getTrainingPlanEntry(Long id) {
        return this.getTrainingPlanEntryRepository().findEntryById(id);
    }
}
