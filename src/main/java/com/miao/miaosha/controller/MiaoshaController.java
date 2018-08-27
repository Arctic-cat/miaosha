package com.miao.miaosha.controller;

import com.miao.common.ServerResponse;
import com.miao.miaosha.pojo.MiaoshaOrder;
import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.service.GoodsService;
import com.miao.miaosha.service.MiaoshaService;
import com.miao.miaosha.service.OrderService;
import com.miao.miaosha.service.RedisService;
import com.miao.miaosha.vo.GoodsVo;
import com.miao.rabbitmq.MQSender;
import com.miao.rabbitmq.MiaoshaMsg;
import com.miao.redis.GoodsKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class MiaoshaController implements InitializingBean{
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;
    @Autowired
    RedisService redisService;
    @Autowired
    MQSender mqSender;

    private Map<Long,Boolean> localOverMap = new HashMap<>();

    @RequestMapping("/miaosha/{goodsId}")
    public ServerResponse miaosha(User user,@PathVariable("goodsId")long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if (stock <= 0) {
            return ServerResponse.createByErrorMessage("每个用户只能秒杀一个产品");
        }

        //内存标记，减少对redis访问
        boolean over = localOverMap.get(goodsId);

        if (over) {
            return ServerResponse.createByError();
        }

        //连接redis数据库,减库存并判断，如果库存结果小于0则表示商品已秒光
        long redisStock = redisService.decr(GoodsKey.miaoshaGoodsStock, "" + goodsVo.getId());
        if (redisStock < 0) {
            localOverMap.put(goodsId, true);
            return ServerResponse.createByErrorMessage("秒杀失败");
        }

        //一个用户只允许秒杀一件商品
        //判断是否秒过商品,秒过就不允许再秒了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);

        if (miaoshaOrder != null) {
            return ServerResponse.createByError();
        }
        //入队
        MiaoshaMsg miaoshaMsg = new MiaoshaMsg();
        miaoshaMsg.setUser(user);
        miaoshaMsg.setGoodsId(goodsId);

        mqSender.sendMiaoshaMsg(miaoshaMsg);

        return ServerResponse.createBySuccess("排队中");
    }

    /**
     * orderId:成功
     * -1:失败
     * 0:排队中
     * @return
     */
    @RequestMapping("/result")
    public ServerResponse miaoshaResult(User user, @RequestParam("goodsId")long goodsId) {

        long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);

        return ServerResponse.createBySuccess(result);
    }

    /**
     * 系统初始化时开始搞事情（加载库存到redis）
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        if (goodsVos == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsVos) {
            redisService.set(GoodsKey.miaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getGoodsStock());
            localOverMap.put(goodsVo.getId(), false);
        }
    }
}
