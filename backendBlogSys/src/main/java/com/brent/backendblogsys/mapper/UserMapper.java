package com.brent.backendblogsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brent.backendblogsys.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 为了spring security验证密码
    @Select("select * from user where email = #{email}")
    User selectUserForAuth(@Param("email") String email);
}
