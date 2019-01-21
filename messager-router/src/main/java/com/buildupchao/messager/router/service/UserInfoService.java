package com.buildupchao.messager.router.service;

import com.buildupchao.messager.common.bean.UserInfo;

import java.util.Set;

/**
 * @author buildupchao
 *         Date: 2019/1/21 22:52
 * @since JDK 1.8
 */
public interface UserInfoService {

    boolean checkAndSaveUserLoginStatus(Long userId);

    void removeLoginStatus(Long userId);

    UserInfo getUserInfoByUserId(Long userId);

    Set<UserInfo> onlineUsers();
}
