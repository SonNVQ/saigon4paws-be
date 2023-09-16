package org.saigon4paws.Services;

import org.saigon4paws.DTO.PetDTO;
import org.saigon4paws.Models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    Page<Pet> findAll(int page, int size);

    Pet getPetById(Long id);

    PetDTO getPetDTOById(Long id);

    PetDTO createPet(PetDTO petDTO);

    PetDTO updatePet(Long id, PetDTO petDTO) throws Exception;

    void deletePet(Long id) throws Exception;

    String uploadPetPhoto(MultipartFile avatar) throws Exception;

    List<Long> getAllNonAdoptedPetId();
}
