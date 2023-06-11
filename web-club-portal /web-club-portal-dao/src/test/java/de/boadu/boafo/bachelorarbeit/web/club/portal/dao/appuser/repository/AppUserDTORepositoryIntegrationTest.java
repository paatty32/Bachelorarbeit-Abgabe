package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppUserDTORepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void whenFindByName_thenReturnPerson(){

        AppUserDTO patrick = new AppUserDTO();
        patrick.setName("Patrick");

        testEntityManager.persist(patrick);
        testEntityManager.flush();

        AppUserDTO found = appUserRepository.findPersonByName("Patrick");

        assertThat(found.getName()).isEqualTo(patrick.getName());

    }

    @Test
    public void whenNotFindByName_thenReturnNull(){

        AppUserDTO found = appUserRepository.findPersonByName("Test");

        assertThat(found).isNull();

    }

    @Test
    public void whenFindBySurname_thenReturnPersonSurname(){

        AppUserDTO surname = new AppUserDTO();
        surname.setName("Mustermann");
        surname.setSurname("Max");

        testEntityManager.persist(surname);
        testEntityManager.flush();

        AppUserDTO foundPersonDTO = this.appUserRepository.findPersonBySurname("Max");

        assertThat(foundPersonDTO.getSurname()).isEqualTo(surname.getSurname());

    }

    @Test
    public void whenNotFindBySurname_thenReturnNull(){

        AppUserDTO notFoundPersonDTO = this.appUserRepository.findPersonBySurname("NotFoundSurname");

        assertThat(notFoundPersonDTO).isNull();

    }

    @Test
    public void whenSave_thenReturnPerson(){

        AppUserDTO newPersonDTO = new AppUserDTO();
        newPersonDTO.setName("Mustermann");
        newPersonDTO.setSurname("Max");

        AppUserDTO savedPersonDTO = this.appUserRepository.save(newPersonDTO);

        assertThat(savedPersonDTO).hasFieldOrPropertyWithValue("name", "Mustermann");
        assertThat(savedPersonDTO).hasFieldOrPropertyWithValue("surname", "Max");


    }



}
