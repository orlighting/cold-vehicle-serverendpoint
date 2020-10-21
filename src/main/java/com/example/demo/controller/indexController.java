package com.example.demo.controller;


import com.example.demo.mapper.DriverMapper;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.HumiMapper;
import com.example.demo.mapper.TempMapper;
import com.example.demo.pojo.Driver;
import com.example.demo.pojo.FaceBase64;
import com.example.demo.pojo.Good;
import com.example.demo.pojo.sensor.Humi;
import com.example.demo.pojo.sensor.Temp;
import com.example.demo.result.indexResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Controller
public class indexController {

    @Resource
    DriverMapper driverMapper;
    @Resource
    GoodMapper goodMapper;
    @Resource
    TempMapper tempMapper;
    @Resource
    HumiMapper humiMapper;

    @CrossOrigin
    @PostMapping("api/indexGet")
    @ResponseBody
    public indexResult indesInfo(@RequestBody FaceBase64 tempid){

        String tempDrid = tempid.getDriverId();
        tempDrid = HtmlUtils.htmlEscape(tempDrid);

        Driver tempdr = driverMapper.findByNum(tempDrid);
        Good tempgd = goodMapper.findByTime("1");
        Temp temptp = tempMapper.findByTime("1");
        Humi temphm = humiMapper.findByTime("1");


        return new indexResult(temptp,temphm,tempgd.getKinds(),tempdr);
    }


}
