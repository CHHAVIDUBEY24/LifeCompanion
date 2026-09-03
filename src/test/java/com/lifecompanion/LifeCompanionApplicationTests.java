package com.lifecompanion;

import com.lifecompanion.dto.ChatResponseDto;
import com.lifecompanion.dto.MoodCheckinDto;
import com.lifecompanion.model.MicroActivity;
import com.lifecompanion.model.MoodEntry;
import com.lifecompanion.service.ActivityService;
import com.lifecompanion.service.GeminiAiService;
import com.lifecompanion.service.GroundingService;
import com.lifecompanion.service.MoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LifeCompanionApplicationTests {

	@Autowired
	private GeminiAiService aiService;

	@Autowired
	private MoodService moodService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private GroundingService groundingService;

	@Test
	void contextLoads() {
		assertNotNull(aiService);
		assertNotNull(moodService);
		assertNotNull(activityService);
		assertNotNull(groundingService);
	}

	@Test
	void testEmpatheticCompanionResponse() {
		ChatResponseDto res = aiService.processUserMessage("I'm feeling very lonely and overwhelmed today.");
		assertNotNull(res);
		assertNotNull(res.getReply());
		assertFalse(res.isCrisisAlert());
		assertTrue(res.getReply().toLowerCase().contains("lonely") || res.getReply().toLowerCase().contains("kindness") || res.getReply().toLowerCase().contains("breath"));
	}

	@Test
	void testCrisisGuardrailDetection() {
		ChatResponseDto res = aiService.processUserMessage("I want to end my life and hurt myself.");
		assertNotNull(res);
		assertTrue(res.isCrisisAlert());
		assertTrue(res.getReply().contains("988") || res.getReply().contains("Crisis"));
	}

	@Test
	void testMoodCheckinAndTrends() {
		MoodCheckinDto dto = new MoodCheckinDto();
		dto.setMoodScore(4);
		dto.setPrimaryEmotion("Hopeful");
		dto.setEnergyLevel(4);
		dto.setJournalNote("Had a calm walk in the garden.");

		MoodEntry saved = moodService.recordMood(dto);
		assertNotNull(saved.getId());
		assertEquals("Hopeful", saved.getPrimaryEmotion());
		assertNotNull(saved.getGentleInsight());

		double avg = moodService.getAverageMoodThisWeek();
		assertTrue(avg > 0);
	}

	@Test
	void testMicroActivityQuests() {
		List<MicroActivity> list = activityService.getTodayActivities();
		assertFalse(list.isEmpty());
		
		Long firstId = list.get(0).getId();
		var updated = activityService.toggleActivityCompletion(firstId, "Felt refreshed");
		assertTrue(updated.isPresent());
		assertTrue(updated.get().isCompleted());
	}

	@Test
	void testGroundingAffirmations() {
		String affirmation = groundingService.getRandomAffirmation();
		assertNotNull(affirmation);
		assertFalse(affirmation.isBlank());
		assertEquals(5, groundingService.getSensorySteps().size());
	}
}
