package com.buildupchao.messager.router.service;

import com.buildupchao.messager.common.enums.StatusEnum;
import com.buildupchao.messager.router.bean.req.GroupChatInfoVO;
import com.buildupchao.messager.router.bean.req.LoginReqVO;
import com.buildupchao.messager.router.bean.res.MessagerServerInfoVO;
import com.buildupchao.messager.router.bean.res.RegisterResVO;

import java.util.Map;

/**
 * @author buildupchao
 *         Date: 2019/1/21 20:27
 * @since JDK 1.8
 */
public interface AccountService {

    StatusEnum login(LoginReqVO loginReqVO) throws Exception;

    RegisterResVO register(RegisterResVO registerResVO) throws Exception;

    void offline(Long userId) throws Exception;

    void saveRouteInfo(LoginReqVO loginReqVO, String msg) throws Exception;

    MessagerServerInfoVO getRouteInfoByUserId(Long userId);

    Map<Long, MessagerServerInfoVO> loadRouteInfos();

    void pushMsg(String url, long sendUserId, GroupChatInfoVO groupChatInfo) throws Exception;
}
