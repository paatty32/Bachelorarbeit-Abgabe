package de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.repository;

import de.boadu.boafo.bachelorarbeit.web.club.portal.dao.group.GroupRequestsDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRequestRepository extends CrudRepository<GroupRequestsDTO, Long> {

    Long countRequestByAdminId(Long adminId);

    List<GroupRequestsDTO> getGroupRequestsByAdminId(Long adminId);

    List<GroupRequestsDTO> getGroupRequestsByRequesterIdAndGroupId(Long requesterId, Long groupId);

    Long countRequestByGroupId(Long groupId);

    GroupRequestsDTO getGroupRequestByRequesterIdAndGroupId(Long requesterId, Long groupRequestId);
}
