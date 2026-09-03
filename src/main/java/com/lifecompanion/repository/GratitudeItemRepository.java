package com.lifecompanion.repository;

import com.lifecompanion.model.GratitudeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GratitudeItemRepository extends JpaRepository<GratitudeItem, Long> {
    List<GratitudeItem> findAllByOrderByCreatedAtDesc();
    List<GratitudeItem> findByFavoriteTrueOrderByCreatedAtDesc();
}
