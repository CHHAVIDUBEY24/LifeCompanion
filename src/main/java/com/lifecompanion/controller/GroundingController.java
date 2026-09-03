package com.lifecompanion.controller;

import com.lifecompanion.service.GroundingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroundingController {

    private final GroundingService groundingService;

    public GroundingController(GroundingService groundingService) {
        this.groundingService = groundingService;
    }

    @GetMapping("/grounding")
    public String showGroundingSuite(Model model) {
        model.addAttribute("sensorySteps", groundingService.getSensorySteps());
        model.addAttribute("affirmations", groundingService.getAllAffirmations());
        model.addAttribute("randomAffirmation", groundingService.getRandomAffirmation());
        model.addAttribute("pageTitle", "Mindful Grounding - LifeCompanion");
        model.addAttribute("activeTab", "grounding");
        return "grounding";
    }
}
