package com.example.llmdamo.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class DSChatController {

    @Resource
    private  DeepSeekChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    @GetMapping(value = "/ai/generateDS", produces = "text/html; charset=UTF-8")
    public String generateDS(@RequestParam(value = "message", defaultValue = "给我讲一个笑话") String message) {
        return chatClient.prompt("你是一个助手").user(message).call().content();
    }

    @GetMapping(value = "/ai/generateDSStream", produces = "text/html; charset=UTF-8")
    public Flux<String> generateDSStream(@RequestParam(value = "message", defaultValue = "讲一个笑话给我") String message) {

        return chatClient.prompt("你是一个助手").user(message).stream().content();
    }

    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "给我讲一个笑话") String message) {
        return Map.of("generation", chatModel.call(message)).get("generation");
    }

    @GetMapping(value = "/ai/generateStream", produces = "text/html; charset=UTF-8")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "讲一个笑话给我") String message) {
        var prompt = new Prompt(new UserMessage(message));

        return chatModel.stream(prompt).mapNotNull(ChatResponse -> ChatResponse.getResult().getOutput().getText());
    }


}
