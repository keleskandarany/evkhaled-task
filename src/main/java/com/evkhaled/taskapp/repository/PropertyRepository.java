package com.evkhaled.taskapp.repository;

import com.evkhaled.taskapp.dto.PropertySummaryDTO;
import com.evkhaled.taskapp.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT new com.evkhaled.taskapp.dto.PropertySummaryDTO(p.id, p.name, p.price, p.image) FROM Property p")
    List<PropertySummaryDTO> getAllWithoutDetails();

    @Query(value = "TRUNCATE TABLE properties", nativeQuery = true)
    void truncate();
}