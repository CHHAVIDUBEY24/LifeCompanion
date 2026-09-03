package com.lifecompanion.dto;

import com.lifecompanion.model.MicroActivity;
import com.lifecompanion.model.MoodEntry;
import com.lifecompanion.model.ReconnectionGoal;
import java.util.List;

public class DashboardStatsDto {
    private MoodEntry latestMood;
    private double averageMoodThisWeek;
    private long totalReflections;
    private long completedActivitiesCount;
    private long totalActivitiesCount;
    private String dailyAffirmation;
    private String dailyPrompt;
    private List<MicroActivity> todayActivities;
    private List<ReconnectionGoal> activeGoals;

    public DashboardStatsDto() {}

    public MoodEntry getLatestMood() { return latestMood; }
    public void setLatestMood(MoodEntry latestMood) { this.latestMood = latestMood; }

    public double getAverageMoodThisWeek() { return averageMoodThisWeek; }
    public void setAverageMoodThisWeek(double averageMoodThisWeek) { this.averageMoodThisWeek = averageMoodThisWeek; }

    public long getTotalReflections() { return totalReflections; }
    public void setTotalReflections(long totalReflections) { this.totalReflections = totalReflections; }

    public long getCompletedActivitiesCount() { return completedActivitiesCount; }
    public void setCompletedActivitiesCount(long completedActivitiesCount) { this.completedActivitiesCount = completedActivitiesCount; }

    public long getTotalActivitiesCount() { return totalActivitiesCount; }
    public void setTotalActivitiesCount(long totalActivitiesCount) { this.totalActivitiesCount = totalActivitiesCount; }

    public String getDailyAffirmation() { return dailyAffirmation; }
    public void setDailyAffirmation(String dailyAffirmation) { this.dailyAffirmation = dailyAffirmation; }

    public String getDailyPrompt() { return dailyPrompt; }
    public void setDailyPrompt(String dailyPrompt) { this.dailyPrompt = dailyPrompt; }

    public List<MicroActivity> getTodayActivities() { return todayActivities; }
    public void setTodayActivities(List<MicroActivity> todayActivities) { this.todayActivities = todayActivities; }

    public List<ReconnectionGoal> getActiveGoals() { return activeGoals; }
    public void setActiveGoals(List<ReconnectionGoal> activeGoals) { this.activeGoals = activeGoals; }
}
