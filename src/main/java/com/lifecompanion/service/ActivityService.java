package com.lifecompanion.service;

import com.lifecompanion.model.MicroActivity;
import com.lifecompanion.repository.MicroActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final MicroActivityRepository activityRepository;

    public ActivityService(MicroActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<MicroActivity> getTodayActivities() {
        LocalDate today = LocalDate.now();
        List<MicroActivity> activities = activityRepository.findByTargetDate(today);
        if (activities.isEmpty()) {
            // Seed today's activities from template library
            seedDefaultActivitiesForDate(today);
            activities = activityRepository.findByTargetDate(today);
        }
        return activities;
    }

    public List<MicroActivity> getAllActivities() {
        return activityRepository.findAllByOrderByCompletedAscTargetDateDesc();
    }

    public Optional<MicroActivity> toggleActivityCompletion(Long id, String reflection) {
        Optional<MicroActivity> optional = activityRepository.findById(id);
        if (optional.isPresent()) {
            MicroActivity activity = optional.get();
            boolean newStatus = !activity.isCompleted();
            activity.setCompleted(newStatus);
            activity.setCompletedAt(newStatus ? LocalDateTime.now() : null);
            if (reflection != null && !reflection.isBlank()) {
                activity.setCompletionReflection(reflection);
            }
            activityRepository.save(activity);
            return Optional.of(activity);
        }
        return Optional.empty();
    }

    public MicroActivity createCustomActivity(String title, String description, String category, Integer durationMinutes, String icon) {
        MicroActivity activity = new MicroActivity();
        activity.setTitle(title != null ? title.trim() : "Gentle Step");
        activity.setDescription(description);
        activity.setCategory(category != null ? category.toUpperCase() : "SELF_CARE");
        activity.setDurationMinutes(durationMinutes != null ? durationMinutes : 3);
        activity.setIcon(icon != null ? icon : "sparkles");
        activity.setCustom(true);
        activity.setTargetDate(LocalDate.now());
        activity.setCompleted(false);
        return activityRepository.save(activity);
    }

    public long getCompletedCount() {
        return activityRepository.countByCompletedTrue();
    }

    public long getTotalCount() {
        return activityRepository.count();
    }

    public void seedDefaultActivitiesForDate(LocalDate date) {
        List<MicroActivity> defaults = List.of(
            new MicroActivity("Drink a warm cup of water or tea", "Sit down quietly and feel the warmth in your hands for 2 minutes.", "SELF_CARE", 2, "coffee", false),
            new MicroActivity("Open the window & look at the sky", "Take 3 deep breaths and look at the clouds, trees, or natural light.", "GROUNDING", 3, "sun", false),
            new MicroActivity("Send a gentle emoji or text", "Reach out to one person with a low-pressure 'thinking of you' or cute sticker.", "SOCIAL_WARMTH", 1, "heart", false),
            new MicroActivity("Gentle shoulder & neck release", "Slowly roll your shoulders backward 5 times and let your neck stretch gently.", "MOVEMENT", 2, "activity", false),
            new MicroActivity("Doodle or jot down 3 favorite words", "No artistic perfection needed—just let a pen flow on paper for a few moments.", "CREATIVITY", 4, "feather", false)
        );

        for (MicroActivity act : defaults) {
            act.setTargetDate(date);
            activityRepository.save(act);
        }
    }
}
