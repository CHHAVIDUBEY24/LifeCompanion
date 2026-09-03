package com.lifecompanion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mood_entries")
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Mood rating from 1 (Very Low / Overwhelmed) to 5 (Peaceful / Thriving)
     */
    @Column(nullable = false)
    private Integer moodScore;

    /**
     * e.g. "Overwhelmed", "Lonely", "Anxious", "Calm", "Grateful", "Exhausted", "Hopeful"
     */
    private String primaryEmotion;

    /**
     * Comma-separated or tag list of secondary emotions
     */
    private String emotionTags;

    /**
     * Energy level from 1 (Drained) to 5 (Energized)
     */
    private Integer energyLevel;

    @Column(length = 2000)
    private String journalNote;

    /**
     * Triggers / context: e.g. "Work pressure", "Isolation", "Lack of sleep"
     */
    private String contextTrigger;

    /**
     * Gentle AI-generated or self-selected reflection insight
     */
    @Column(length = 1000)
    private String gentleInsight;

    public MoodEntry() {
        this.timestamp = LocalDateTime.now();
    }

    public MoodEntry(Integer moodScore, String primaryEmotion, String emotionTags, Integer energyLevel, String journalNote, String contextTrigger) {
        this.timestamp = LocalDateTime.now();
        this.moodScore = moodScore;
        this.primaryEmotion = primaryEmotion;
        this.emotionTags = emotionTags;
        this.energyLevel = energyLevel;
        this.journalNote = journalNote;
        this.contextTrigger = contextTrigger;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }

    public String getPrimaryEmotion() { return primaryEmotion; }
    public void setPrimaryEmotion(String primaryEmotion) { this.primaryEmotion = primaryEmotion; }

    public String getEmotionTags() { return emotionTags; }
    public void setEmotionTags(String emotionTags) { this.emotionTags = emotionTags; }

    public Integer getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(Integer energyLevel) { this.energyLevel = energyLevel; }

    public String getJournalNote() { return journalNote; }
    public void setJournalNote(String journalNote) { this.journalNote = journalNote; }

    public String getContextTrigger() { return contextTrigger; }
    public void setContextTrigger(String contextTrigger) { this.contextTrigger = contextTrigger; }

    public String getGentleInsight() { return gentleInsight; }
    public void setGentleInsight(String gentleInsight) { this.gentleInsight = gentleInsight; }
}
