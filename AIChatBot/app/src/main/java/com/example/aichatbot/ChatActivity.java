package com.example.aichatbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    

    private TextView welcomeText;
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;

    private ChatAdapter adapter;
    private ArrayList<ChatMessage> messages;
    private ChatDatabase database;
    private OllamaService ollamaService;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username = getIntent().getStringExtra("username");

        welcomeText = findViewById(R.id.welcomeText);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        welcomeText.setText("Welcome " + username + "!");

        database = ChatDatabase.getInstance(this);

        messages = new ArrayList<>();
        messages.addAll(database.chatDao().getMessages(username));

        adapter = new ChatAdapter(messages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:11434/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ollamaService = retrofit.create(OllamaService.class);

        sendButton.setOnClickListener(v -> sendUserMessage());
    }

    private void sendUserMessage() {
        String text = messageInput.getText().toString().trim();

        if (text.isEmpty()) {
            return;
        }

        messageInput.setText("");

        ChatMessage userMessage = new ChatMessage(username, text, true, getCurrentTime());
        saveAndDisplayMessage(userMessage);

        getBotReply(text);
    }

    private void getBotReply(String userText) {

        OllamaRequest request = new OllamaRequest(userText);

        Call<OllamaResponse> call = ollamaService.generate(request);

        call.enqueue(new Callback<OllamaResponse>() {
            @Override
            public void onResponse(Call<OllamaResponse> call,
                                   Response<OllamaResponse> response) {

                String reply = "No response";

                if (response.isSuccessful()
                        && response.body() != null) {

                    reply = response.body().response;
                }

                ChatMessage botMessage = new ChatMessage(
                        username,
                        reply,
                        false,
                        getCurrentTime()
                );

                saveAndDisplayMessage(botMessage);
            }

            @Override
            public void onFailure(Call<OllamaResponse> call, Throwable t) {

                ChatMessage botMessage = new ChatMessage(
                        username,
                        "Could not connect to local AI model.",
                        false,
                        getCurrentTime()
                );

                saveAndDisplayMessage(botMessage);
            }
        });
    }
    private void saveAndDisplayMessage(ChatMessage message) {
        database.chatDao().insertMessage(message);
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.scrollToPosition(messages.size() - 1);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}