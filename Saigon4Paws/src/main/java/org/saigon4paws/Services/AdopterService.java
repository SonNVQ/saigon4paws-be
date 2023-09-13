package org.saigon4paws.Services;

import org.saigon4paws.DTO.AdopterDTO;

public interface AdopterService {
    AdopterDTO saveAdopter(AdopterDTO adopterDTO);

    AdopterDTO getAdopterById(Long id);
}
