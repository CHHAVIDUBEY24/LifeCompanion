package com.lifecompanion.controller;

import com.lifecompanion.dto.MoodCheckinDto;
import com.lifecompanion.model.MoodEntry;
import com.lifecompanion.service.MoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReflectionController {

    private final MoodService moodService;

    public ReflectionController(MoodService moodService) {
        this.moodService = moodService;
    }

    @GetMapping("/reflections")
    public String showReflections(Model model) {
        model.addAttribute("recentEntries", moodService.getAllMoodEntries());
        model.addAttribute("randomPrompt", moodService.getRandomReflectionPrompt());
        model.addAttribute("latestMood", moodService.getLatestMood().orElse(null));
        model.addAttribute("weekAverage", moodService.getAverageMoodThisWeek());
        model.addAttribute("pageTitle", "Self-Reflection & Mood - LifeCompanion");
        model.addAttribute("activeTab", "reflections");
        return "reflections";
    }

    @PostMapping("/reflections/checkin")
    public String submitFormCheckin(@ModelAttribute MoodCheckinDto dto) {
        moodService.recordMood(dto);
        return "redirect:/reflections";
    }

    @PostMapping("/api/reflections/checkin")
    @ResponseBody
    public ResponseEntity<MoodEntry> submitApiCheckin(@RequestBody MoodCheckinDto dto) {
        MoodEntry saved = moodService.recordMood(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/api/reflections/trends")
    @ResponseBody
    public ResponseEntity<List<MoodEntry>> getMoodTrends() {
        return ResponseEntity.ok(moodService.getRecentMoodEntries(14));
    }
}
