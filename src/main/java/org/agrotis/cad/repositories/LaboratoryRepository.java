package org.agrotis.cad.repositories;

import org.agrotis.cad.model.LaboratoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryEntity, Integer> { }
