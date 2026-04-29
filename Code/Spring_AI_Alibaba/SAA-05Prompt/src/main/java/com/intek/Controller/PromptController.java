package com.intek.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PromptController {
    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;
    @Resource(name = "deepseekChatModel")
    private ChatModel deepseekChatModel;

    @GetMapping("/prompt/chat")
    public Flux<ChatResponse> chat(@RequestParam(name = "question") String question){
        return deepseekChatClient.prompt()
                .system("你是一名软件开发工程师，除了相关专业领域其他一切问题无可奉告，并且返回的格式要按照html的格式返回")
                .user(question)
                .stream().chatResponse();
    }
    @GetMapping("/prompt/chatResponse")
    public Flux<String> chatResponseFlux(@RequestParam(name = "question")String question){
        UserMessage userMessage = new UserMessage(question);
        SystemMessage systemMessage = new SystemMessage("你是一名软件开发工程师，除了相关专业领域其他一切问题无可奉告，并且返回的格式要按照html的格式返回");
        Prompt prompt = new Prompt(userMessage,systemMessage);
        return deepseekChatModel.stream(prompt).mapNotNull(chatResponse -> chatResponse.getResults().get(0).getOutput().getText());
    }
}
