package com.buildupchao.messager.router.cache;

import com.buildupchao.messager.common.exception.MessagerException;
import com.buildupchao.messager.router.kit.ZKit;
import com.google.common.cache.LoadingCache;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author buildupchao
 *         Date: 2019/1/21 19:57
 * @since JDK 1.8
 */
@Component
public class ServerRouteCache {

    @Autowired
    private LoadingCache<String, String> cache;

    @Autowired
    private ZKit zKit;

    private AtomicLong counter = new AtomicLong();


    public void addCache(String key) {
        cache.put(key, key);
    }

    public void updateCache(List<String> newServerRoutes) {
        cache.invalidateAll();
        for (String serverRoute : newServerRoutes) {
            String realServerRoute = serverRoute.split("-")[1];
            addCache(realServerRoute);
        }
    }

    public List<String> listAll() {
        if (cache.size() == 0) {
            reloadCache();
        }

        List<String> serverRoutes = Lists.newArrayList(cache.asMap().keySet());
        return serverRoutes;
    }

    public String selectServer() {
        if (cache.size() == 0) {
            throw new MessagerException("Not any messager server which can provide service");
        }

        List<String> allServerRoutes = listAll();
        Long position = counter.incrementAndGet() % allServerRoutes.size();
        if (position < 0) {
            position = 0L;
        }
        return allServerRoutes.get(position.intValue());
    }

    private void reloadCache() {
        List<String> allNode = zKit.getAllNode();
        for (String serverRoute : allNode) {
            String realServerRoute = serverRoute.split("-")[1];
            addCache(realServerRoute);
        }
    }
}
