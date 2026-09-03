package com.lifecompanion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gratitude_items")
public class GratitudeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * Category: SIMPLE_PLEASURE, KIND_WORD, NATURE, COMFORT_OBJECT, MEMORY
     */
    private String category;

    private boolean favorite;

    private LocalDateTime createdAt;

    public GratitudeItem() {
        this.createdAt = LocalDateTime.now();
        this.favorite = false;
    }

    public GratitudeItem(String content, String category) {
        this.content = content;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.favorite = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
