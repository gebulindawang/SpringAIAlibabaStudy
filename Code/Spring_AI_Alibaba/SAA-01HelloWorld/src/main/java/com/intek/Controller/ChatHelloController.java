package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatHelloController {
    @Resource
    private ChatModel chatModel;

    @GetMapping("/hellow/doChat")
    public String doChat(@RequestParam(value = "msg",defaultValue = "你是谁") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("/hellow/doChatFlux")
    public Flux<String> doChatFlux(@RequestParam(value = "msg",defaultValue = "你是谁") String msg) {
        return chatModel.stream(msg);
    }
}
