package com.lifecompanion.controller;

import com.lifecompanion.dto.ChatRequestDto;
import com.lifecompanion.dto.ChatResponseDto;
import com.lifecompanion.model.ChatMessage;
import com.lifecompanion.repository.ChatMessageRepository;
import com.lifecompanion.service.GeminiAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompanionController {

    private final GeminiAiService aiService;
    private final ChatMessageRepository chatRepository;

    public CompanionController(GeminiAiService aiService, ChatMessageRepository chatRepository) {
        this.aiService = aiService;
        this.chatRepository = chatRepository;
    }

    @GetMapping("/companion")
    public String showCompanionPage(Model model) {
        List<ChatMessage> chatHistory = chatRepository.findAllByOrderByTimestampAsc();
        model.addAttribute("chatHistory", chatHistory);
        model.addAttribute("pageTitle", "AI Companion - LifeCompanion");
        model.addAttribute("activeTab", "companion");
        return "companion";
    }

    @PostMapping("/api/companion/chat")
    @ResponseBody
    public ResponseEntity<ChatResponseDto> handleChat(@RequestBody ChatRequestDto request) {
        ChatResponseDto response = aiService.processUserMessage(request.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/companion/clear")
    @ResponseBody
    public ResponseEntity<Void> clearHistory() {
        chatRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
