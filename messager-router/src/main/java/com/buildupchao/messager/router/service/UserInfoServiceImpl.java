package com.buildupchao.messager.router.service;

import com.buildupchao.messager.common.bean.UserInfo;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.buildupchao.messager.router.constant.Constants.ACCOUNT_PREFIX;
import static com.buildupchao.messager.router.constant.Constants.LOGIN_STATUS_PREFIX;

/**
 * @author buildupchao
 *         Date: 2019/1/21 22:54
 * @since JDK 1.8
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Map<Long, UserInfo> USER_INFO_MAP = new ConcurrentHashMap<>(64);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean checkAndSaveUserLoginStatus(Long userId) {
        Long add = redisTemplate.opsForSet().add(LOGIN_STATUS_PREFIX, userId.toString());
        if (add == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void removeLoginStatus(Long userId) {
        redisTemplate.opsForSet().remove(LOGIN_STATUS_PREFIX, userId);
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfo userInfo = USER_INFO_MAP.get(userId);
        if (userInfo == null) {
            String userName = redisTemplate.opsForValue().get(ACCOUNT_PREFIX + userId);
            if (!StringUtils.isEmpty(userName)) {
                userInfo = new UserInfo(userId, userName);
                USER_INFO_MAP.put(userId, userInfo);
            }
        }
        return userInfo;
    }

    @Override
    public Set<UserInfo> onlineUsers() {
        Set<String> members = redisTemplate.opsForSet().members(LOGIN_STATUS_PREFIX);
        if (CollectionUtils.isEmpty(members)) {
            return Sets.newHashSetWithExpectedSize(0);
        }
        Set<UserInfo> userInfos = Sets.newHashSet();
        for (String member : members) {
            UserInfo userInfo = getUserInfoByUserId(Long.parseLong(member));
            userInfos.add(userInfo);
        }
        return userInfos;
    }
}
