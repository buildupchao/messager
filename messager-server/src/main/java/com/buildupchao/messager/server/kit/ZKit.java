package com.buildupchao.messager.server.kit;

import com.buildupchao.messager.server.config.AppConfiguration;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:18
 * @since JDK 1.8
 */
@Component
public class ZKit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKit.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private AppConfiguration appConfiguration;

    public void createRootNode() {
        boolean exists = zkClient.exists(appConfiguration.getZkRoot());
        if (exists) {
            return;
        }
        zkClient.createPersistent(appConfiguration.getZkRoot());
    }

    public void createNode(String path) {
        zkClient.createEphemeral(path);
    }
}
