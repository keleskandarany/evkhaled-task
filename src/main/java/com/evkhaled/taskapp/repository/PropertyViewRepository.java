package com.evkhaled.taskapp.repository;

import com.evkhaled.taskapp.entity.PropertyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyViewRepository extends JpaRepository<PropertyView, Long> {
    PropertyView findByPropertyIdAndUser(Long propertyId, String user);

    List<PropertyView> getByPropertyId(Long propertyId);

    @Query(value = "TRUNCATE TABLE property_views", nativeQuery = true)
    void truncate();
}