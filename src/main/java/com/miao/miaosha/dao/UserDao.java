package com.miao.miaosha.dao;

import com.miao.miaosha.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("select * from miaosha_user where id=#{id}")
    User getUserById(@Param("id") int id);

    @Insert("insert into miaosha_user(id,password) values(#{id},#{password})")
    int insert(User user);
}
