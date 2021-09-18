package com.learn.netty.tio.controller;

import com.learn.netty.tio.common.packet.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.core.starter.TioServerBootstrap;

@RestController
public class HelloController {
    static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private TioServerBootstrap bootstrap;
    @GetMapping("/")
    public String index()
    {
        return "Hello, tio-spring-boot-starter !!!";
    }
    /**
     * 推送消息到客户端
     * @throws Exception
     */
    @GetMapping("/push/{message}")
    public String pushMessage(@PathVariable String message) throws Exception {
        HelloPacket packet = new HelloPacket();
        packet.setBody(message.getBytes(HelloPacket.CHARSET));
        Tio.sendToAll(bootstrap.getServerTioConfig(), packet);
        logger.info("Push a message to client successfully");
        return "Push a message to client successfully";
    }
}