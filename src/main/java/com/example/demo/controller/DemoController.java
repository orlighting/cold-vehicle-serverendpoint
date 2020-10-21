package com.example.demo.controller;


import com.example.demo.mapper.HumiMapper;
import com.example.demo.pojo.Demo;
import com.example.demo.pojo.WebSocketUtil;
import com.example.demo.result.Result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

@Controller
@ServerEndpoint("/websocket/doorState")
public class DemoController{

    @Resource
    HumiMapper humiMapper;


    @CrossOrigin
    @PostMapping("api/demo")
    @ResponseBody
    public Result demo(@RequestBody Demo requestdemo){

        String testdata = requestdemo.getDoorState();

        System.out.println(123+testdata);

        if(Objects.equals(testdata,"open")){
            humiMapper.updateDoor("1","1");
        }
        else if(Objects.equals(testdata,"close")){
            humiMapper.updateDoor("1","2");
        }

        WebSocketUtil.sendMessageToAllOnlineUser("1");

        return new Result(200);

    }

    private static void sendMessage(Session session, String message) {

        final RemoteEndpoint.Basic basic = session.getBasicRemote();

        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void openSession(Session session){
        //存储用户
        WebSocketUtil.USERS_ONLINE.put("username", session);

    }


    @OnClose
    public void closeSession(Session session){


        //删除用户
        WebSocketUtil.USERS_ONLINE.remove("username");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message){


    }

    @OnError
    public void sessionError(Session session, Throwable throwable){
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("WebSocket连接发生异常，message:"+throwable.getMessage());
    }


}







