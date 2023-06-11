package de.boadu.boafo.bachelorarbeit.web.club.portal.config.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.appuser.AppUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SecurityService {

    private static final String LOGOUT_SUCCESS_URL = "/";

    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        // Anonymous or no authentication.
        return null;
    }

    private Collection<? extends GrantedAuthority> getUserAuthority(){

        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        if( principal instanceof UserDetails){

            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();

            return authorities;

        }

        return null;

    }

    public AppUserDTO getLoggedUser(){

        UserDetails authenticatedUser = this.getAuthenticatedUser();

        return (AppUserDTO) authenticatedUser;

    }

    public List<String> getUserRoles(){

        List<String> userRoles = new ArrayList<>();

        Collection<? extends GrantedAuthority> userAuthority = this.getUserAuthority();

        if(userAuthority != null) {
            for (GrantedAuthority authority : userAuthority) {

                userRoles.add(authority.toString());

            }
        }

        return userRoles;
    }

    public Long getUserId(){

        UserDetails authenticatedUser = this.getAuthenticatedUser();

        AppUserDTO currentUser = (AppUserDTO) authenticatedUser;

        return currentUser.getId();

    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }
}