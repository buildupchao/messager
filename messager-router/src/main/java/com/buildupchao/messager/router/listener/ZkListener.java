package com.buildupchao.messager.router.listener;

import com.buildupchao.messager.router.kit.ServerRouteListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/1/22 00:30
 * @since JDK 1.8
 */
@Component
public class ZkListener implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        Thread zkListener = new Thread(new ServerRouteListener());
        zkListener.setName("zk-listener");
        zkListener.start();
    }
}
