package com.miao.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.miao.redis.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis服务层,配置redis的增删改查方法
 */
@Service
public class RedisService {
    //redis连接池
    @Autowired
    JedisPool jedisPool;


    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();
        String realKey  = prefix.getPrefix() + key;
        String  str = jedis.get(realKey);
        T t = stringToBean(str, clazz);

        return t;

    }

    /**
     * 设置对象
     */
    public <T> boolean set(KeyPrefix prefix,String key, T value) {
        Jedis jedis = null;
        jedis = jedisPool.getResource();
        String str = beanToString(value);
        if(str == null || str.length() <= 0) {
            return false;
        }
        //生成真正的key
        String realKey  = prefix.getPrefix() + key;
        int seconds =  prefix.expireSeconds();
        if(seconds <= 0) {
            jedis.set(realKey, str);
        }else {
            jedis.setex(realKey, seconds, str);
        }
        return true;
    }

    /**
     * 判断记录是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        jedis = jedisPool.getResource();
        //生成真正的key
        String realKey  = prefix.getPrefix() + key;
        return  jedis.exists(realKey);
    }

    /**
     * 删除记录
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        jedis =  jedisPool.getResource();
        //生成真正的key
        String realKey  = prefix.getPrefix() + key;
        long ret =  jedis.del(realKey);
        return ret > 0;

    }

    /**
     * 增加值,数值加一并返回数值结果
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        jedis =  jedisPool.getResource();
        //生成真正的key
        String realKey  = prefix.getPrefix() + key;
        return  jedis.incr(realKey);

    }

    /**
     * 减少值,数值减一并返回数值结果
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        jedis =  jedisPool.getResource();
        //生成真正的key
        String realKey  = prefix.getPrefix() + key;
        return  jedis.decr(realKey);

    }


    /**
     * 对象转string
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * string转对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }

    }


}
