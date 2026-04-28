package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Resource
    private ChatModel chatModel;


    @GetMapping("/chatModel/dochat")
    public Flux<String> doChat(@RequestParam(value = "msg",defaultValue = "你是谁") String msg) {

        return chatModel.stream(msg);
    }


}
