package de.boadu.boafo.bachelorarbeit.web.club.portal.service.appuser;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.repository.AppUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return this.getAppUserRepository().findPersonByEmail(mail);
    }
}
