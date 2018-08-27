package com.miao.miaosha.controller;

import com.miao.common.ServerResponse;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.reptile.Reptile;
import com.miao.miaosha.service.UserService;
import com.miao.miaosha.service.RedisService;
import com.miao.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.miao.miaosha.reptile.Reptile.*;

@RestController
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;


    @GetMapping("/hi/{id}")
    public String say(@PathVariable ("id") Integer id) {
        return "id:"+id;
    }

    @GetMapping("/test")
    public String test() {
//        getExternalAPI("http://cp.study.163.com/j/cp/getOrderDownloadUrl.json?tradeStartTime=1530201600000&tradeEndTime=1530374399000&tradeStatus=2&orderUserSource=0");
//        getDownLoadAPI("http://cp.study.163.com/j/cp/getOrderDownloadUrl.json?tradeStartTime=1530201600000&tradeEndTime=1530374399000&tradeStatus=2&orderUserSource=0");
//        http302("http://cp.study.163.com/j/cp/downloadOrders.htm?providerId=3071896&tradeStartTime=1530115200000&tradeEndTime=1530287999000&tradeStatus=2&orderUserSource=0");
//        downloadFile("http://nos.netease.com/edu-common-private/b758191c-eac5-4e15-916d-49e9f41a426a?download=3071896_1530433384507.xls&Signature=0yEoEqy2rUErFKcQ1grjPuABNCBUdaIPkolwh1MPB%2BE%3D&Expires=1530434287&NOSAccessKeyId=7ba71f968e4340f1ab476ecb300190fa", null, "1.xls");
        Reptile reptile = new Reptile();
        reptile.parseWorkbook("");
        return null;
    }

    @GetMapping("/db/get/{id}")
    @ResponseBody
    public ServerResponse<User> dbGet(@PathVariable ("id") Integer id) {

        return ServerResponse.createBySuccess("成功",userService.getUserById(id));
    }

    @GetMapping("/db/tx")
    @ResponseBody
    public ServerResponse<Boolean> dbTx() {

        return ServerResponse.createBySuccess(userService.tx());
    }

    @GetMapping("/mq")
    @ResponseBody
    public ServerResponse<Boolean> mq() {
        mqSender.send("消息队列成功");
        return ServerResponse.createBySuccess();
    }

//    @GetMapping("/redis/get")
//    @ResponseBody
//    public ServerResponse<User> redisGet() {
//        User val=redisService.get(UserKey.ById,""+1, User.class);
//        return ServerResponse.createBySuccess(val);
//    }

//    @GetMapping("/redis/set")
//    @ResponseBody
//    public ServerResponse redisSet() {
//
//
//        return ServerResponse.createBySuccess();
//    }
}
