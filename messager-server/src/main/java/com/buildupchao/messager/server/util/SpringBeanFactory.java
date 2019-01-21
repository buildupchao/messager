package com.buildupchao.messager.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:51
 * @since JDK 1.8
 */
@Component
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> cls) {
        return context.getBean(cls);
    }

    public static <T> T getBean(String name, Class<T> cls) {
        return context.getBean(name, cls);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
