package com.example.llmdamo.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.llmdamo.entity.History;
import com.example.llmdamo.service.HistoryService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DSChatController {

    @Resource
    private  DeepSeekChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    @Resource
    private HistoryService historyService;

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


    @GetMapping(value = "/ai/generateStreamMySQL", produces = "text/html; charset=UTF-8")
    public Flux<String> generateStreamMySQL(@RequestParam(value = "message", defaultValue = "讲一个笑话给我") String message,
                                            @RequestParam(value = "sessionId", defaultValue = "1") Long sessionId) {
        // 保存用户聊天记录
        History userHistory = new History();
        userHistory.setSessionId(sessionId);
        userHistory.setContent(message);
        userHistory.setRole("user");
        userHistory.setDataTime(LocalDateTime.now());
        historyService.save(userHistory);

        // 获取sessionId对应的历史记录
        List<History> historyList = historyService.list(new LambdaUpdateWrapper<History>().eq(History::getSessionId, sessionId).ne(History::getId, userHistory.getId()));

        List<Message> messages = historyList.stream().map(history -> "user".equals(history.getRole())
                ? new UserMessage(history.getContent()) : new AssistantMessage(history.getContent())).collect(Collectors.toList());

        StringBuilder[] sb = {new StringBuilder()};

        Flux<String> stream = chatClient.prompt("你是一个助手").user(message).messages(messages).stream().content();
        return stream.doOnNext(s -> sb[0].append(s)).doOnComplete(() -> {
            History assistantHistory = new History();
            assistantHistory.setSessionId(sessionId);
            assistantHistory.setContent(sb[0].toString());
            assistantHistory.setRole("assistant");
            assistantHistory.setDataTime(LocalDateTime.now());

            historyService.save(assistantHistory);
        });
    }




}



















