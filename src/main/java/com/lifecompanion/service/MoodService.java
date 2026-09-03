package com.lifecompanion.service;

import com.lifecompanion.dto.MoodCheckinDto;
import com.lifecompanion.model.MoodEntry;
import com.lifecompanion.repository.MoodEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MoodService {

    private final MoodEntryRepository moodRepository;

    private static final List<String> REFLECTION_PROMPTS = List.of(
        "What is taking up the most emotional space in your mind today?",
        "Name one small moment of comfort or peace you experienced recently.",
        "If you could speak to yourself with the kindness you give to a best friend, what would you say?",
        "What is one expectation you can release yourself from today?",
        "What is one sensory detail around you right now that feels pleasant or grounding?",
        "What does your mind and body need most in this moment: rest, connection, or quiet?",
        "Acknowledge one small thing you survived or navigated this week."
    );

    public MoodService(MoodEntryRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public MoodEntry recordMood(MoodCheckinDto dto) {
        MoodEntry entry = new MoodEntry();
        entry.setMoodScore(dto.getMoodScore() != null ? dto.getMoodScore() : 3);
        entry.setPrimaryEmotion(dto.getPrimaryEmotion() != null ? dto.getPrimaryEmotion() : "Reflective");
        
        if (dto.getEmotionTags() != null && !dto.getEmotionTags().isEmpty()) {
            entry.setEmotionTags(String.join(", ", dto.getEmotionTags()));
        } else {
            entry.setEmotionTags(dto.getPrimaryEmotion());
        }

        entry.setEnergyLevel(dto.getEnergyLevel() != null ? dto.getEnergyLevel() : 3);
        entry.setJournalNote(dto.getJournalNote());
        entry.setContextTrigger(dto.getContextTrigger());
        entry.setGentleInsight(generateGentleInsight(entry));

        return moodRepository.save(entry);
    }

    public List<MoodEntry> getRecentMoodEntries(int limit) {
        if (limit <= 7) {
            return moodRepository.findTop7ByOrderByTimestampDesc();
        }
        return moodRepository.findTop30ByOrderByTimestampDesc();
    }

    public List<MoodEntry> getAllMoodEntries() {
        return moodRepository.findAllByOrderByTimestampDesc();
    }

    public Optional<MoodEntry> getLatestMood() {
        return moodRepository.findTopByOrderByTimestampDesc();
    }

    public double getAverageMoodThisWeek() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<MoodEntry> weekEntries = moodRepository.findByTimestampBetweenOrderByTimestampAsc(sevenDaysAgo, LocalDateTime.now());
        if (weekEntries.isEmpty()) {
            return 0.0;
        }
        return weekEntries.stream().mapToInt(MoodEntry::getMoodScore).average().orElse(0.0);
    }

    public String getRandomReflectionPrompt() {
        Random random = new Random();
        return REFLECTION_PROMPTS.get(random.nextInt(REFLECTION_PROMPTS.size()));
    }

    private String generateGentleInsight(MoodEntry entry) {
        int score = entry.getMoodScore();
        if (score <= 2) {
            return "Honoring your low energy and heavy feelings without judgment is a profound act of self-care. Take things one gentle breath at a time.";
        } else if (score == 3) {
            return "Navigating an in-between or steady day. Remember that everyday balance is meaningful, and giving yourself steady grace is enough.";
        } else {
            return "Noticing positive moments anchors our emotional reserves. Savor this gentle warmth and let it nourish your day.";
        }
    }
}
