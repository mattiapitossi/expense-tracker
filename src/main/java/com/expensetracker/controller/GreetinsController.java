package com.mattiap.expensetracker.controller;

import com.mattiap.expensetracker.model.Message;
import com.mattiap.expensetracker.model.OutputMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GreetinsController {

    @MessageMapping("/chat/{pageId}")
    @SendTo("/topic/messages/{pageId}")
    OutputMessage send(Message message, @DestinationVariable("pageId") String pageId) {
        var time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.from(), message.text(), time);
    }
}