package org.grokking.challenges.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by vinhdp on 26/11/17.
 *
 * Template: https://spring.io/guides/gs/messaging-stomp-websocket/
 */

@Controller
public class DashboardController {

    @MessageMapping("/dashboard")
    @SendTo("/topic/dashboard")
    public Greeting1 getDashboard(HelloMessage message) throws Exception {

        System.out.println(message.getName());
        Thread.sleep(1000); // simulated delay
        return new Greeting1("Hello, " + message.getName() + "!");
    }
}

class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Greeting1 {

    private String content;

    public Greeting1() {
    }

    public Greeting1(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
