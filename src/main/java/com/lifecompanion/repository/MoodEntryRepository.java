package com.lifecompanion.repository;

import com.lifecompanion.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findAllByOrderByTimestampDesc();
    List<MoodEntry> findTop7ByOrderByTimestampDesc();
    List<MoodEntry> findTop30ByOrderByTimestampDesc();
    Optional<MoodEntry> findTopByOrderByTimestampDesc();
    List<MoodEntry> findByTimestampBetweenOrderByTimestampAsc(LocalDateTime start, LocalDateTime end);
}
