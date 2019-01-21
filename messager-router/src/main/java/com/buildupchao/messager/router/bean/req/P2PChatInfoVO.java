package com.buildupchao.messager.router.bean.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/22 00:00
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P2PChatInfoVO {
    private Long userId;
    private Long receivedUserId;
    private String message;
}
