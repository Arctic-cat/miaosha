package com.miao.miaosha.dao;

import com.miao.miaosha.pojo.MiaoshaOrder;
import com.miao.miaosha.pojo.Order;
import com.miao.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface OrderDao {

    @Select("select * from miaosha_order where user_id = #{userId} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);

    @Insert("insert into order_info (user_id,goods_id,goods_name,goods_count,goods_price,status) values(" +
            "#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{status}) ")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(Order order);

    @Insert("insert into miaosha_order (user_id,goods_id,order_id)values(#{userId},#{goodsId},#{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
