package com.buildupchao.messager.server.util;

import com.buildupchao.messager.common.bean.UserInfo;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buildupchao
 *         Date: 2019/1/20 12:22
 * @since JDK 1.8
 */
public class SessionSocketHelper {

    private static final Map<Long, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);
    private static final Map<Long, String> SESSION_MAP = new ConcurrentHashMap<>(16);

    public static void saveSession(Long userId, String userName) {
        SESSION_MAP.put(userId, userName);
    }

    public static void removeSession(Long userId) {
        SESSION_MAP.remove(userId);
    }

    public static void put(Long userId, NioSocketChannel socketChannel) {
        CHANNEL_MAP.put(userId, socketChannel);
    }

    public static NioSocketChannel get(Long userId) {
        return CHANNEL_MAP.get(userId);
    }

    public static void remove(NioSocketChannel socketChannel) {
        CHANNEL_MAP.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(socketChannel))
                .forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

    public static Map<Long, NioSocketChannel> getChannelMap() {
        return CHANNEL_MAP;
    }

    public static UserInfo getUserInfo(NioSocketChannel socketChannel) {
        UserInfo userInfo = null;
        for (Map.Entry<Long, NioSocketChannel> entry : CHANNEL_MAP.entrySet()) {
            if (entry.getValue().equals(socketChannel)) {
                Long userId = entry.getKey();
                String userName = SESSION_MAP.get(userId);
                userInfo = new UserInfo(userId, userName);
                break;
            }
        }
        return userInfo;
    }
}
