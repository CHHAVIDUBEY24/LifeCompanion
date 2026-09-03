package com.lifecompanion.controller;

import com.lifecompanion.dto.DashboardStatsDto;
import com.lifecompanion.service.ActivityService;
import com.lifecompanion.service.GroundingService;
import com.lifecompanion.service.MoodService;
import com.lifecompanion.service.ReconnectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MoodService moodService;
    private final ActivityService activityService;
    private final GroundingService groundingService;
    private final ReconnectionService reconnectionService;

    public DashboardController(MoodService moodService,
                               ActivityService activityService,
                               GroundingService groundingService,
                               ReconnectionService reconnectionService) {
        this.moodService = moodService;
        this.activityService = activityService;
        this.groundingService = groundingService;
        this.reconnectionService = reconnectionService;
    }

    @GetMapping("/")
    public String showDashboard(Model model) {
        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setLatestMood(moodService.getLatestMood().orElse(null));
        stats.setAverageMoodThisWeek(moodService.getAverageMoodThisWeek());
        stats.setTotalReflections(moodService.getAllMoodEntries().size());
        stats.setCompletedActivitiesCount(activityService.getCompletedCount());
        stats.setTotalActivitiesCount(activityService.getTotalCount());
        stats.setDailyAffirmation(groundingService.getRandomAffirmation());
        stats.setDailyPrompt(moodService.getRandomReflectionPrompt());
        stats.setTodayActivities(activityService.getTodayActivities());
        stats.setActiveGoals(reconnectionService.getActiveGoals());

        model.addAttribute("stats", stats);
        model.addAttribute("pageTitle", "Dashboard - LifeCompanion");
        model.addAttribute("activeTab", "dashboard");
        return "dashboard";
    }
}
