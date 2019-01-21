package com.buildupchao.messager.server.kit;

import com.buildupchao.messager.server.config.AppConfiguration;
import com.buildupchao.messager.server.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:19
 * @since JDK 1.8
 */
public class ZKRegistry implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKRegistry.class);

    private ZKit zKit;
    private AppConfiguration appConfiguration;

    private String ip;
    private int messagerServerPort;
    private int httpPort;

    public ZKRegistry(String ip, int messagerServerPort, int httpPort) {
        this.ip = ip;
        this.messagerServerPort = messagerServerPort;
        this.httpPort = httpPort;
        zKit = SpringBeanFactory.getBean(ZKit.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        zKit.createRootNode();

        if (appConfiguration.isZkSwitch()) {
            String path = appConfiguration.getZkRoot() + "/ip-" + ip + ":" + messagerServerPort + ":" + httpPort;
            zKit.createNode(path);
            LOGGER.info("Register into zookeeper successfully, path=[{}]", path);
        }
    }
}
