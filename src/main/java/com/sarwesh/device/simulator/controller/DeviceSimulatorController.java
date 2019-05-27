package com.sarwesh.device.simulator.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.sarwesh.device.simulator.model.DeviceSimulatorMessage;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class DeviceSimulatorController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public DeviceSimulatorMessage sendMessage(@Payload DeviceSimulatorMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public DeviceSimulatorMessage addUser(@Payload DeviceSimulatorMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
