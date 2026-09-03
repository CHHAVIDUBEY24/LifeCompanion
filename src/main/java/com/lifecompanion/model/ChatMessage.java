package com.lifecompanion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * "user" or "assistant" or "system"
     */
    @Column(nullable = false)
    private String role;

    @Column(length = 4000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * True if crisis safety guardrail triggered
     */
    private boolean crisisAlert;

    /**
     * Gentle suggestion linked to the conversation (e.g., "Try a 2-min box breathing")
     */
    private String suggestedAction;

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage(String role, String content, boolean crisisAlert) {
        this.role = role;
        this.content = content;
        this.crisisAlert = crisisAlert;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isCrisisAlert() { return crisisAlert; }
    public void setCrisisAlert(boolean crisisAlert) { this.crisisAlert = crisisAlert; }

    public String getSuggestedAction() { return suggestedAction; }
    public void setSuggestedAction(String suggestedAction) { this.suggestedAction = suggestedAction; }
}
