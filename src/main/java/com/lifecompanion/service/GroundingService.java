package com.lifecompanion.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GroundingService {

    private static final List<String> AFFIRMATIONS = List.of(
        "I am doing the best I can with the energy I have today.",
        "My worth is not defined by how productive or cheerful I am.",
        "It is safe for me to rest, pause, and breathe right now.",
        "I have survived every difficult day so far, and I will navigate this one too.",
        "I don't have to figure out my entire future in this single moment.",
        "I give myself permission to be a gentle work in progress.",
        "Small steps still carry me forward, even when they feel tiny.",
        "There is no rush. Peace is found one moment at a time."
    );

    private static final List<Map<String, String>> SENSORY_STEPS = List.of(
        Map.of(
            "step", "5",
            "sense", "SEE",
            "icon", "eye",
            "prompt", "Look around you and name 5 things you can see.",
            "subtext", "Notice their colors, patterns, shadows, or textures (e.g. a wooden table, a plant leaf, a pen)."
        ),
        Map.of(
            "step", "4",
            "sense", "TOUCH",
            "icon", "hand",
            "prompt", "Notice 4 things you can physically touch or feel right now.",
            "subtext", "Feel the texture of your clothes, the floor under your feet, or the coolness of a surface."
        ),
        Map.of(
            "step", "3",
            "sense", "HEAR",
            "icon", "volume-2",
            "prompt", "Listen carefully and identify 3 sounds around you.",
            "subtext", "The hum of a fan, birds chirping outside, distant traffic, or your own gentle breath."
        ),
        Map.of(
            "step", "2",
            "sense", "SMELL",
            "icon", "wind",
            "prompt", "Notice 2 scents in your environment.",
            "subtext", "The aroma of coffee, fresh air from a window, soap on your hands, or a comforting candle."
        ),
        Map.of(
            "step", "1",
            "sense", "TASTE",
            "icon", "smile",
            "prompt", "Focus on 1 taste in your mouth, or take a gentle sip of water.",
            "subtext", "Notice the sensation of temperature and moisture soothing your throat."
        )
    );

    public List<Map<String, String>> getSensorySteps() {
        return SENSORY_STEPS;
    }

    public List<String> getAllAffirmations() {
        return AFFIRMATIONS;
    }

    public String getRandomAffirmation() {
        Random random = new Random();
        return AFFIRMATIONS.get(random.nextInt(AFFIRMATIONS.size()));
    }
}
