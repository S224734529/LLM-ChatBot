# AI Chatbot Android Application

## Project Overview

This project is a Java Android chatbot application that allows users to log in and communicate with an AI assistant through a simple messaging interface. The application follows the provided wireframe design and integrates a locally hosted Large Language Model (LLM) using Ollama and Gemma 3.

The app supports:
- Username-based login
- AI-powered chatbot conversation
- Persistent chat history using Room Database
- Timestamped messages
- Local LLM integration through Ollama

---

# Features

## User Authentication
Users can enter a username on the login screen to access the chatbot interface.

## Chat Interface
The application provides a messaging-style chat interface where users can:
- Send messages
- Receive AI-generated responses
- View timestamps for each message

## Chat History Persistence
All conversations are stored locally using Room Database so previous messages remain available after reopening the app.

## Local AI Integration
The application connects to a locally running Gemma 3 model through Ollama instead of relying on cloud-based APIs.

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java | Android application development |
| Android Studio | Development environment |
| RecyclerView | Chat message display |
| Room Database | Local message storage |
| Retrofit | API communication |
| Ollama | Local LLM runtime |
| Gemma 3 | Large Language Model |

---

# System Architecture

```text
Android App
     ↓
Retrofit API Requests
     ↓
Ollama Local Server
     ↓
Gemma 3 LLM
```

---

# Project Structure

```text
app/src/main/java/com/example/aichatbot/

├── LoginActivity.java
├── ChatActivity.java
├── ChatAdapter.java
├── ChatMessage.java
├── ChatDao.java
├── ChatDatabase.java
├── OllamaRequest.java
├── OllamaResponse.java
└── OllamaService.java
```

---

# Setup Instructions

## 1. Install Android Studio

Download Android Studio:

https://developer.android.com/studio

---

## 2. Install Ollama

Download Ollama:

https://ollama.com

Install Ollama for your operating system.

---

## 3. Download Gemma 3 Model

Open terminal and run:

```bash
ollama pull gemma3:1b
```

or:

```bash
ollama pull gemma3:1b-it-q4_K_M
```

---

## 4. Start Ollama Server

Run:

```bash
ollama serve
```

If you receive:

```text
address already in use
```

Ollama is already running.

---

## 5. Verify Ollama

Open browser:

```text
http://localhost:11434
```

Expected output:

```text
Ollama is running
```

---

## 6. Configure Android App

Ensure Retrofit uses:

```java
.baseUrl("http://10.0.2.2:11434/")
```

The Android emulator uses `10.0.2.2` to access localhost on the computer.

---

## 7. Enable Cleartext Traffic

In `AndroidManifest.xml`:

```xml
<application
    android:usesCleartextTraffic="true">
```

---

# Database Design

## ChatMessage Entity

| Field | Type |
|---|---|
| id | int |
| username | String |
| message | String |
| isUser | boolean |
| timestamp | String |

---

# Example API Request

```json
{
  "model": "gemma3:1b",
  "prompt": "Hello",
  "stream": false
}
```

---

# Future Improvements

- User profile support
- Dark mode
- Typing indicators
- Voice input
- Cloud database integration
- Online LLM support
- Message deletion

---

# LLM Declaration Statement

I used ChatGPT to assist with understanding how to connect a simple local LLM setup using Ollama and Gemma 3 to a Java Android application. The tool was used to help identify the required connection approach, including running Ollama locally, using the Android emulator address `10.0.2.2`, and sending requests from the Java Android app to the Ollama API.

The final implementation, testing, debugging, and integration into the Android project were completed by me.

---

# Author

Pamuditha Senadeera

---

# License

This project is developed for educational purposes.
