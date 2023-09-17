package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.VolunteerDTO;
import org.saigon4paws.Models.Volunteer;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Repositories.VolunteerRepository;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Services.VolunteerService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ReliefGroupService reliefGroupService;
    
    @Override
    public VolunteerDTO saveVolunteer(VolunteerDTO volunteerDTO) {
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(volunteerDTO.getReliefGroupId());
        Volunteer volunteer = Volunteer.builder()
                .fullName(volunteerDTO.getFullName())
                .dob(volunteerDTO.getDob())
                .phoneNumber(volunteerDTO.getPhoneNumber())
                .freeTime(volunteerDTO.getFreeTime())
                .address(volunteerDTO.getAddress())
                .reason(volunteerDTO.getReason())
                .reliefGroup(reliefGroup)
                .build();
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return getVolunteerDTO(savedVolunteer);
    }

    @Override
    public Page<Volunteer> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return volunteerRepository.findAll(pageable);
    }

    @Override
    public Volunteer getVolunteerById(Long id) {
        if (id == null)
            return null;
        return volunteerRepository.findById(id).orElse(null);
    }

    private VolunteerDTO getVolunteerDTO(Volunteer volunteer) {
        return VolunteerDTO.builder()
                .id(volunteer.getId())
                .fullName(volunteer.getFullName())
                .dob(volunteer.getDob())
                .phoneNumber(volunteer.getPhoneNumber())
                .freeTime(volunteer.getFreeTime())
                .address(volunteer.getAddress())
                .reason(volunteer.getReason())
                .createdAt(volunteer.getCreatedAt())
                .reliefGroupId(volunteer.getReliefGroup().getId())
                .build();
    }

    @Override
    public VolunteerDTO getVolunteerDTOById(Long id) {
        Volunteer volunteer = getVolunteerById(id);
        if (volunteer == null)
            return null;
        return getVolunteerDTO(volunteer);
    }

    @Override
    public VolunteerDTO updateVolunteer(Long id, VolunteerDTO petDTO) throws Exception {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer == null)
            throw new Exception("Volunteer not found!");
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(petDTO.getReliefGroupId());
        volunteer.setFullName(petDTO.getFullName());
        volunteer.setDob(petDTO.getDob());
        volunteer.setPhoneNumber(petDTO.getPhoneNumber());
        volunteer.setFreeTime(petDTO.getFreeTime());
        volunteer.setAddress(petDTO.getAddress());
        volunteer.setReason(petDTO.getReason());
        volunteer.setReliefGroup(reliefGroup);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return getVolunteerDTO(savedVolunteer);
    }

    @Override
    public void deleteVolunteer(Long id) throws Exception {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer == null)
            throw new Exception("Volunteer not found!");
        volunteerRepository.delete(volunteer);
    }

    @Override
    public VolunteerDTO changeVolunteerStatus(Long id, String status) throws Exception {
        Volunteer volunteer = getVolunteerById(id);
        if (volunteer == null)
            throw new Exception("Volunteer not found!");
        volunteer.setStatus(status);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return getVolunteerDTO(savedVolunteer);
    }
}
