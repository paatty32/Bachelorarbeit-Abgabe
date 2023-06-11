package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group;

public interface MutableGroupRequest {

    //void setId(Long id);

    void setRequesterId(Long requesterId);

    void setName(String name);

    void setSurname(String surname);

    void setGroupId(Long id);

    void setAdminId(Long id);

    void setGroupName(String name);

}
