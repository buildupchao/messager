package com.buildupchao.messager.router.bean.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/21 20:36
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResVO {
    private Long userId;
    private String userName;
}
