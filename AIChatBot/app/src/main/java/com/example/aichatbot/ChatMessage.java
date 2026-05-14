package com.example.aichatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_messages")
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String message;
    public boolean isUser;
    public String timestamp;

    public ChatMessage(String username, String message, boolean isUser, String timestamp) {
        this.username = username;
        this.message = message;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }
}