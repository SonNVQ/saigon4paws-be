package org.saigon4paws.Repositories;

import org.saigon4paws.Models.ReliefGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliefGroupRepository extends JpaRepository<ReliefGroup, Long> {
}
