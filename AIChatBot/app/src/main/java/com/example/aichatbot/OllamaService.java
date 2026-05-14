package com.example.aichatbot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OllamaService {

    @POST("api/generate")
    Call<OllamaResponse> generate(
            @Body OllamaRequest request
    );
}