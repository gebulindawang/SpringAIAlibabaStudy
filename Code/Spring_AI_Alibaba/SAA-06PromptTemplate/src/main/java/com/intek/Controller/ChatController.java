package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatController {
    @Resource(name = "deepseekChatModel")
    private ChatModel deepseek;
    @Resource(name = "qwenChatModel")
    private ChatModel qwen;
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenClient;

    @GetMapping("/chat/promptTemplate")
    public Flux<String> promptTemplate(@RequestParam(name = "question") String question){
        PromptTemplate promptTemplate = new PromptTemplate(
                "讲一个{topic}的故事"
                +"并以{output_format}的格式输出"
                +"字数在{word_count}字左右"
        );
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", "游戏",
                "output_format", "html",
                "word_count", "100"
        ));

        return deepseekClient.prompt(prompt)
                .system("你作为一名游戏工程师，请根据问题给出相应的答案")
                .user(question)
                .stream().content();
    }


}
