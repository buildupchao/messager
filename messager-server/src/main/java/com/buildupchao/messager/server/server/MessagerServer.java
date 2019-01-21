package com.buildupchao.messager.server.server;

import com.buildupchao.messager.common.constant.Constants;
import com.buildupchao.messager.common.protocol.MessagerRequestProto;
import com.buildupchao.messager.server.bean.SendRequestMsgVO;
import com.buildupchao.messager.server.init.MessagerServerInitializer;
import com.buildupchao.messager.server.util.SessionSocketHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:48
 * @since JDK 1.8
 */
@Component
public class MessagerServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();

    @Value("${messager.server.port}")
    private int listenPort;

    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(listenPort))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new MessagerServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            LOGGER.info("Start messager server successfully");
        }
    }

    @PreDestroy
    public void destory() {
        boss.shutdownGracefully().syncUninterruptibly();
        worker.shutdownGracefully().syncUninterruptibly();
    }

    public void sendMsg(SendRequestMsgVO sendRequestMsgVO) {
        NioSocketChannel socketChannel = SessionSocketHelper.get(sendRequestMsgVO.getUserId());
        if (socketChannel == null) {
            throw new NullPointerException(String.format("Client [%d] offline", sendRequestMsgVO.getUserId()));
        }

        MessagerRequestProto.MessagerRequestProtocol protocol = MessagerRequestProto.MessagerRequestProtocol.newBuilder()
                .setType(Constants.CommandType.MESSAGE)
                .setRequestId(sendRequestMsgVO.getUserId())
                .setReqMsg(sendRequestMsgVO.getMessage())
                .build();
        ChannelFuture future = socketChannel.writeAndFlush(protocol);
        future.addListeners((ChannelFutureListener) channelFuture -> {
            LOGGER.info("Messager server send a protocol message [{}] successfully", sendRequestMsgVO.toString());
        });
    }
}
