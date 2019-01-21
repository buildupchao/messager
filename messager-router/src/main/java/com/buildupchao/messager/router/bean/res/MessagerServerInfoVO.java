package com.buildupchao.messager.router.bean.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/21 22:59
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagerServerInfoVO {
    private String host;
    private int zkPort;
    private Integer httpPort;
}
