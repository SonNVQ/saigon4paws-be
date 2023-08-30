package org.saigon4paws.Repositories;

import org.saigon4paws.Models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
}
