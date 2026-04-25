*****Spring AI Alibaba****

在yml文件里配置好密钥后，还需要配置配置类来进行读取

```java
@Configuration
public class SaaLLMConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Bean
    public DashScopeApi dashScopeAgentApi(){
        return DashScopeApi.builder().apiKey(apiKey).build();
    }
}

```


