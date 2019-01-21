package com.buildupchao.messager.router.kit;

import com.buildupchao.messager.router.config.AppConfiguration;
import com.buildupchao.messager.router.util.SpringBeanFactory;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 *         Date: 2019/1/21 20:21
 * @since JDK 1.8
 */
public class ServerRouteListener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerRouteListener.class);

    private AppConfiguration appConfiguration;

    private ZKit zKit;

    public ServerRouteListener() {
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
        zKit = SpringBeanFactory.getBean(ZKit.class);
    }

    @Override
    public void run() {
        zKit.subscribeEvent(appConfiguration.getZkRoot());
    }
}
