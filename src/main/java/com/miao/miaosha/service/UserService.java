package com.miao.miaosha.service;

import com.miao.common.CodeMsg;
import com.miao.common.ServerResponse;
import com.miao.exception.GlobalException;
import com.miao.miaosha.dao.UserDao;
import com.miao.miaosha.pojo.User;
import com.miao.redis.UserKey;
import com.miao.util.MD5Util;
import com.miao.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.miao.util.CookieUtil.writeLoginToken;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public boolean tx() {

        return true;
    }

    public User getUserByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长cookie有效期
        if(user != null) {
            writeLoginToken(response, token);
            //延长redis有效期
            redisService.set(UserKey.token, token, user);
        }
        return user;
    }


    public ServerResponse<User> login(Integer id,String password, HttpServletResponse response) {


        User user = getUserById(id);
        if (user == null) {
            return ServerResponse.createByError();
        }

        String MD5Password = MD5Util.inputPassToDBPass(password, user.getSalt());

        if (!StringUtils.equals(user.getPassword(), MD5Password)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        user.setPassword(null);
        user.setSalt(null);

        //生成cookies
        String token = UUIDUtil.uuid();
        redisService.set(UserKey.token, token, user);

        writeLoginToken(response,token);

        return ServerResponse.createBySuccess(user);
    }


}
