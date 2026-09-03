package com.lifecompanion.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "micro_activities")
public class MicroActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    /**
     * Category: SELF_CARE, GROUNDING, SOCIAL_WARMTH, MOVEMENT, CREATIVITY
     */
    @Column(nullable = false)
    private String category;

    /**
     * Duration in minutes (usually small: 1 to 10 mins)
     */
    private Integer durationMinutes;

    /**
     * Icon identifier (lucide icon name or emoji)
     */
    private String icon;

    private boolean completed;

    private LocalDateTime completedAt;

    /**
     * Target date for daily activities
     */
    private LocalDate targetDate;

    /**
     * Whether this was created by the user or default seeded
     */
    private boolean custom;

    /**
     * Reflection note after finishing the activity
     */
    private String completionReflection;

    public MicroActivity() {
        this.completed = false;
        this.targetDate = LocalDate.now();
    }

    public MicroActivity(String title, String description, String category, Integer durationMinutes, String icon, boolean custom) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.durationMinutes = durationMinutes;
        this.icon = icon;
        this.custom = custom;
        this.completed = false;
        this.targetDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public boolean isCustom() { return custom; }
    public void setCustom(boolean custom) { this.custom = custom; }

    public String getCompletionReflection() { return completionReflection; }
    public void setCompletionReflection(String completionReflection) { this.completionReflection = completionReflection; }
}
