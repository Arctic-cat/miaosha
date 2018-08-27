package com.miao.miaosha.service;

import com.miao.miaosha.dao.GoodsDao;
import com.miao.miaosha.pojo.Goods;
import com.miao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(long goodsId) {
        int ret = goodsDao.reduceStock(goodsId);
        int result =goodsDao.reduceGoodsStock(goodsId);
        return ret > 0&&result>0;
    }
}
