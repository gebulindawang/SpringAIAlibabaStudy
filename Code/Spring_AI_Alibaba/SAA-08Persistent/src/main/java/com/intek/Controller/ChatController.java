package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

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

    @GetMapping("/chat/deepseek")
    public Flux<String> chatDeepseek(@RequestParam(name = "msg", defaultValue = "你是谁") String msg,@RequestParam(name = "userId") String userId) {
        return deepseekClient.prompt(msg)
                .advisors(new Consumer<ChatClient.AdvisorSpec>() {
                    @Override
                    public void accept(ChatClient.AdvisorSpec advisorSpec) {
                        advisorSpec.param(CONVERSATION_ID, userId);
                    }
                }).stream().content();
    }


}
