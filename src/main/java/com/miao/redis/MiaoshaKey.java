package com.miao.redis;

public class MiaoshaKey extends BasePrefix{

	private MiaoshaKey(String prefix) {
		super(prefix);
	}
	public static MiaoshaKey goodsOver = new MiaoshaKey("go");
}
