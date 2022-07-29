package com.exam.mapper;

import com.exam.entity.Admin;
import com.exam.entity.Login;
import com.exam.entity.User;
import com.exam.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select userId,userName,tel," +
            "email,cardId,sex,role from user where userName = #{username} and pwd = #{password} and role=#{type}")
    public User userLogin(Login login);
}
