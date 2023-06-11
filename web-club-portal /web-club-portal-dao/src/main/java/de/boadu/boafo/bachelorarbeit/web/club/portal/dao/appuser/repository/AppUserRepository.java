package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserDTO, Long> {

    AppUserDTO save(AppUserDTO personDTO);

    AppUserDTO findPersonBySurname(String surname);

    AppUserDTO findPersonById(Long id);

    AppUserDTO findPersonByName(String name);

    AppUserDTO findPersonByEmail(String email);
}
