package com.intek.Controller;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamOutputController {

    private static final Logger log = LoggerFactory.getLogger(StreamOutputController.class);
    @Resource(name = "deepseekChatModel")
    private ChatModel deepseek;

    @Resource(name = "qwenChatModel")
    private ChatModel qwen;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenClient;

    @GetMapping("/streamOutput/deepseek")
    public Flux<String> streamOutputDeepseek(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {

        return deepseek.stream(msg);
    }

    @GetMapping("/streamOutput/qwen")
    public Flux<String> streamOutputQwen(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return qwen.stream(msg);
    }

    @GetMapping("/streamOutput/deepseekClient")
    public Flux<String> streamOutputDeepseekClient(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {


        return deepseekClient.prompt(msg).user( "user").stream().content();
    }
}