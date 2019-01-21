package com.buildupchao.messager.common.constant;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:31
 * @since JDK 1.8
 */
public class Constants {

    public static final String COUNTER_SERVER_PUSH_COUNT = "counter.server.push.count";

    public static final String COUNTER_CLIENT_PUSH_COUNT = "counter.client.push.count";

    public static class CommandType {
        public static final int LOGIN = 1;

        public static final int MESSAGE = 2;

        public static final int PING = 3;
    }
}
