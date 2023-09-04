package org.saigon4paws.Repositories;

import org.saigon4paws.Models.ReliefGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliefGroupRepository extends JpaRepository<ReliefGroup, Integer> {
    ReliefGroup findByName(String name);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByBankAccountNumber(String bankAccountNumber);
}
