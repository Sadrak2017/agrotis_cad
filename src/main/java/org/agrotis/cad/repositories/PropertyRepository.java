package org.agrotis.cad.repositories;

import org.agrotis.cad.model.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {
  List<PropertyEntity> findByDescriptionIn(List<String> descriptions);
}
