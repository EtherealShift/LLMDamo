package com.example.llmdamo.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class DSChatController {


    private final DeepSeekChatModel chatModel;

    @Autowired
    public DSChatController(DeepSeekChatModel chatModel) {
        this.chatModel = chatModel;
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

//    @GetMapping(value = "/ai/generatePythonCode", produces = "text/html; charset=UTF-8")
//    public String generateCode(@RequestParam(value = "message", defaultValue = "请写快速排序代码") String message) {
//        UserMessage userMessage = new UserMessage(message);
//        Message assistantMessage = DeepSeekAssistantMessage.prefixAssistantMessage("```python\\n");
//        Prompt prompt = new Prompt(List.of(userMessage, assistantMessage), ChatOptions.builder().stopSequences(List.of("```")).build());
//        ChatResponse response = chatModel.call(prompt);
//        return response.getResult().getOutput().getText();
//    }

}
