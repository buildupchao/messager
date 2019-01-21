package com.buildupchao.messager.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:05
 * @since JDK 1.8
 */
@Data
@Component
public class AppConfiguration {

    @Value("${messager.zk.root}")
    private String zkRoot;

    @Value("${messager.zk.addr}")
    private String zkAddr;

    @Value("${messager.zk.switch}")
    private boolean zkSwitch;

    @Value("${messager.clear.route.request.url}")
    private String clearRouteUrl;

    @Value("${messager.server.port}")
    private int messagerServerPort;

    @Value("${messager.heartbeat.time}")
    private long heartBeatTime;
}
