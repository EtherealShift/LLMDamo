package com.example.llmdamo.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class OllamaChatController {
//
//    @Resource
//    private OllamaChatModel chatModel;
//
//    @GetMapping("/ai/generate")
//    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        return Map.of("generation", this.chatModel.call(message));
//    }
//
//    @GetMapping(value = "/ai/generateStream", produces = "text/html; charset=UTF-8")
//    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "讲个笑话") String message) {
//        Prompt prompt = new Prompt(new UserMessage(message));
//        return this.chatModel.stream(prompt).map(ChatResponse -> ChatResponse.getResult().getOutput().getText());
//    }

}
