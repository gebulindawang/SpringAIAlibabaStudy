# 简介

`ChatClient` 提供了与 AI 通信的 Fluent API，支持同步和反应式编程模型。与 `ChatModel`、`Message`、`ChatMemory` 等原子性 API 相比，使用 `ChatClient` 可以将与 LLM 以及其他组件交互的复杂性隐藏在背后。

## 基础功能

1. 定制和组装模型的输入
2. 格式化解析模型的输出
3. 调整模型交互参数

## 高级功能

1. 聊天记忆
2. 工具函数调用
3. RAG（检索增强生成）

chatClient是高级封装，基于ChatModel构建，适合快速构建标准化复杂AI服务，支持同步和流式交互，集成多种高级功能

## ChatClient注入

```java
 private final ChatClient client;
    public  ChatClientController (ChatModel chatModel){
        this.client = ChatClient.builder(chatModel).build();
    }
```

## 流式输出

是一种逐步返回大模型生成结果的技术，生成一点返回一点，允许服务器将相应内容分批次传输给客户端，而不是等待全部内容生成完毕后一次性返回

这种机制能显著提升用户体验，尤其适用于大模型响应较慢的场景

## Server-Sent Events(SSE)

SSE是一种允许服务端可以持续推送数据片段（如逐词或逐句）到web前端的Web技术。通过单向的HTTP长连接，使用一个长期存在的连接，让服务器可以主动将数据推给客户端，SSE是轻量级的单项通信协议，适合AI对话这类服务端主导的场景

核心概念

SSE的核心思想是：客户端发起一个请求，服务器保持这个连接打开并在有新数据时，通过这个连接将数据发送给客户端

| 特性     | Server-Sent Events(SSE)  | WebSocket |
| -------- | ------------------------ | --------- |
| __通信方向__ | 单向（服务器 -> 客户端） | 双向      |
|__协议__|基于HTTP|独立的ws://或wss://协议|
|__数据类型__|文本|文本加二进制|
|__复杂性__|简单|相对复杂|
|__连接开销__|较低|较高|
|__自动重连__|是|需要手动实现|
|__浏览器支持__|广泛支持（除了IE）|广泛支持|

 ## 多模型适配

### ChatModel实现

```java
@Configuration
public class SaaLLMConfig {
    private final String DEEPSEEK_MODEL = "deepseek-v4-flash";
    private final String QWEN_MODEL = "qwen-plus";

    @Bean(name = "deepseekChatModel")
    public ChatModel deepseek(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("SpringAIAlibabaStudyApiKey")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }
    @Bean(name ="qwenChatModel")
    public ChatModel qwen(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("SpringAIAlibabaStudyApiKey")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }
}
```

```java
@Resource(name = "deepseekChatModel")
private ChatModel deepseek;
@Resource(name = "qwenChatModel")
private ChatModel Qwen;
@GetMapping("/streamOutput/deepseek")
    public Flux<String> streamOutputDeepseek(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return deepseek.stream(msg);
    }

    @GetMapping("/streamOutput/qwen")
    public Flux<String> streamOutputQwen(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return qwen.stream(msg);
    }

```

### ChatClient 实现

``` java
@Configuration
public class SaaLLMConfig {
    private final String DEEPSEEK_MODEL = "deepseek-v4-flash";
    private final String QWEN_MODEL = "qwen-plus";

    @Bean(name = "deepseekChatModel")
    public ChatModel deepseek(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("SpringAIAlibabaStudyApiKey")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }
    @Bean(name ="qwenChatModel")
    public ChatModel qwen(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("SpringAIAlibabaStudyApiKey")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }


    @Bean(name = "deepseekChatClient")
    public ChatClient deepseekClient(@Qualifier("deepseekChatModel") ChatModel chatModel){
        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder()
                        .model(DEEPSEEK_MODEL)
                        .build())
                .build();
    }

    @Bean(name = "qwenChatClient")
    public ChatClient qwenClient(@Qualifier("qwenChatModel") ChatModel chatModel){
        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder()
                        .model(QWEN_MODEL)
                        .build())
                .build();
    }
}
```



## Flux

Flux是springWebFlux中的一个核心组件，属于响应式编程的一部分。它主要用户处理异步，非阻塞的流式数据，能够高效的处理高并发场景。Flux可以生成和处理一系列的事件或数据如流式输出等等

## 提示词(Prompt)

Prompt是引导AI模型生成特定输出的输入格式，Prompt的设计和措辞会显著影响模型的响应

### 提示词的四大角色

1. USER：用户原始提问。代表用户的输入他们向AI提出的问题，命令或陈述
2. ASSISTANT：AI返回的响应信息，定义为“助手角色”的消息。用它可以确保上下文能够连贯的交互
3. SYSTEM ： 设定AI行为边界/角色/定位。指导AI的行为和响应方式，设置AI如何解释和回复输入的内容
4. TOOL