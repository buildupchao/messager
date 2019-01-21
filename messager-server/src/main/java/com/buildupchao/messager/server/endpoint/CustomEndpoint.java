package com.buildupchao.messager.server.endpoint;

import com.buildupchao.messager.server.util.SessionSocketHelper;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import java.util.Map;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:47
 * @since JDK 1.8
 */
public class CustomEndpoint extends AbstractEndpoint<Map<Long, NioSocketChannel>> {

    public CustomEndpoint(String id) {
        super(id, false);
    }

    @Override
    public Map<Long, NioSocketChannel> invoke() {
        return SessionSocketHelper.getChannelMap();
    }
}
