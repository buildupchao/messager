package com.buildupchao.messager.router.kit;

import com.alibaba.fastjson.JSON;
import com.buildupchao.messager.router.cache.ServerRouteCache;
import com.buildupchao.messager.router.config.AppConfiguration;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author buildupchao
 *         Date: 2019/1/21 19:43
 * @since JDK 1.8
 */
@Component
public class ZKit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKit.class);

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ServerRouteCache serverRouteCache;

    public void subscribeEvent(String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                serverRouteCache.updateCache(list);
            }
        });
    }

    public List<String> getAllNode() {
        List<String> children = zkClient.getChildren(appConfiguration.getZkRoot());
        LOGGER.info("Got all zookeeper node info.", JSON.toJSONString(children));
        return children;
    }
}
