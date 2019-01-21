package com.buildupchao.messager.server;

import com.buildupchao.messager.server.config.AppConfiguration;
import com.buildupchao.messager.server.kit.ZKRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:45
 * @since JDK 1.8
 */
@SpringBootApplication
public class MessagerServerApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerServerApplication.class);

    @Autowired
    private AppConfiguration appConfiguration;

    @Value("${server.port}")
    private int httpPort;

    public static void main(String[] args) {
        SpringApplication.run(MessagerServerApplication.class, args);
        LOGGER.info("Start messager server application successfully");
    }

    @Override
    public void run(String... strings) throws Exception {
        String addr = InetAddress.getLocalHost().getHostAddress();
        Thread registryListener = new Thread(new ZKRegistry(addr, appConfiguration.getMessagerServerPort(), httpPort));
        registryListener.setName("zk-registry");
        registryListener.start();
    }
}
