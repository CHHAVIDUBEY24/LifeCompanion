// LifeCompanion Interactive Scripts

document.addEventListener('DOMContentLoaded', () => {
    // 1. Initialize Lucide Icons
    if (window.lucide) {
        window.lucide.createIcons();
    }

    // 2. Chat Assistant Functionality
    initChat();

    // 3. Box Breathing Engine
    initBoxBreathing();

    // 4. Mood Trend Chart
    initMoodChart();

    // 5. Activity Completion Toggles
    initActivityHandlers();

    // 6. Reconnection & Gratitude Handlers
    initReconnectionHandlers();
});

/* --- CHAT LOGIC --- */
function initChat() {
    const chatForm = document.getElementById('chatForm');
    const chatInput = document.getElementById('chatInput');
    const chatContainer = document.getElementById('chatMessages');

    if (!chatForm || !chatInput || !chatContainer) return;

    // Scroll to bottom
    chatContainer.scrollTop = chatContainer.scrollHeight;

    chatForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const text = chatInput.value.trim();
        if (!text) return;

        // Append user message
        appendChatMessage('user', text, new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }));
        chatInput.value = '';

        // Show typing indicator
        const typingId = showTypingIndicator();

        try {
            const res = await fetch('/api/companion/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: text })
            });

            removeTypingIndicator(typingId);

            if (res.ok) {
                const data = await res.json();
                appendChatMessage('assistant', data.reply, data.timestamp, data.crisisAlert, data.suggestedAction);
            } else {
                appendChatMessage('assistant', "I'm right here with you. Take a gentle breath, let's try again in a moment.", '', false, null);
            }
        } catch (err) {
            removeTypingIndicator(typingId);
            appendChatMessage('assistant', "I'm right here with you. Take a gentle breath, let's try again in a moment.", '', false, null);
        }
    });

    // Handle suggestion chips
    document.querySelectorAll('.suggestion-chip').forEach(chip => {
        chip.addEventListener('click', () => {
            chatInput.value = chip.getAttribute('data-text');
            chatForm.dispatchEvent(new Event('submit'));
        });
    });
}

function appendChatMessage(role, text, time, isCrisis = false, suggestedAction = null) {
    const chatContainer = document.getElementById('chatMessages');
    if (!chatContainer) return;

    const wrapper = document.createElement('div');
    wrapper.className = `flex gap-3 ${role === 'user' ? 'justify-end' : 'justify-start'} animate-fade-in`;

    let crisisBadge = isCrisis ? `
        <div class="mb-3 p-3 bg-red-50 border border-red-200 rounded-xl text-xs text-red-700 flex items-center gap-2">
            <i data-lucide="alert-circle" class="w-4 h-4 text-red-500"></i>
            <span>Support &amp; Crisis resources detected. Please connect with caring professionals.</span>
        </div>` : '';

    let actionButton = suggestedAction ? `
        <div class="mt-3 pt-2 border-t border-slate-100 flex items-center justify-between text-xs text-emerald-700">
            <span>Gentle suggestion: <strong>${suggestedAction}</strong></span>
            <a href="/grounding" class="text-xs bg-emerald-50 text-emerald-700 px-2 py-1 rounded-lg hover:bg-emerald-100 transition">Try Now</a>
        </div>` : '';

    // Convert markdown bullet points and newlines to readable html
    let formatted = text.replace(/\n/g, '<br/>')
                        .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                        .replace(/•\s/g, '• ');

    if (role === 'user') {
        wrapper.innerHTML = `
            <div class="max-w-[80%] rounded-2xl rounded-tr-sm p-4 bg-slate-700 text-white shadow-sm">
                <p class="text-sm whitespace-pre-wrap leading-relaxed">${formatted}</p>
                <span class="block text-[10px] text-slate-300 text-right mt-1">${time || ''}</span>
            </div>
        `;
    } else {
        wrapper.innerHTML = `
            <div class="w-8 h-8 rounded-full bg-emerald-100 text-emerald-800 flex items-center justify-center flex-shrink-0 font-serif text-sm font-bold shadow-sm">
                LC
            </div>
            <div class="max-w-[80%] rounded-2xl rounded-tl-sm p-4 bg-white border border-slate-100 text-slate-800 shadow-sm">
                ${crisisBadge}
                <div class="text-sm leading-relaxed text-slate-700">${formatted}</div>
                ${actionButton}
                <span class="block text-[10px] text-slate-400 text-right mt-1.5">${time || ''}</span>
            </div>
        `;
    }

    chatContainer.appendChild(wrapper);
    chatContainer.scrollTop = chatContainer.scrollHeight;
    if (window.lucide) window.lucide.createIcons();
}

function showTypingIndicator() {
    const chatContainer = document.getElementById('chatMessages');
    const id = 'typing-' + Date.now();
    const div = document.createElement('div');
    div.id = id;
    div.className = 'flex gap-3 items-center text-slate-400 text-xs italic pl-2';
    div.innerHTML = `
        <div class="w-8 h-8 rounded-full bg-emerald-50 text-emerald-700 flex items-center justify-center font-serif text-xs">LC</div>
        <div class="bg-slate-100 px-4 py-2 rounded-2xl flex items-center gap-1.5">
            <span class="w-2 h-2 rounded-full bg-emerald-500 animate-bounce"></span>
            <span class="w-2 h-2 rounded-full bg-emerald-500 animate-bounce [animation-delay:0.2s]"></span>
            <span class="w-2 h-2 rounded-full bg-emerald-500 animate-bounce [animation-delay:0.4s]"></span>
        </div>
    `;
    chatContainer.appendChild(div);
    chatContainer.scrollTop = chatContainer.scrollHeight;
    return id;
}

function removeTypingIndicator(id) {
    const el = document.getElementById(id);
    if (el) el.remove();
}


/* --- BOX BREATHING LOGIC --- */
let breathingInterval = null;
let breathingPhase = 0; // 0: Inhale, 1: Hold, 2: Exhale, 3: Hold

function initBoxBreathing() {
    const startBtn = document.getElementById('startBreathingBtn');
    const circle = document.getElementById('breathingInnerCircle');
    const instructionText = document.getElementById('breathingInstruction');
    const countdownText = document.getElementById('breathingCountdown');

    if (!startBtn || !circle || !instructionText) return;

    const phases = [
        { label: 'Inhale gently...', class: 'inhale', text: 'Breathe in peace' },
        { label: 'Hold softly...', class: 'hold1', text: 'Feel stillness' },
        { label: 'Exhale slowly...', class: 'exhale', text: 'Release tension' },
        { label: 'Rest & hold...', class: 'hold2', text: 'Quiet pause' }
    ];

    startBtn.addEventListener('click', () => {
        if (breathingInterval) {
            // Stop
            clearInterval(breathingInterval);
            breathingInterval = null;
            circle.className = 'breathing-circle-inner';
            instructionText.textContent = 'Ready to begin';
            countdownText.textContent = '4s Box Breathing';
            startBtn.innerHTML = '<i data-lucide="play" class="w-4 h-4"></i> Start 4-4-4-4 Breathing';
            if (window.lucide) window.lucide.createIcons();
        } else {
            // Start
            startBtn.innerHTML = '<i data-lucide="square" class="w-4 h-4"></i> Pause Exercise';
            if (window.lucide) window.lucide.createIcons();

            let seconds = 4;
            breathingPhase = 0;

            const runStep = () => {
                const cur = phases[breathingPhase];
                circle.className = `breathing-circle-inner breathing-active ${cur.class}`;
                instructionText.textContent = cur.label;
                countdownText.textContent = `${cur.text} (${seconds}s)`;

                seconds--;
                if (seconds < 0) {
                    seconds = 4;
                    breathingPhase = (breathingPhase + 1) % 4;
                }
            };

            runStep();
            breathingInterval = setInterval(runStep, 1000);
        }
    });
}


/* --- MOOD TREND CHART --- */
async function initMoodChart() {
    const ctx = document.getElementById('moodChartCanvas');
    if (!ctx) return;

    try {
        const res = await fetch('/api/reflections/trends');
        if (!res.ok) return;
        const data = await res.json();
        if (!data || data.length === 0) return;

        // Reverse to show chronological order
        const chronological = [...data].reverse();
        const labels = chronological.map(e => {
            const d = new Date(e.timestamp);
            return d.toLocaleDateString([], { month: 'short', day: 'numeric' });
        });
        const scores = chronological.map(e => e.moodScore);

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Mood Level (1-5)',
                    data: scores,
                    borderColor: '#52796f',
                    backgroundColor: 'rgba(132, 169, 140, 0.2)',
                    fill: true,
                    tension: 0.35,
                    pointBackgroundColor: '#2f3e46',
                    pointRadius: 5,
                    pointHoverRadius: 7
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        min: 1,
                        max: 5,
                        ticks: {
                            stepSize: 1,
                            callback: (val) => {
                                const labels = ['', 'Overwhelmed', 'Low', 'Neutral', 'Good', 'Peaceful'];
                                return labels[val] || val;
                            }
                        },
                        grid: { color: 'rgba(0,0,0,0.05)' }
                    },
                    x: {
                        grid: { display: false }
                    }
                },
                plugins: {
                    legend: { display: false }
                }
            }
        });
    } catch (err) {
        console.warn('Could not load mood chart:', err);
    }
}


/* --- ACTIVITY HANDLERS --- */
function initActivityHandlers() {
    document.querySelectorAll('.activity-toggle-btn').forEach(btn => {
        btn.addEventListener('click', async () => {
            const id = btn.getAttribute('data-id');
            if (!id) return;

            try {
                const res = await fetch(`/api/activities/${id}/toggle`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({})
                });

                if (res.ok) {
                    const updated = await res.json();
                    const card = document.getElementById(`activity-card-${id}`);
                    if (card) {
                        if (updated.completed) {
                            card.classList.add('bg-emerald-50/70', 'border-emerald-200');
                            btn.innerHTML = '<i data-lucide="check-circle-2" class="w-5 h-5 text-emerald-600"></i>';
                            btn.classList.add('bg-emerald-100');
                        } else {
                            card.classList.remove('bg-emerald-50/70', 'border-emerald-200');
                            btn.innerHTML = '<i data-lucide="circle" class="w-5 h-5 text-slate-400"></i>';
                            btn.classList.remove('bg-emerald-100');
                        }
                        if (window.lucide) window.lucide.createIcons();
                    }
                }
            } catch (e) {
                console.error('Failed to toggle activity', e);
            }
        });
    });

    // Create custom quest form
    const customForm = document.getElementById('customQuestForm');
    if (customForm) {
        customForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const title = document.getElementById('questTitle').value.trim();
            const description = document.getElementById('questDesc').value.trim();
            const category = document.getElementById('questCategory').value;
            const duration = document.getElementById('questDuration').value;

            if (!title) return;

            try {
                const res = await fetch('/api/activities/create', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ title, description, category, duration })
                });

                if (res.ok) {
                    window.location.reload();
                }
            } catch (err) {
                console.error('Error creating quest:', err);
            }
        });
    }
}


/* --- RECONNECTION & GRATITUDE HANDLERS --- */
function initReconnectionHandlers() {
    // Goal creation
    const goalForm = document.getElementById('newGoalForm');
    if (goalForm) {
        goalForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const title = document.getElementById('goalTitle').value.trim();
            const targetName = document.getElementById('goalTarget').value.trim();
            const category = document.getElementById('goalCategory').value;
            const smallStep = document.getElementById('goalStep').value.trim();
            const comfortLevel = document.getElementById('goalComfort').value;

            try {
                const res = await fetch('/api/reconnect/goals', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ title, targetName, category, smallStep, comfortLevel })
                });
                if (res.ok) window.location.reload();
            } catch (err) {
                console.error(err);
            }
        });
    }

    // Gratitude creation
    const gratitudeForm = document.getElementById('newGratitudeForm');
    if (gratitudeForm) {
        gratitudeForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const content = document.getElementById('gratitudeContent').value.trim();
            const category = document.getElementById('gratitudeCategory').value;

            if (!content) return;

            try {
                const res = await fetch('/api/reconnect/gratitude', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ content, category })
                });
                if (res.ok) window.location.reload();
            } catch (err) {
                console.error(err);
            }
        });
    }

    // Gratitude favorite toggle
    document.querySelectorAll('.gratitude-fav-btn').forEach(btn => {
        btn.addEventListener('click', async () => {
            const id = btn.getAttribute('data-id');
            try {
                const res = await fetch(`/api/reconnect/gratitude/${id}/favorite`, { method: 'POST' });
                if (res.ok) {
                    const item = await res.json();
                    btn.classList.toggle('text-amber-500', item.favorite);
                    btn.classList.toggle('text-slate-300', !item.favorite);
                }
            } catch (err) {
                console.error(err);
            }
        });
    });
}
