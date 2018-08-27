package com.miao.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class Const {


    public static final String TOKEN_PREFIX = "token";

    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL        = "email";
    public static final String USERNAME     = "username";
    public static final String USERID       = "userId";

    /**
     * 字符编码
     */
    public static final String encoding = "utf-8";


    public interface UserActive {
        int NORMAL = 1;//正常
        int BAN    = 2;//已屏蔽
    }

    /**
     * 新闻发布状态：1 已发布，2 被退回；
     */
    public interface NewsStatus {
        int NORMAL = 1;
        int BAN    = 2;
    }

    /**
     * 新闻置顶状态：1 置顶，2 不置顶
     */
    public interface NewsIsHead {
        int IS_HEAD  = 1;
        int NOT_HEAD = 2;
    }

    /**
     * 新闻类别是否可用：1 正常，2 隐藏；
     */
    public interface NewsCategoryActive {
        int NORMAL = 1;
        int BAN    = 2;
    }


    /**
     * 空气污染等级枚举
     */
    public enum AirQuality {
        EXCELLENT(0, 50, "优"),
        GOOD(51, 100, "良"),
        MILD_POLLUTION(101, 150, "轻度污染"),
        MODERATELY_POLLUTION(151, 200, "中度污染"),
        SEVERE_POLLUTION(201, 300, "重度污染"),
        SERIOUS_POLLUTION(301, Integer.MAX_VALUE, "严重污染"),;

        private int    minValue;
        private int    maxValue;
        private String airDesc;


        AirQuality(int minValue, int maxValue, String airDesc) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.airDesc = airDesc;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public String getAirDesc() {
            return airDesc;
        }
    }

    /**
     * 用户类型枚举
     */
    public enum UserType {
        SYSTEM_ADMIN(101, "系统管理员"),
        ENTERPRISE_CUSTOMER(201, "企业用户"),
        PROVINCIAL_ADMIN(301, "省级管理用户"),
        PREFECTURE_ADMIN(401, "地级管理用户"),
        COUNTY_ADMIN(501, "县级管理用户");
        private String type;
        private int    code;

        UserType(int code, String type) {
            this.code = code;
            this.type = type;
        }

        public static UserType codeOf(int code) {
            for (UserType userType : values()) {
                if (userType.getCode() == code) {
                    return userType;
                }
            }
            return null;
        }

        public static List<Map> getAllUserType() {
            List<Map> mapList = Lists.newArrayList();
            Map map;
            for (UserType userType : values()) {
                map = Maps.newHashMap();
                map.put("code", userType.getCode());
                map.put("type", userType.getType());
                mapList.add(map);
            }
            return mapList;
        }

        public String getType() {
            return type;
        }

        public int getCode() {
            return code;
        }

    }

    /**
     * 0 成功
     * 1 错误
     * 10 未知错误
     * 20 需要登录
     * 30 权限不足
     * 40 参数错误
     */
    public enum ResponseCode {
        SUCCESS(0, "SUCCESS"),
        ERROR(1, "ERROR"),
        UNKNOWN_ERROR(10, "UNKNOWN_ERROR"),
        NEED_LOGIN(20, "NEED_LOGIN"),
        PERMISSION_DENIED(30, "PERMISSION_DENIED"),
        ILLEGAL_ARGUMENT(40, "ILLEGAL_ARGUMENT");

        private final int    code;
        private final String desc;

        ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 企业污染类型：1 废水，2 废气；
     */
    public enum CompanyType {
        WASTEWATER(1, "废水"),
        EXHAUSTGAS(2, "废气");

        private final int code;
        private final String desc;

        public static CompanyType codeOf(int code){
            for(CompanyType companyType : values()){
                if(companyType.getCode() == code){
                    return companyType;
                }
            }
            return null;
        }

        CompanyType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 达标状态:0 达标 1 不达标
     */
    public interface StandardStatus {
        int STANDARD =0;
        int NOT_STANDARD = 1;
    }



}
