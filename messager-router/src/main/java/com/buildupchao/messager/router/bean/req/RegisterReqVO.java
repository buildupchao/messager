package com.buildupchao.messager.router.bean.req;

import com.buildupchao.messager.common.bean.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/21 20:33
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReqVO extends BaseRequest {
    private String userName;
}
