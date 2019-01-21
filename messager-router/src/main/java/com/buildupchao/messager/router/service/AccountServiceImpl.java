package com.buildupchao.messager.router.service;

import com.alibaba.fastjson.JSONObject;
import com.buildupchao.messager.common.bean.UserInfo;
import com.buildupchao.messager.common.enums.StatusEnum;
import com.buildupchao.messager.common.exception.MessagerException;
import com.buildupchao.messager.router.bean.req.GroupChatInfoVO;
import com.buildupchao.messager.router.bean.req.LoginReqVO;
import com.buildupchao.messager.router.bean.res.MessagerServerInfoVO;
import com.buildupchao.messager.router.bean.res.RegisterResVO;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.buildupchao.messager.router.constant.Constants.ACCOUNT_PREFIX;
import static com.buildupchao.messager.router.constant.Constants.ROUTE_PREFIX;

/**
 * @author buildupchao
 *         Date: 2019/1/21 20:28
 * @since JDK 1.8
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private MediaType mediaType = MediaType.parse("application/json");

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public StatusEnum login(LoginReqVO loginReqVO) throws Exception {
        String key = ACCOUNT_PREFIX + loginReqVO.getUserId();

        String name = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(name)) {
            return StatusEnum.ACCOUNT_NOT_EXISTS;
        }
        if (!name.equals(loginReqVO.getUserName())) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        boolean status = userInfoService.checkAndSaveUserLoginStatus(loginReqVO.getUserId());
        if (status == false) {
            return StatusEnum.REPEAT_LOGIN;
        }
        return StatusEnum.SUCCESS;
    }

    @Override
    public RegisterResVO register(RegisterResVO registerResVO) throws Exception {
        String key = ACCOUNT_PREFIX + registerResVO.getUserId();

        String name = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(name)) {
            redisTemplate.opsForValue().set(key, registerResVO.getUserName());
            redisTemplate.opsForValue().set(registerResVO.getUserName(), key);
        } else {
            Long userId = Long.parseLong(name.split(":")[1]);
            registerResVO.setUserId(userId);
            registerResVO.setUserName(name);
        }
        return registerResVO;
    }

    @Override
    public void offline(Long userId) throws Exception {
        redisTemplate.delete(ROUTE_PREFIX + userId);
        userInfoService.removeLoginStatus(userId);
    }

    @Override
    public void saveRouteInfo(LoginReqVO loginReqVO, String routeInfo) throws Exception {
        String key = ROUTE_PREFIX + loginReqVO;
        redisTemplate.opsForValue().set(key, routeInfo);
    }

    @Override
    public MessagerServerInfoVO getRouteInfoByUserId(Long userId) {
        String routeInfo = redisTemplate.opsForValue().get(ROUTE_PREFIX + userId);
        if (routeInfo == null) {
            throw new MessagerException(StatusEnum.OFF_LINE);
        }

        String[] serverInfos = routeInfo.split(":");
        MessagerServerInfoVO messagerServerInfo = MessagerServerInfoVO.builder()
                .host(serverInfos[0]).zkPort(Integer.parseInt(serverInfos[1]))
                .httpPort(Integer.parseInt(serverInfos[2]))
                .build();
        return messagerServerInfo;
    }

    @Override
    public Map<Long, MessagerServerInfoVO> loadRouteInfos() {
        Map<Long, MessagerServerInfoVO> routes = Maps.newHashMapWithExpectedSize(64);

        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        ScanOptions options = ScanOptions.scanOptions().match(ROUTE_PREFIX + "*").build();
        Cursor<byte[]> scan = connection.scan(options);

        while (scan.hasNext()) {
            byte[] next = scan.next();
            String key = new String(next, StandardCharsets.UTF_8);
            parseServerInfo(routes, key);
        }
        return routes;
    }

    @Override
    public void pushMsg(String url, long sendUserId, GroupChatInfoVO groupChatInfo) throws Exception {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(sendUserId);

        JSONObject jsonData = new JSONObject();
        jsonData.put("userId", groupChatInfo.getUserId());
        jsonData.put("msg", String.format("%s:【%s】", userInfo.getUserName(), groupChatInfo.getMessage()));

        RequestBody requestBody = RequestBody.create(mediaType, jsonData.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        try {
            if (!response.isSuccessful()) {
                throw new MessagerException("Unexcepted code " + response);
            }
        } finally {
            response.body().close();
        }
    }

    private void parseServerInfo(Map<Long, MessagerServerInfoVO> routes, String key) {
        Long userId = Long.parseLong(key.split(":")[1]);
        MessagerServerInfoVO messagerServerInfo = getRouteInfoByUserId(userId);
        routes.put(userId, messagerServerInfo);
    }
}
