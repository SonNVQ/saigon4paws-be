package org.saigon4paws.Services;

import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Adopter;
import org.springframework.data.domain.Page;

public interface AdopterService {
    AdopterDTO saveAdopter(AdopterDTO adopterDTO);

    Page<Adopter> findAll(int page, int size);

    Adopter getAdopterById(Long id);

    AdopterDTO getAdopterDTOById(Long id);

    AdopterDTO updateAdopter(Long id, AdopterDTO petDTO) throws Exception;

    void deleteAdopter(Long id) throws Exception;

    AdopterDTO changeAdoptStatus(Long id, String status) throws Exception;
}
