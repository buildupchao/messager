package com.buildupchao.messager.server.bean;

import com.buildupchao.messager.common.bean.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:51
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestMsgVO extends BaseRequest {

    private Long userId;
    private String message;
}
