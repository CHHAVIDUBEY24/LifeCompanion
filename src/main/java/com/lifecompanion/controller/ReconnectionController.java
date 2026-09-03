package com.lifecompanion.controller;

import com.lifecompanion.model.GratitudeItem;
import com.lifecompanion.model.ReconnectionGoal;
import com.lifecompanion.service.ReconnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class ReconnectionController {

    private final ReconnectionService reconnectionService;

    public ReconnectionController(ReconnectionService reconnectionService) {
        this.reconnectionService = reconnectionService;
    }

    @GetMapping("/reconnect")
    public String showReconnection(Model model) {
        model.addAttribute("goals", reconnectionService.getAllGoals());
        model.addAttribute("gratitudeItems", reconnectionService.getAllGratitudeItems());
        model.addAttribute("pageTitle", "Gradual Reconnection - LifeCompanion");
        model.addAttribute("activeTab", "reconnect");
        return "reconnect";
    }

    @PostMapping("/api/reconnect/goals")
    @ResponseBody
    public ResponseEntity<ReconnectionGoal> createGoal(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String targetName = (String) payload.get("targetName");
        String category = (String) payload.get("category");
        String smallStep = (String) payload.get("smallStep");
        Integer comfortLevel = payload.get("comfortLevel") != null ? Integer.parseInt(payload.get("comfortLevel").toString()) : 1;

        ReconnectionGoal goal = reconnectionService.createGoal(title, targetName, category, smallStep, comfortLevel);
        return ResponseEntity.ok(goal);
    }

    @PostMapping("/api/reconnect/goals/{id}/status")
    @ResponseBody
    public ResponseEntity<ReconnectionGoal> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        Optional<ReconnectionGoal> updated = reconnectionService.updateGoalStatus(id, status);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/reconnect/gratitude")
    @ResponseBody
    public ResponseEntity<GratitudeItem> addGratitude(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        String category = payload.get("category");
        GratitudeItem item = reconnectionService.addGratitude(content, category);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/api/reconnect/gratitude/{id}/favorite")
    @ResponseBody
    public ResponseEntity<GratitudeItem> toggleFavorite(@PathVariable Long id) {
        Optional<GratitudeItem> updated = reconnectionService.toggleFavorite(id);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
