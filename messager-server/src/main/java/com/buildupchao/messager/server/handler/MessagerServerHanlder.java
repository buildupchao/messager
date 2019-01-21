package com.buildupchao.messager.server.handler;

import com.buildupchao.messager.common.bean.UserInfo;
import com.buildupchao.messager.common.constant.Constants;
import com.buildupchao.messager.common.exception.MessagerException;
import com.buildupchao.messager.common.protocol.MessagerRequestProto;
import com.buildupchao.messager.server.config.AppConfiguration;
import com.buildupchao.messager.server.util.NettyAttrUtil;
import com.buildupchao.messager.server.util.SessionSocketHelper;
import com.buildupchao.messager.server.util.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 *         Date: 2019/1/20 12:11
 * @since JDK 1.8
 */
public class MessagerServerHanlder extends SimpleChannelInboundHandler<MessagerRequestProto.MessagerRequestProtocol> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerServerHanlder.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagerRequestProto.MessagerRequestProtocol msg) throws Exception {
        LOGGER.info("Received message=[{}]", msg.toString());

        if (msg.getType() == Constants.CommandType.LOGIN) {
            SessionSocketHelper.put(msg.getRequestId(), (NioSocketChannel) ctx.channel());
            SessionSocketHelper.saveSession(msg.getRequestId(), msg.getReqMsg());
            LOGGER.info("Client [{}] is online", msg.getReqMsg());
        }

        if (msg.getType() == Constants.CommandType.PING) {
            NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        UserInfo userInfo = SessionSocketHelper.getUserInfo((NioSocketChannel) ctx.channel());
        if (userInfo != null) {
            LOGGER.warn("Client [{}] offline because of triggering channelInactive", userInfo.getUserName());
            userOffline(userInfo, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                AppConfiguration configuration = SpringBeanFactory.getBean(AppConfiguration.class);
                long heartBeatTime = configuration.getHeartBeatTime() * 1000;

                long now = System.currentTimeMillis();
                Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
                if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
                    UserInfo userInfo = SessionSocketHelper.getUserInfo((NioSocketChannel) ctx.channel());
                    LOGGER.warn("Client [{}] heartbeat timeout [{}]ms, close channel", userInfo.getUserName(), (now - lastReadTime));

                    userOffline(userInfo, (NioSocketChannel) ctx.channel());
                    ctx.channel().close();
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (MessagerException.isResetByPeer(cause.getMessage())) {
            return;
        }
        LOGGER.error(cause.getMessage(), cause);
    }

    private void userOffline(UserInfo userInfo, NioSocketChannel socketChannel) {
        LOGGER.info("Client [{}] offline", userInfo.getUserName());
        SessionSocketHelper.remove(socketChannel);
        SessionSocketHelper.removeSession(userInfo.getUserId());

        clearRouteInfo(userInfo);
    }

    private void clearRouteInfo(UserInfo userInfo) {
        // to do
        // call route-center api to clear
    }
}
