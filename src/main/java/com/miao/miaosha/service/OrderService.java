package com.miao.miaosha.service;

import com.miao.common.ServerResponse;
import com.miao.miaosha.dao.GoodsDao;
import com.miao.miaosha.dao.OrderDao;
import com.miao.miaosha.pojo.MiaoshaOrder;
import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;


    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public Order createOrder(User user, GoodsVo goodsVo) {
        long userId = user.getId();
        //生成普通订单
        Order order = new Order();
        order.setDeliveryAddrId(0l);
        order.setGoodsCount(1);
        order.setGoodsId(goodsVo.getId());
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsPrice(goodsVo.getGoodsPrice());
        order.setStatus(0);
        order.setUserId(userId);
        long orderId = orderDao.insert(order);
        //生成秒杀订单
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(userId);
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return order;
    }
}
