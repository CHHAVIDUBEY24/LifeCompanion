package com.lifecompanion.service;

import com.lifecompanion.dto.ChatResponseDto;
import com.lifecompanion.model.ChatMessage;
import com.lifecompanion.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class GeminiAiService {

    private final ChatMessageRepository chatRepository;
    private final RestTemplate restTemplate;

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Value("${gemini.api.model:gemini-2.5-flash}")
    private String modelName;

    // Strict crisis detection keywords
    private static final Pattern CRISIS_PATTERN = Pattern.compile(
        "\\b(suicide|kill myself|end my life|want to die|hurt myself|self harm|hang myself|slit my wrist|no reason to live|better off dead)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final String SYSTEM_PROMPT = """
        You are 'LifeCompanion', an exceptionally warm, empathetic, and gentle emotional companion.
        Your goal is to support people who feel lonely, overwhelmed, anxious, or down.
        Key guidelines:
        1. Always validate emotions with genuine kindness before offering any perspective.
        2. Keep tone soothing, warm, non-judgmental, and unhurried.
        3. Never lecture, minimize, or give overwhelming to-do lists.
        4. Suggest only tiny, low-pressure micro-actions if appropriate (e.g. taking a sip of water, loosening shoulders).
        5. Keep responses concise to moderate in length (2-4 brief paragraphs) so they are easy to read for someone feeling drained.
        """;

    public GeminiAiService(ChatMessageRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.restTemplate = new RestTemplate();
    }

    public ChatResponseDto processUserMessage(String userText) {
        String cleanText = userText != null ? userText.trim() : "";
        if (cleanText.isEmpty()) {
            return new ChatResponseDto("I am right here with you whenever you are ready to talk.", false, null, formatNow());
        }

        // 1. Save User Message
        ChatMessage userMsg = new ChatMessage("user", cleanText, false);
        chatRepository.save(userMsg);

        // 2. Check for Crisis Indicators
        if (isCrisis(cleanText)) {
            String crisisResponse = """
                I hear how deeply painful things are for you right now, and I want you to know you don't have to carry this alone. Please reach out to someone who can support you safely:

                • **National Suicide & Crisis Lifeline**: Call or text **988** (Available 24/7, free & confidential in the US/Canada)
                • **Crisis Text Line**: Text **HOME to 741741**
                • **India Tele-MANAS**: Call **14416** or **1800-891-4416** (24/7 Toll-Free)
                • **International Helplines**: [Find a Helpline](https://findahelpline.com)

                Please consider reaching out to a trusted loved one, doctor, or calling one of the free services above right now. Your life and wellbeing matter deeply.
                """;

            ChatMessage assistantMsg = new ChatMessage("assistant", crisisResponse, true);
            assistantMsg.setSuggestedAction("Emergency Helplines & Grounding");
            chatRepository.save(assistantMsg);

            return new ChatResponseDto(crisisResponse, true, "Emergency Helplines & Grounding", formatNow());
        }

        // 3. Generate AI Response (Gemini API or Empathetic Fallback Engine)
        String aiReply;
        String suggestedAction = extractGentleSuggestion(cleanText);

        if (geminiApiKey != null && !geminiApiKey.isBlank()) {
            try {
                aiReply = callGeminiApi(cleanText);
            } catch (Exception ex) {
                aiReply = generateEmpatheticFallbackResponse(cleanText);
            }
        } else {
            aiReply = generateEmpatheticFallbackResponse(cleanText);
        }

        // 4. Save Assistant Message
        ChatMessage assistantMsg = new ChatMessage("assistant", aiReply, false);
        assistantMsg.setSuggestedAction(suggestedAction);
        chatRepository.save(assistantMsg);

        return new ChatResponseDto(aiReply, false, suggestedAction, formatNow());
    }

    private boolean isCrisis(String text) {
        return CRISIS_PATTERN.matcher(text).find();
    }

    private String callGeminiApi(String userText) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent?key=" + geminiApiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Fetch recent context (last 6 messages)
        List<ChatMessage> recent = chatRepository.findTop50ByOrderByTimestampDesc();
        Collections.reverse(recent);

        List<Map<String, Object>> contents = new ArrayList<>();

        // Add System / Framing instruction
        Map<String, Object> systemPart = Map.of("text", SYSTEM_PROMPT);
        Map<String, Object> systemContent = Map.of("role", "user", "parts", List.of(systemPart));
        contents.add(systemContent);

        Map<String, Object> systemAck = Map.of("role", "model", "parts", List.of(Map.of("text", "I understand. I am ready to be a compassionate, comforting companion.")));
        contents.add(systemAck);

        for (ChatMessage m : recent) {
            String role = "user".equalsIgnoreCase(m.getRole()) ? "user" : "model";
            contents.add(Map.of("role", role, "parts", List.of(Map.of("text", m.getContent()))));
        }

        Map<String, Object> body = Map.of("contents", contents);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(url, request, Map.class);
        if (response != null && response.containsKey("candidates")) {
            List candidates = (List) response.get("candidates");
            if (!candidates.isEmpty()) {
                Map first = (Map) candidates.get(0);
                Map content = (Map) first.get("content");
                List parts = (List) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    Map firstPart = (Map) parts.get(0);
                    return (String) firstPart.get("text");
                }
            }
        }
        return generateEmpatheticFallbackResponse(userText);
    }

    /**
     * Sophisticated built-in empathetic response engine when offline or no API key
     */
    private String generateEmpatheticFallbackResponse(String text) {
        String lower = text.toLowerCase();

        if (lower.contains("lonely") || lower.contains("alone") || lower.contains("nobody")) {
            return "Feeling lonely can be such a heavy and quiet weight to carry. I want to gently remind you that having these feelings is completely human, and right now in this space, you are seen and heard.\n\n" +
                   "You don't have to fix everything or force yourself into big social situations today. Even taking one slow breath or sitting by a window with a warm drink can be a gentle way of holding space for yourself. Would you like to talk about what has been on your mind, or would you prefer a peaceful grounding exercise?";
        }

        if (lower.contains("overwhelm") || lower.contains("too much") || lower.contains("stress") || lower.contains("anxious") || lower.contains("panic")) {
            return "It sounds like there is a lot pulling at your energy right now, and that feeling of overwhelm is completely valid. When everything feels like it's piling up, it is okay to pause and set down the invisible weight for a moment.\n\n" +
                   "Let's gently take one small step back together. Can you drop your shoulders away from your ears and unclench your jaw? We don't have to solve tomorrow or even the next hour—just focus on this single moment.";
        }

        if (lower.contains("tired") || lower.contains("exhausted") || lower.contains("drained") || lower.contains("sleep")) {
            return "Emotional and physical exhaustion go hand in hand. Please be extra gentle with yourself today. You are allowed to rest without feeling guilty or needing to justify it.\n\n" +
                   "If you can, try to give yourself permission to do the bare minimum today. What is one small comfort you could offer yourself right now, like curling under a soft blanket or dimming the lights?";
        }

        if (lower.contains("thank") || lower.contains("helpful") || lower.contains("better")) {
            return "I am so glad to be here with you. Acknowledging even small moments of relief or connection is a meaningful step forward. Remember to give yourself credit for showing up for yourself today.";
        }

        if (lower.contains("sad") || lower.contains("crying") || lower.contains("hopeless") || lower.contains("down")) {
            return "I'm sending you warmth. Sadness asks for kindness, not pressure. It's okay to feel whatever is here right now without having to rush out of it.\n\n" +
                   "I am right here with you. What is one gentle thing your heart or body is needing most in this moment?";
        }

        return "Thank you for sharing that with me. It takes courage to open up and reflect on how you're feeling.\n\n" +
               "Take your time, breathe at your own pace, and remember there's no right or wrong way to feel. What part of your day has felt the heaviest, or what would bring you even a tiny slice of ease right now?";
    }

    private String extractGentleSuggestion(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("anxious") || lower.contains("panic") || lower.contains("overwhelm")) {
            return "Box Breathing & 5-4-3-2-1 Reset";
        }
        if (lower.contains("lonely") || lower.contains("alone")) {
            return "Gentle Reconnection Step";
        }
        if (lower.contains("tired") || lower.contains("exhausted")) {
            return "Micro-Rest & Warm Drink";
        }
        return "Daily Check-in Reflection";
    }

    private String formatNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
