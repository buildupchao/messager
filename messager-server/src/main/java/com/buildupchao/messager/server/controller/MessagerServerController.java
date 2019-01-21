package com.buildupchao.messager.server.controller;

import com.buildupchao.messager.common.bean.BaseResponse;
import com.buildupchao.messager.common.constant.Constants;
import com.buildupchao.messager.common.util.ResponseHelper;
import com.buildupchao.messager.server.bean.SendRequestMsgVO;
import com.buildupchao.messager.server.bean.SendResponseMsgVO;
import com.buildupchao.messager.server.server.MessagerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author buildupchao
 *         Date: 2019/1/20 13:55
 * @since JDK 1.8
 */
@RestController
public class MessagerServerController {

    @Autowired
    private MessagerServer messagerServer;

    @Autowired
    private CounterService counterService;


    @PostMapping("/sendMsg")
    public BaseResponse<SendResponseMsgVO> sendMsg(@RequestBody SendRequestMsgVO sendRequestMsgVO) {
        messagerServer.sendMsg(sendRequestMsgVO);
        counterService.increment(Constants.COUNTER_SERVER_PUSH_COUNT);

        SendResponseMsgVO sendResponseMsgVO = new SendResponseMsgVO("OK");
        return ResponseHelper.create(sendResponseMsgVO);
    }
}
