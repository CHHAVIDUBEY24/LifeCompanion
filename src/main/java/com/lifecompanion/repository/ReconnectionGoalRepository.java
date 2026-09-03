package com.lifecompanion.repository;

import com.lifecompanion.model.ReconnectionGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReconnectionGoalRepository extends JpaRepository<ReconnectionGoal, Long> {
    List<ReconnectionGoal> findAllByOrderByCreatedAtDesc();
    List<ReconnectionGoal> findByStatus(String status);
}
