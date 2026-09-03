package com.lifecompanion.service;

import com.lifecompanion.model.GratitudeItem;
import com.lifecompanion.model.ReconnectionGoal;
import com.lifecompanion.repository.GratitudeItemRepository;
import com.lifecompanion.repository.ReconnectionGoalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReconnectionService {

    private final ReconnectionGoalRepository goalRepository;
    private final GratitudeItemRepository gratitudeRepository;

    public ReconnectionService(ReconnectionGoalRepository goalRepository, GratitudeItemRepository gratitudeRepository) {
        this.goalRepository = goalRepository;
        this.gratitudeRepository = gratitudeRepository;
    }

    public List<ReconnectionGoal> getAllGoals() {
        return goalRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<ReconnectionGoal> getActiveGoals() {
        return goalRepository.findByStatus("NOT_STARTED");
    }

    public ReconnectionGoal createGoal(String title, String targetName, String category, String smallStep, Integer comfortLevel) {
        ReconnectionGoal goal = new ReconnectionGoal(title, targetName, category, smallStep, comfortLevel != null ? comfortLevel : 1);
        return goalRepository.save(goal);
    }

    public Optional<ReconnectionGoal> updateGoalStatus(Long id, String status) {
        Optional<ReconnectionGoal> opt = goalRepository.findById(id);
        if (opt.isPresent()) {
            ReconnectionGoal goal = opt.get();
            goal.setStatus(status);
            if ("COMPLETED".equalsIgnoreCase(status)) {
                goal.setCompletedAt(LocalDateTime.now());
            }
            return Optional.of(goalRepository.save(goal));
        }
        return Optional.empty();
    }

    public List<GratitudeItem> getAllGratitudeItems() {
        return gratitudeRepository.findAllByOrderByCreatedAtDesc();
    }

    public GratitudeItem addGratitude(String content, String category) {
        GratitudeItem item = new GratitudeItem(content, category != null ? category : "SIMPLE_PLEASURE");
        return gratitudeRepository.save(item);
    }

    public Optional<GratitudeItem> toggleFavorite(Long id) {
        Optional<GratitudeItem> opt = gratitudeRepository.findById(id);
        if (opt.isPresent()) {
            GratitudeItem item = opt.get();
            item.setFavorite(!item.isFavorite());
            return Optional.of(gratitudeRepository.save(item));
        }
        return Optional.empty();
    }
}
