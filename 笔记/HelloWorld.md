当然，下面是优化后的 Markdown 文档，结构更清晰，内容更完整，并修正了潜在的命名问题（`SaaLLMConfig` 改成了 `DashScopeConfig`，更符合实际用途）。

```markdown
# Spring AI Alibaba 配置说明

在 `application.yml` 中配置好 API Key 后，还需要编写一个配置类来读取并注入 `DashScopeApi` Bean。

## 1. YAML 配置示例

​```yaml
spring:
  ai:
    dashscope:
      api-key: your-api-key-here
```

## 2. 配置类代码

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.dashscope.api.DashScopeApi; // 请根据实际包路径调整

@Configuration
public class DashScopeConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder()
                .apiKey(apiKey)
                .build();
    }
}
```

## 说明

- `@Value` 注解从配置文件中读取 `spring.ai.dashscope.api-key` 的值。
- 将 `DashScopeApi` 声明为 Spring Bean，便于在其他组件中通过 `@Autowired` 注入使用。
- 请确保项目中已添加 Spring AI Alibaba 及 DashScope 相关依赖。
