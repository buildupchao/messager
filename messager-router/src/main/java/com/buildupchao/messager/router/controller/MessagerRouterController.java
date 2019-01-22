package com.buildupchao.messager.router.controller;

import com.buildupchao.messager.common.bean.BaseResponse;
import com.buildupchao.messager.common.bean.NullBody;
import com.buildupchao.messager.common.bean.UserInfo;
import com.buildupchao.messager.common.enums.StatusEnum;
import com.buildupchao.messager.common.util.ResponseHelper;
import com.buildupchao.messager.router.bean.req.GroupChatInfoVO;
import com.buildupchao.messager.router.bean.req.LoginReqVO;
import com.buildupchao.messager.router.bean.req.P2PChatInfoVO;
import com.buildupchao.messager.router.bean.req.RegisterReqVO;
import com.buildupchao.messager.router.bean.res.MessagerServerInfoVO;
import com.buildupchao.messager.router.bean.res.RegisterResVO;
import com.buildupchao.messager.router.cache.ServerRouteCache;
import com.buildupchao.messager.router.service.AccountService;
import com.buildupchao.messager.router.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author buildupchao
 *         Date: 2019/1/21 23:49
 * @since JDK 1.8
 */
@RestController
public class MessagerRouterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagerRouterController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServerRouteCache serverRouteCache;


    @PostMapping("/group/chat")
    public BaseResponse<NullBody> groupChat(@RequestBody GroupChatInfoVO userSendChatInfo) throws Exception {
        Map<Long, MessagerServerInfoVO> routeMap = accountService.loadRouteInfos();
        for (Map.Entry<Long, MessagerServerInfoVO> entry : routeMap.entrySet()) {
            Long userId = entry.getKey();
            MessagerServerInfoVO serverInfo = entry.getValue();
            if (userId.equals(userSendChatInfo.getUserId())) {
                continue;
            }

            String url = String.format("http://%s:%d/sendMsg", serverInfo.getHost(), serverInfo.getHttpPort());
            GroupChatInfoVO chatInfo = new GroupChatInfoVO(userId, userSendChatInfo.getMessage());
            accountService.pushMsg(url, userSendChatInfo.getUserId(), chatInfo);
        }

        return ResponseHelper.createSuccess();
    }

    @PostMapping("/p2p/chat")
    public BaseResponse<NullBody> p2pChat(@RequestBody P2PChatInfoVO p2PChatInfo) throws Exception {
        MessagerServerInfoVO serverInfo = accountService.getRouteInfoByUserId(p2PChatInfo.getReceivedUserId());

        String url = String.format("http://%s:%d/sendMsg", serverInfo.getHost(), serverInfo.getHttpPort());
        GroupChatInfoVO chatInfo = new GroupChatInfoVO(p2PChatInfo.getReceivedUserId(), p2PChatInfo.getMessage());

        accountService.pushMsg(url, p2PChatInfo.getUserId(), chatInfo);
        return ResponseHelper.createSuccess();
    }

    @PostMapping("/register")
    public BaseResponse<RegisterResVO> register(@RequestBody RegisterReqVO registerReqInfo) throws Exception {
        Long userId = System.currentTimeMillis();
        RegisterResVO registerResInfo = new RegisterResVO();
        registerResInfo.setUserId(userId);
        registerResInfo.setUserName(registerReqInfo.getUserName());
        registerResInfo = accountService.register(registerResInfo);

        return ResponseHelper.createSuccess(registerResInfo);
    }

    @PostMapping("/login")
    public BaseResponse<MessagerServerInfoVO> login(@RequestBody LoginReqVO loginReqInfo) throws Exception {
        StatusEnum status = accountService.login(loginReqInfo);
        if (status == StatusEnum.SUCCESS) {
            String server = serverRouteCache.selectServer();
            String[] serverInfos = server.split(":");
            MessagerServerInfoVO messagerServerInfo = MessagerServerInfoVO.builder()
                    .host(serverInfos[0]).zkPort(Integer.parseInt(serverInfos[1])).httpPort(Integer.parseInt(serverInfos[2]))
                    .build();
            accountService.saveRouteInfo(loginReqInfo, server);

            return ResponseHelper.createSuccess(messagerServerInfo);
        }
        return ResponseHelper.createSuccess();
    }

    @PostMapping("/users/online")
    public BaseResponse<Set<UserInfo>> onlineUsers() throws Exception {
        Set<UserInfo> userInfos = userInfoService.onlineUsers();
        return ResponseHelper.createSuccess(userInfos);
    }

    public BaseResponse<NullBody> offline(@RequestBody GroupChatInfoVO chatInfo) throws Exception {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(chatInfo.getUserId());
        LOGGER.info("User [{}] offline", userInfo.toString());

        accountService.offline(chatInfo.getUserId());
        return ResponseHelper.createSuccess();
    }
}
