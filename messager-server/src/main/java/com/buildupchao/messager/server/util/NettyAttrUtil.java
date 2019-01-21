package com.buildupchao.messager.server.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author buildupchao
 *         Date: 2019/1/20 12:45
 * @since JDK 1.8
 */
public class NettyAttrUtil {

    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("reader-time");

    public static void updateReaderTime(Channel channel, Long timestamp) {
        channel.attr(ATTR_KEY_READER_TIME).set(timestamp.toString());
    }

    public static Long getReaderTime(Channel channel) {
        String value = getAttribute(channel, ATTR_KEY_READER_TIME);
        if (value != null) {
            return Long.valueOf(value);
        }
        return null;
    }

    private static String getAttribute(Channel channel, AttributeKey<String> key) {
        return channel.attr(key).get();
    }
}
