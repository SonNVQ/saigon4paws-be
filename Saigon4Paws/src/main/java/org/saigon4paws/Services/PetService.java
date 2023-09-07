package org.saigon4paws.Services;

import org.saigon4paws.DTO.PetDTO;
import org.saigon4paws.Models.Pet;
import org.springframework.data.domain.Page;

public interface PetService {
    Page<Pet> findAll(int page, int size);

    Pet getPetById(Long id);

    PetDTO getPetDTOById(Long id);

    PetDTO createPet(PetDTO petDTO);

    PetDTO updatePet(Long id, PetDTO petDTO) throws Exception;

    void deletePet(Long id) throws Exception;
}