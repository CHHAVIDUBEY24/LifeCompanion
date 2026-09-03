package com.lifecompanion.repository;

import com.lifecompanion.model.MicroActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MicroActivityRepository extends JpaRepository<MicroActivity, Long> {
    List<MicroActivity> findByTargetDate(LocalDate targetDate);
    List<MicroActivity> findByCategory(String category);
    List<MicroActivity> findByCompletedTrue();
    long countByCompletedTrue();
    List<MicroActivity> findAllByOrderByCompletedAscTargetDateDesc();
}
