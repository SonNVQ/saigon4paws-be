package org.saigon4paws.Services;

import org.saigon4paws.DTO.VolunteerDTO;
import org.saigon4paws.Models.Volunteer;
import org.springframework.data.domain.Page;

public interface VolunteerService {
    VolunteerDTO saveVolunteer(VolunteerDTO volunteerDTO);

    Page<Volunteer> findAll(int page, int size);

    Volunteer getVolunteerById(Long id);

    VolunteerDTO getVolunteerDTOById(Long id);

    VolunteerDTO updateVolunteer(Long id, VolunteerDTO petDTO) throws Exception;

    void deleteVolunteer(Long id) throws Exception;

    VolunteerDTO changeVolunteerStatus(Long id, String status) throws Exception;
}
