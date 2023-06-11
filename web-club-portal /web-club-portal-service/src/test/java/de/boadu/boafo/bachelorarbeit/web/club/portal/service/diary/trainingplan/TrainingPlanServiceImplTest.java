package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingplan;


import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.MutableTrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.TrainingPlanEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Getter(AccessLevel.PRIVATE)
public class TrainingPlanServiceImplTest {

    @Mock
    private TrainingPlanEntryRepository trainingPlanEntryRepository;

    @Mock
    private TrainingPlanRepository trainingPlanRepository;

    @InjectMocks
    private TrainingPlanServiceImpl trainingPlanService;

    AppUserDTO testPersonDTO;

    DiaryId diaryId;

    @BeforeEach
    public void setUpt(){

        this.testPersonDTO = AppUserDTO.builder().id(1L)
                .name("Test")
                .build();

        this.diaryId = DiaryId.builder()
                .userId(this.getTestPersonDTO().getId())
                .diaryType(DiaryType.TRAININGPLAN)
                .build();

    }

    @Test
    public void whenTrainingPlanEntriesIsThere_thenReturnTrainingPlanEntries(){

        List<TrainingPlanEntryDTO> entries = new ArrayList<>();
        TrainingPlanEntryDTO max = TrainingPlanEntryDTO.builder()
                .id(1L)
                .session("10x100m")
                .athlete("Max")
                .build();

        entries.add(max);

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());
        trainingPlan.setEntries(entries);

        Mockito.when(this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId())).thenReturn(trainingPlan);

        List<TrainingPlanEntry> trainingPlanEntries = this.getTrainingPlanService().getTrainingPlanEntries(this.getTestPersonDTO().getId());

        assertThat(trainingPlanEntries).isEqualTo(entries);

    }

    @Test
    public void whenTrainingPlanEntrisIsEmpty_thenReturnZero(){

        List<TrainingPlanEntryDTO> entries = new ArrayList<>();

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());
        trainingPlan.setEntries(entries);

        Mockito.when(this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId())).thenReturn(trainingPlan);

        List<TrainingPlanEntry> trainingPlanEntries = this.getTrainingPlanService().getTrainingPlanEntries(this.getTestPersonDTO().getId());

        assertThat(trainingPlanEntries.size()).isEqualTo(0);

    }

    @Test
    public void whenTrainiingPlanEntryisAdded_thenReturnAddedEntry(){

        MutableTrainingPlanEntry newEntry = new TrainingPlanEntryDTO();
        newEntry.setId(3L);
        newEntry.setSession("5x30m");
        newEntry.setDate(LocalDate.now());
        newEntry.setAthlete("Max");

        List<TrainingPlanEntryDTO> entries = new ArrayList<>();

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());
        trainingPlan.setEntries(entries);

        Mockito.when(this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId())).thenReturn(trainingPlan);

        this.getTrainingPlanService().addNewTrainingPlanEntry(this.testPersonDTO.getId(), newEntry);

        assertThat(trainingPlan.getEntries().get(0).getSession()).isEqualTo("5x30m");
        assertThat(trainingPlan.getEntries().get(0).getDate()).isEqualTo(LocalDate.now());
        assertThat(trainingPlan.getEntries().get(0).getAthlete()).isEqualTo("Max");


    }

    @Test
    public void whenTrainingPlanEntryIsDeleted_thenReturnEmptyEntries(){

        List<TrainingPlanEntryDTO> trainingPlanEntries = new ArrayList<>();

        TrainingPlanEntryDTO entry = new TrainingPlanEntryDTO();
        entry.setId(2L);
        entry.setSession("1x100m maximal");
        entry.setAthlete("Mustermann");
        entry.setDate(LocalDate.now());

        trainingPlanEntries.add(entry);

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());
        trainingPlan.setEntries(trainingPlanEntries);

        lenient().when(this.getTrainingPlanEntryRepository().findEntryById(2L)).thenReturn(entry);
        Mockito.when(this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId())).thenReturn(trainingPlan);

        this.getTrainingPlanService().deleteTrainingPlanEntry(this.testPersonDTO.getId(), entry.getId());

        assertThat(trainingPlan.getEntries().isEmpty()).isEqualTo(true);

    }

    @Test
    public void whenTrainingPlanEntryToDeleteIsNotThere_thenDeleteNothing(){

        List<TrainingPlanEntryDTO> trainingPlanEntries = new ArrayList<>();

        TrainingPlanEntryDTO entry = new TrainingPlanEntryDTO();
        entry.setId(2L);
        entry.setSession("1x100m maximal");
        entry.setAthlete("Mustermann");
        entry.setDate(LocalDate.now());

        trainingPlanEntries.add(entry);

        TrainingPlanDTO trainingPlan = new TrainingPlanDTO(this.getDiaryId());
        trainingPlan.setEntries(trainingPlanEntries);

        lenient().when(this.getTrainingPlanEntryRepository().findEntryById(2L)).thenReturn(entry);
        Mockito.when(this.getTrainingPlanRepository().findTrainingPlanById(this.getDiaryId())).thenReturn(trainingPlan);

        this.getTrainingPlanService().deleteTrainingPlanEntry(this.testPersonDTO.getId(), 5L);

        assertThat(trainingPlan.getEntries().size()).isEqualTo(1);

    }

}
