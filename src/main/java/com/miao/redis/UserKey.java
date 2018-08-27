package com.miao.redis;

public class UserKey extends BasePrefix{

    private UserKey( String prefix) {
        super(prefix);
    }

    public static UserKey token = new UserKey("token_");
}
