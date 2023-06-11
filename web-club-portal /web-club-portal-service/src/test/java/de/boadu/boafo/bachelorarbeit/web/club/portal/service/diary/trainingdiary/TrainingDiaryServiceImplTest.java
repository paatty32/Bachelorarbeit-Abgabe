package de.boadu.boafo.bachelorarbeit.web.club.portal.service.diary.trainingdiary;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.DiaryId;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiary;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryDto;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntry;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.TrainingDiaryEntryDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryEntryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.roles.DiaryType;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser.AppUserService;
import de.boadu.boafo.bachelorarbeit.web.club.portal.service.trainingDiary.TrainingDiaryServiceImpl;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
@Getter
public class TrainingDiaryServiceImplTest {

    @Mock
    private TrainingsDiaryRepository trainingsDiaryRepository;

    @Mock
    private TrainingsDiaryEntryRepository trainingsDiaryEntryRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private TrainingDiaryServiceImpl trainingDiaryService;

    private AppUserDTO testUser;


    @BeforeEach
    public void setUp(){

         this.testUser = AppUserDTO.builder().id(3L)
                .name("Test")
                .surname("User")
                .build();

    }


    @Test
    public void whenTrainingDiaryByUserIsThere_thenReturnTrainingDiary(){

        DiaryId diaryId = DiaryId.builder()
                .userId(1L)
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryDto trainingDiary = new TrainingDiaryDto(diaryId);

        AppUserDTO personDTO = new AppUserDTO();
        personDTO.setId(1L);
        personDTO.setName("Mustermann");
        personDTO.setSurname("Max");

        Mockito.when(trainingsDiaryRepository.findTrainingDiaryById(diaryId)).thenReturn(trainingDiary);

        TrainingDiary foundTrainingDiary = this.trainingDiaryService.getTrainingsDiaryByUser(diaryId);

        assertThat(foundTrainingDiary).isNotNull();

    }

    @Test
    public void whenTrainingDiaryByUserIsNotThere_thenReturnNull(){

        DiaryId trainingDiaryIdByUser = DiaryId.builder()
                .userId(2L)
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiary notFoundDiary = this.trainingDiaryService.getTrainingsDiaryByUser(trainingDiaryIdByUser);

        assertThat(notFoundDiary).isNull();

    }

    @Test
    public void whenTrainingDiaryEntriesByUserIsThere_thenReturnEntries(){

        DiaryId traininingDiaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryEntryDTO trainEntry = TrainingDiaryEntryDTO.builder()
                .id(4L)
                .session("5x600m")
                .build();

        List<TrainingDiaryEntryDTO> trainEntries = new ArrayList<>();

        TrainingDiaryDto trainDiary = new TrainingDiaryDto(traininingDiaryId);
        trainDiary.setEntry(trainEntries);
        trainDiary.getEntry().add(trainEntry);

        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(traininingDiaryId)).thenReturn(trainDiary);

        List<TrainingDiaryEntry> trainingsDiaryEntriesByUser = this.getTrainingDiaryService().getTrainingsDiaryEntriesByUser(this.getTestUser().getId());

        assertThat(trainingsDiaryEntriesByUser).isEqualTo(trainEntries);

    }


    @Test
    public void whenTrainingDiaryEntriesIsNotThere_thenReturnEmpty(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryDto diary = new TrainingDiaryDto(diaryId);

        List<TrainingDiaryEntryDTO> entries = new ArrayList<>();

        diary.setEntry(entries);

        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(diaryId)).thenReturn(diary);

        List<TrainingDiaryEntry> emptyEntries = this.getTrainingDiaryService().getTrainingsDiaryEntriesByUser(this.getTestUser().getId());

        assertThat(emptyEntries).isEmpty();

    }

    @Test
    public void whenTrainingDiaryEntriesIsNull_thenReturnZero(){

        DiaryId diaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        List<TrainingDiaryEntryDTO> enrties = new ArrayList<>();

        TrainingDiaryDto diary = new TrainingDiaryDto(diaryId);
        diary.setEntry(enrties);

        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(diaryId)).thenReturn(diary);

        List<TrainingDiaryEntry> zeroEntries = this.getTrainingDiaryService().getTrainingsDiaryEntriesByUser(this.getTestUser().getId());

        assertThat(zeroEntries.size()).isEqualTo(0);

    }

    @Test
    public void whenTrainingDiaryEntryIsDeleted_thenReturnDiaryWithOutEntry(){

        DiaryId traininingDiaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryEntryDTO trainEntry = TrainingDiaryEntryDTO.builder()
                .id(4L)
                .session("5x600m")
                .build();

        TrainingDiaryEntryDTO trainEntry2 = TrainingDiaryEntryDTO.builder()
                .id(5L)
                .session("5x800m")
                .build();

        List<TrainingDiaryEntryDTO> trainEntries = new ArrayList<>();

        TrainingDiaryDto trainDiary = new TrainingDiaryDto(traininingDiaryId);
        trainDiary.setEntry(trainEntries);
        trainDiary.getEntry().add(trainEntry);
        trainDiary.getEntry().add(trainEntry2);

        Set<AppUser> userTrainer = new HashSet<>();

        lenient().when(this.getTrainingsDiaryEntryRepository().findEntryById(4L)).thenReturn(trainEntry2);
        lenient().when(this.getTrainingsDiaryEntryRepository().findEntryById(5L)).thenReturn(trainEntry2);
        lenient().when(this.getAppUserService().getUserTrainer(3L)).thenReturn(userTrainer);
        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(traininingDiaryId)).thenReturn(trainDiary);

        this.getTrainingDiaryService().deleteEntry(this.getTestUser().getId(), 5L );

        assertThat(trainDiary.getEntry().contains(trainEntry)).isEqualTo(true);
        assertThat(trainDiary.getEntry().contains(trainEntry2)).isEqualTo(false);

    }

    @Test
    public void whenTrainingDiaryToDeleteIsNotThere_thenDeleteNothing(){

        DiaryId traininingDiaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryEntryDTO trainEntry = TrainingDiaryEntryDTO.builder()
                .id(4L)
                .session("5x600m")
                .build();

        TrainingDiaryEntryDTO trainEntry2 = TrainingDiaryEntryDTO.builder()
                .id(5L)
                .session("5x800m")
                .build();

        List<TrainingDiaryEntryDTO> trainEntries = new ArrayList<>();

        TrainingDiaryDto trainDiary = new TrainingDiaryDto(traininingDiaryId);
        trainDiary.setEntry(trainEntries);
        trainDiary.getEntry().add(trainEntry);
        trainDiary.getEntry().add(trainEntry2);

        lenient().when(this.getTrainingsDiaryEntryRepository().findEntryById(4L)).thenReturn(trainEntry2);
        lenient().when(this.getTrainingsDiaryEntryRepository().findEntryById(5L)).thenReturn(trainEntry2);
        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(traininingDiaryId)).thenReturn(trainDiary);

        this.getTrainingDiaryService().deleteEntry(this.getTestUser().getId(), 10L );

        assertThat(trainDiary.getEntry().contains(trainEntry)).isEqualTo(true);
        assertThat(trainDiary.getEntry().contains(trainEntry2)).isEqualTo(true);


    }

    @Test
    public void whenAddNewTrainingDiaryEntry_thenReturnEntry(){

        DiaryId traininingDiaryId = DiaryId.builder()
                .userId(this.getTestUser().getId())
                .diaryType(DiaryType.TRAINING)
                .build();

        TrainingDiaryEntryDTO trainEntry = TrainingDiaryEntryDTO.builder()
                .id(4L)
                .session("5x600m")
                .build();

        List<TrainingDiaryEntryDTO> trainEntries = new ArrayList<>();

        TrainingDiaryDto trainDiary = new TrainingDiaryDto(traininingDiaryId);
        trainDiary.setEntry(trainEntries);

        Mockito.when(this.getTrainingsDiaryRepository().findTrainingDiaryById(traininingDiaryId)).thenReturn(trainDiary);

        this.getTrainingDiaryService().addNewTrainingDiaryEntry(this.getTestUser().getId(), trainEntry);

        assertThat(trainDiary.getEntry().contains(trainEntry)).isEqualTo(true);

    }


}
