package com.tfgbackend.configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;

@Configuration
public class GeminiConfiguration {

    @Bean
    public ChatModel geminiChatModel(@Value("${gemini.api.key}") String apiKey) {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.1-flash-lite")
                .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
                .timeout(java.time.Duration.ofMinutes(1))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
