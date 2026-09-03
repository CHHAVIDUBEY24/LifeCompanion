package com.lifecompanion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconnection_goals")
public class ReconnectionGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String targetName; // e.g. "College friend Sam", "Mom", "Local pottery club"

    /**
     * Category: FRIEND, FAMILY, COMMUNITY, NATURE, HOBBY
     */
    private String category;

    @Column(length = 1000)
    private String smallStep; // e.g. "Send a quick photo of my coffee", "Wave hello to neighbor"

    /**
     * Comfort level required: 1 (Very Low / Easy), 2 (Gentle Stretch), 3 (Bigger Step)
     */
    private Integer comfortLevel;

    /**
     * Status: NOT_STARTED, IN_PROGRESS, COMPLETED
     */
    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public ReconnectionGoal() {
        this.status = "NOT_STARTED";
        this.createdAt = LocalDateTime.now();
        this.comfortLevel = 1;
    }

    public ReconnectionGoal(String title, String targetName, String category, String smallStep, Integer comfortLevel) {
        this.title = title;
        this.targetName = targetName;
        this.category = category;
        this.smallStep = smallStep;
        this.comfortLevel = comfortLevel;
        this.status = "NOT_STARTED";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSmallStep() { return smallStep; }
    public void setSmallStep(String smallStep) { this.smallStep = smallStep; }

    public Integer getComfortLevel() { return comfortLevel; }
    public void setComfortLevel(Integer comfortLevel) { this.comfortLevel = comfortLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
