# 🌿 LifeCompanion - Wellbeing & Reconnection Platform

A mental wellbeing and companion web application built with **Spring Boot 3 (Java 17)** designed to support people experiencing loneliness, burnout, or emotional overwhelm.

---

## ✨ Key Features

1. **💬 Empathetic AI Companion (`/companion`)**
   - Warm, non-judgmental conversational companion powered by Google Gemini API (with built-in empathetic offline simulation).
   - **Crisis Safety Guardrails**: Detects severe distress keywords and immediately displays regional helpline resources (988, Crisis Text Line, Tele-MANAS, etc.).
   - Multi-turn conversation persistence.

2. **📝 Structured Self-Reflection & Mood Journey (`/reflections`)**
   - Daily emotional check-in (Mood 1-5, primary emotions, energy meter, contextual triggers, journal notes).
   - Dynamic guided reflection prompts.
   - Interactive emotional trajectory trends chart powered by Chart.js.

3. **🌱 Achievable Micro-Quests (`/activities`)**
   - Bite-sized, low-friction micro-habits categorized by *Self-Care*, *Grounding*, *Social Warmth*, *Movement*, and *Creativity*.
   - Zero penalty or streak stress—celebrates micro-wins with compassion.
   - Custom quest creator.

4. **🧘 Mindful Grounding Suite (`/grounding`)**
   - **Interactive 4-4-4-4 Box Breathing Visualizer** (Inhale, Hold, Exhale, Hold).
   - **5-4-3-2-1 Sensory Grounding Guide** to alleviate panic or racing thoughts.
   - **Soothing Affirmation Deck**.

5. **🤝 Gradual Reconnection Roadmap & Gratitude Vault (`/reconnect`)**
   - Step-by-step low-pressure outreach goals with comfort-level tags (Family, Friends, Community, Hobbies).
   - Personal **Gratitude & Comfort Vault** to preserve moments of warmth and sensory joy.

---

## 🚀 Getting Started

### Prerequisites
- **Java 17+** (JDK 17 or higher)
- **Apache Maven 3.9+**

### Running the Application

1. Open your terminal in the project directory:
   ```bash
   cd C:\Users\hp\.gemini\antigravity\scratch\life-companion
   ```

2. Run with Maven:
   ```bash
   mvn spring-boot:run
   ```

3. Open your browser and visit:
   ```
   http://localhost:8080
   ```

### (Optional) Gemini API Key Configuration
To enable live AI inference with Google's Gemini models, set the `GEMINI_API_KEY` environment variable or add it to `src/main/resources/application.properties`:

```properties
gemini.api.key=YOUR_GEMINI_API_KEY
gemini.api.model=gemini-2.5-flash
```

---

## 🛠️ Tech Stack
- **Backend**: Spring Boot 3.3.3, Spring Data JPA, Spring Web, Spring Validation
- **Database**: H2 (Embedded / File-backed)
- **Frontend**: Thymeleaf, Tailwind CSS, Lucide Icons, Chart.js
- **Testing**: JUnit 5, Spring Boot Test
