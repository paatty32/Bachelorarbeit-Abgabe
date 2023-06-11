package de.boadu.boafo.bachelorarbeit.web.club.portal.ui.component;

public interface RefreshableComponent <T>{

    void refreshGrid(T data);

    void clearData();

}
