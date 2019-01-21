package com.buildupchao.messager.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author buildupchao
 *         Date: 2019/1/20 16:44
 * @since JDK 1.8
 */
@SpringBootApplication
public class MessagerRouterApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerRouterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MessagerRouterApplication.class, args);
        LOGGER.info("Start messager router successfully");
    }
}
