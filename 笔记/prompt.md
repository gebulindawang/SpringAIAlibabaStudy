## 提示词(Prompt)

Prompt是引导AI模型生成特定输出的输入格式，Prompt的设计和措辞会显著影响模型的响应

### 提示词的四大角色

1. USER：用户原始提问。代表用户的输入他们向AI提出的问题，命令或陈述
2. ASSISTANT：AI返回的响应信息，定义为“助手角色”的消息。用它可以确保上下文能够连贯的交互
3. SYSTEM ： 设定AI行为边界/角色/定位。指导AI的行为和响应方式，设置AI如何解释和回复输入的内容
4. TOOL

 ## 编码实现

``` java
 @GetMapping("/prompt/chat")
    public Flux<ChatResponse> chat(@RequestParam(name = "question") String question){
        return deepseekChatClient.prompt()
                .system("你是一名软件开发工程师，除了相关专业领域其他一切问题无可奉告")
                .user(question)
                .stream().chatResponse();
    }
```

## Prompt Template

SpringAi中用提示模板的关键组件是PromptTemplate类。该类使用Terence Parr开发的Oss StringTemplate引擎来构建和管理提示。PromptTemplate类旨在促进结构化提示的创建，然后将其发送到AI模型进行处理 

