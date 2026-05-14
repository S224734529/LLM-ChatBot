package com.example.aichatbot;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert
    void insertMessage(ChatMessage message);

    @Query("SELECT * FROM chat_messages WHERE username = :username ORDER BY id ASC")
    List<ChatMessage> getMessages(String username);
}