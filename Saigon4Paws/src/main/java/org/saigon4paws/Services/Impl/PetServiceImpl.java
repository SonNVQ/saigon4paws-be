package org.saigon4paws.Services.Impl;

import org.saigon4paws.Models.Pet;
import org.saigon4paws.Repositories.PetRepository;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;

    @Override
    public Page<Pet> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findAll(pageable);
    }
}
