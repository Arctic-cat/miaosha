package com.miao.miaosha.controller;

import com.miao.common.ServerResponse;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.service.GoodsService;
import com.miao.miaosha.service.OrderService;
import com.miao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    @RequestMapping("/info")
    public ServerResponse info(User user) {

        return ServerResponse.createBySuccess(user);
    }




}
