package com.miao.miaosha.service;

import com.miao.miaosha.pojo.Goods;
import com.miao.miaosha.pojo.MiaoshaOrder;
import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.vo.GoodsVo;
import com.miao.redis.MiaoshaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;

    //事务注解→添加事务，要么全部成功，要么全部失败
    @Transactional
    public Order miaosha(User user, GoodsVo goodsVo) {


        boolean flag = goodsService.reduceStock(goodsVo.getId());
        if (flag) {
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }


    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.goodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exist(MiaoshaKey.goodsOver,"" + goodsId);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean overFlag = getGoodsOver(goodsId);
            if (overFlag) {
                return -1;
            } else {
                return 0;
            }

        }
    }


}
