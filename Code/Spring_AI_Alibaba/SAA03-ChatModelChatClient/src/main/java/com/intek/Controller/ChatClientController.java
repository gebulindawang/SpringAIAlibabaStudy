package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientController {

//    private final ChatClient client;
//    public  ChatClientController (ChatModel chatModel){
//        this.client = ChatClient.builder(chatModel).build();
//    }
    @Resource
    private ChatClient client;

    @GetMapping("/chatClient/doChat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg ){

        return client.prompt(msg).user("user").call().content();
    }


}
