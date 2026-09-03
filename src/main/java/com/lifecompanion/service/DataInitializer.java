package com.lifecompanion.service;

import com.lifecompanion.model.GratitudeItem;
import com.lifecompanion.model.MoodEntry;
import com.lifecompanion.model.ReconnectionGoal;
import com.lifecompanion.repository.GratitudeItemRepository;
import com.lifecompanion.repository.MoodEntryRepository;
import com.lifecompanion.repository.ReconnectionGoalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ActivityService activityService;
    private final MoodEntryRepository moodRepository;
    private final ReconnectionGoalRepository goalRepository;
    private final GratitudeItemRepository gratitudeRepository;

    public DataInitializer(ActivityService activityService,
                           MoodEntryRepository moodRepository,
                           ReconnectionGoalRepository goalRepository,
                           GratitudeItemRepository gratitudeRepository) {
        this.activityService = activityService;
        this.moodRepository = moodRepository;
        this.goalRepository = goalRepository;
        this.gratitudeRepository = gratitudeRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Activities for today
        activityService.seedDefaultActivitiesForDate(LocalDate.now());

        // 2. Seed initial mood history if empty
        if (moodRepository.count() == 0) {
            MoodEntry m1 = new MoodEntry(2, "Overwhelmed", "Anxious, Exhausted", 2, "Work was very loud and busy today. Felt hard to concentrate.", "Work deadline");
            m1.setTimestamp(LocalDateTime.now().minusDays(3));
            m1.setGentleInsight("Honoring your low energy is an act of kindness.");
            moodRepository.save(m1);

            MoodEntry m2 = new MoodEntry(3, "Calm", "Reflective, Peaceful", 3, "Went for a short walk after sunset. Air was cool and quiet.", "Nature walk");
            m2.setTimestamp(LocalDateTime.now().minusDays(2));
            m2.setGentleInsight("Gentle movement helps settle an overwhelmed mind.");
            moodRepository.save(m2);

            MoodEntry m3 = new MoodEntry(4, "Hopeful", "Grateful, Connected", 4, "Had a warm cup of herbal tea and called my sibling. Felt heard.", "Social conversation");
            m3.setTimestamp(LocalDateTime.now().minusDays(1));
            m3.setGentleInsight("Small connections build lasting emotional comfort.");
            moodRepository.save(m3);
        }

        // 3. Seed initial Reconnection Goals if empty
        if (goalRepository.count() == 0) {
            ReconnectionGoal g1 = new ReconnectionGoal(
                "Say a warm hello to a quiet friend",
                "Old college roommate / close friend",
                "FRIEND",
                "Send a funny photo or simple 'thinking of you, no need to reply quickly!' message",
                1
            );
            goalRepository.save(g1);

            ReconnectionGoal g2 = new ReconnectionGoal(
                "Visit a peaceful local spot",
                "Neighborhood park / quiet library",
                "COMMUNITY",
                "Sit on a bench with a warm beverage for 10 minutes and watch the world go by gently",
                2
            );
            goalRepository.save(g2);
        }

        // 4. Seed Gratitude Items if empty
        if (gratitudeRepository.count() == 0) {
            GratitudeItem item1 = new GratitudeItem("The smell of rain on dry ground (petrichor)", "NATURE");
            item1.setFavorite(true);
            gratitudeRepository.save(item1);

            GratitudeItem item2 = new GratitudeItem("A soft warm blanket on a cool morning", "COMFORT_OBJECT");
            item2.setFavorite(true);
            gratitudeRepository.save(item2);

            GratitudeItem item3 = new GratitudeItem("A kind cashier who smiled and wished me a gentle day", "KIND_WORD");
            gratitudeRepository.save(item3);
        }
    }
}
