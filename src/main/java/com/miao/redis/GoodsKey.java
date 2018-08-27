package com.miao.redis;

public class GoodsKey extends BasePrefix{

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static GoodsKey goodsList = new GoodsKey(60, "goodList");
	public static GoodsKey goodsDetail = new GoodsKey(60, "goodDetail");
	public static GoodsKey miaoshaGoodsStock= new GoodsKey(0, "goodsStock");
}
