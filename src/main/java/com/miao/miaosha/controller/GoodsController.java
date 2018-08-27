package com.miao.miaosha.controller;

import com.miao.common.ServerResponse;
import com.miao.miaosha.pojo.MiaoshaOrder;
import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.service.GoodsService;
import com.miao.miaosha.service.MiaoshaService;
import com.miao.miaosha.service.OrderService;
import com.miao.miaosha.vo.GoodsVo;
import com.miao.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    @RequestMapping("/list")
    public ServerResponse listGoodsVo() {
//        @CookieValue(value = CookieUtil.COOKIE_NAME ,required = false) String cookieToken, @RequestParam(value = CookieUtil.COOKIE_NAME,required = false)String paramToken
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();

        return ServerResponse.createBySuccess(goodsVos);
    }

    @RequestMapping("/detail/{goodsId}")
    public ServerResponse detail(@PathVariable("goodsId")long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        return ServerResponse.createBySuccess(goodsVo);
    }


}
