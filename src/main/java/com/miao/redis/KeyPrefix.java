package com.miao.redis;

public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();




}
