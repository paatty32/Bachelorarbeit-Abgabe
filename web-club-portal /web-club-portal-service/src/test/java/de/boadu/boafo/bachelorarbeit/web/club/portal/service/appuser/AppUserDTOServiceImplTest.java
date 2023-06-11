package de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.competition.repository.CompetitionDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.training.repository.TrainingsDiaryRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.diary.trainingplan.repository.TrainingPlanRepository;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.MutableAppUser;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AppUserDTOServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private CompetitionDiaryRepository competitionDiaryRepository;

    @Mock
    private TrainingsDiaryRepository trainingsDiaryRepository;

    @Mock
    private TrainingPlanRepository trainingPlanRepository;

    @InjectMocks
    private AppUserServiceImpl personService;

    @Test
    public void whenUserIsCreated_thenReturnUser() throws Exception {

        Set<String> clickedRoles = new HashSet<>();
        clickedRoles.add("Athlet");

        MutableAppUser person = new AppUserDTO();
        person.setName("Mustermann");
        person.setSurname("Max");

        Mockito.when(appUserRepository.save((AppUserDTO) person)).thenReturn((AppUserDTO) person);

        AppUserDTO createdPersonDTO = this.personService.createUser(person, clickedRoles);

        assertThat(createdPersonDTO).hasFieldOrPropertyWithValue("name", "Mustermann");
        assertThat(createdPersonDTO).hasFieldOrPropertyWithValue("surname", "Max");

    }

}


