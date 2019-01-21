package com.buildupchao.messager.server.config;

import com.buildupchao.messager.server.endpoint.CustomEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:49
 * @since JDK 1.8
 */
@Configuration
public class EndpointConfig {

    @Value("${monitor.channel.map.key}")
    private String channelMap;

    @Bean
    public CustomEndpoint customEndpoint() {
        return new CustomEndpoint(channelMap);
    }
}
