package com.intek.Config;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisMemoryConfig {

    @Value("${spring.data.redis.host}")
    private String REDIS_HOST;
    @Value("${spring.data.redis.port}")
    private Integer REDIS_PORT;
    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository(){
        return RedisChatMemoryRepository.builder()
                .host(REDIS_HOST)
                .port(REDIS_PORT)
                .build();
    }
}
