package com.buildupchao.messager.server.config;

import com.buildupchao.messager.common.constant.Constants;
import com.buildupchao.messager.common.protocol.MessagerRequestProto;
import okhttp3.OkHttpClient;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:22
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Autowired
    private AppConfiguration appConfiguration;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(appConfiguration.getZkAddr(), 5000);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Bean("heartBeat")
    public MessagerRequestProto.MessagerRequestProtocol heartBeat() {
        return MessagerRequestProto.MessagerRequestProtocol.newBuilder()
                .setRequestId(0L)
                .setReqMsg("ping")
                .setType(Constants.CommandType.PING)
                .build();
    }
}
