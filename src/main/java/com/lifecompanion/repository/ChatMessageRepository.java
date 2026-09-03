package com.lifecompanion.repository;

import com.lifecompanion.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByOrderByTimestampAsc();
    List<ChatMessage> findTop50ByOrderByTimestampDesc();
}
