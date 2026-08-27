package com.workguard.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ChatModel chatModel;

    public AiController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ask")
    public ResponseEntity<Map<String, Object>> ask(
            @RequestParam(defaultValue = "안녕! 자기소개 간단히 한 줄로 해줘.") String prompt) {
        String response = chatModel.call(prompt);
        return ResponseEntity.ok(Map.of(
                "prompt", prompt,
                "response", response
        ));
    }
}
