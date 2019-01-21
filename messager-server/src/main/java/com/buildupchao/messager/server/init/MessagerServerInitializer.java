package com.buildupchao.messager.server.init;

import com.buildupchao.messager.common.protocol.MessagerRequestProto;
import com.buildupchao.messager.server.handler.MessagerServerHanlder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author buildupchao
 *         Date: 2019/1/20 12:01
 * @since JDK 1.8
 */
public class MessagerServerInitializer extends ChannelInitializer<Channel> {

    private final MessagerServerHanlder messagerServerHanlder = new MessagerServerHanlder();

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new IdleStateHandler(30, 0, 0))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessagerRequestProto.MessagerRequestProtocol.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(messagerServerHanlder)
                ;
    }
}
