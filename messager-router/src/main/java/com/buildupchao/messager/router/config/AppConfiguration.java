package com.buildupchao.messager.router.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/1/20 16:45
 * @since JDK 1.8
 */
@Data
@Component
public class AppConfiguration {

    @Value("${messager.zk.root}")
    private String zkRoot;

    @Value("${messager.zk.addr}")
    private String zkAddr;

    @Value("${server.port}")
    private int port;
}
