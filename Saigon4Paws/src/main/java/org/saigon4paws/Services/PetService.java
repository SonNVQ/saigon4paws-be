package org.saigon4paws.Services;

import org.saigon4paws.Models.Pet;
import org.springframework.data.domain.Page;

public interface PetService {
    Page<Pet> findAll(int page, int size);
}
