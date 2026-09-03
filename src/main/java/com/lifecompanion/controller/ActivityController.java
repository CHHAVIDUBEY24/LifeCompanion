package com.lifecompanion.controller;

import com.lifecompanion.model.MicroActivity;
import com.lifecompanion.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String showActivities(Model model) {
        List<MicroActivity> todayActivities = activityService.getTodayActivities();
        model.addAttribute("todayActivities", todayActivities);
        model.addAttribute("completedCount", activityService.getCompletedCount());
        model.addAttribute("totalCount", activityService.getTotalCount());
        model.addAttribute("pageTitle", "Achievable Quests - LifeCompanion");
        model.addAttribute("activeTab", "activities");
        return "activities";
    }

    @PostMapping("/api/activities/{id}/toggle")
    @ResponseBody
    public ResponseEntity<MicroActivity> toggleActivity(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        String reflection = body != null ? body.get("reflection") : null;
        Optional<MicroActivity> updated = activityService.toggleActivityCompletion(id, reflection);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/activities/create")
    @ResponseBody
    public ResponseEntity<MicroActivity> createActivity(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        String category = (String) payload.get("category");
        Integer duration = payload.get("duration") != null ? Integer.parseInt(payload.get("duration").toString()) : 3;
        String icon = (String) payload.getOrDefault("icon", "sparkles");

        MicroActivity activity = activityService.createCustomActivity(title, description, category, duration, icon);
        return ResponseEntity.ok(activity);
    }
}
