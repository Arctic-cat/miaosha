package com.miao.miaosha.dao;

import com.miao.miaosha.pojo.Goods;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface GoodsDao {
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id ")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count =stock_count -1 where goods_id = #{goodsId} and stock_count>0")
    int reduceStock(@Param("goodsId")long goodsId);

    @Update("update goods set goods_stock =goods_stock -1 where id = #{goodsId} and goods_stock>0")
    int reduceGoodsStock(@Param("goodsId")long goodsId);
}
