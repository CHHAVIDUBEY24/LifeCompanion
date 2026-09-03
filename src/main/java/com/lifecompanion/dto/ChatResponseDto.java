package com.lifecompanion.dto;

public class ChatResponseDto {
    private String reply;
    private boolean crisisAlert;
    private String suggestedAction;
    private String timestamp;

    public ChatResponseDto() {}

    public ChatResponseDto(String reply, boolean crisisAlert, String suggestedAction, String timestamp) {
        this.reply = reply;
        this.crisisAlert = crisisAlert;
        this.suggestedAction = suggestedAction;
        this.timestamp = timestamp;
    }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public boolean isCrisisAlert() { return crisisAlert; }
    public void setCrisisAlert(boolean crisisAlert) { this.crisisAlert = crisisAlert; }

    public String getSuggestedAction() { return suggestedAction; }
    public void setSuggestedAction(String suggestedAction) { this.suggestedAction = suggestedAction; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
