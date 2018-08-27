package com.miao.rabbitmq;

import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.service.GoodsService;
import com.miao.miaosha.service.MiaoshaService;
import com.miao.miaosha.service.RedisService;
import com.miao.miaosha.vo.GoodsVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    @Autowired
    GoodsService goodsService;
    @Autowired
    MiaoshaService miaoshaService;


//    @RabbitListener(queues = MQConfig.QUEUE_1)
//    public void receiver(String message) {
//        System.out.println("收到消息："+message);
//
//    }

    @RabbitListener(queues = MQConfig.QUEUE_1)
    public void receiveMiaoshaMsg(String message) {
        MiaoshaMsg miaoshaMsg = RedisService.stringToBean(message, MiaoshaMsg.class);
        User user = miaoshaMsg.getUser();
        long goodsId = miaoshaMsg.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if (stock <= 0) {
            return;
        }
        miaoshaService.miaosha(user,goodsVo);

    }

}
