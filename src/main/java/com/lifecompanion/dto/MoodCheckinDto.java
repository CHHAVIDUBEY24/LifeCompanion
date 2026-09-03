package com.lifecompanion.dto;

import java.util.List;

public class MoodCheckinDto {
    private Integer moodScore;
    private String primaryEmotion;
    private List<String> emotionTags;
    private Integer energyLevel;
    private String journalNote;
    private String contextTrigger;

    public MoodCheckinDto() {}

    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }

    public String getPrimaryEmotion() { return primaryEmotion; }
    public void setPrimaryEmotion(String primaryEmotion) { this.primaryEmotion = primaryEmotion; }

    public List<String> getEmotionTags() { return emotionTags; }
    public void setEmotionTags(List<String> emotionTags) { this.emotionTags = emotionTags; }

    public Integer getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(Integer energyLevel) { this.energyLevel = energyLevel; }

    public String getJournalNote() { return journalNote; }
    public void setJournalNote(String journalNote) { this.journalNote = journalNote; }

    public String getContextTrigger() { return contextTrigger; }
    public void setContextTrigger(String contextTrigger) { this.contextTrigger = contextTrigger; }
}
