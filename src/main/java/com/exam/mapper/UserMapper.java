package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 分页查询所有学生
     * @param page
     * @return List<Student>
     */
    @Select("select * from user")
    IPage<User> findAll(Page page);

    @Select("select * from user where userId = #{userId}")
    User findById(Integer studentId);

    @Delete("delete from user where userId = #{studentId}")
    int deleteById(Integer studentId);

    /**
     *更新所有学生信息
     * @param student 传递一个对象
     * @return 受影响的记录条数
     */
    @Update("update user set userName = #{userName}," +
            "tel = #{tel},email = #{email},pwd = #{pwd},cardId = #{cardId},sex = #{sex},role = #{role} " +
            "where userId = #{userId}")
    int update(User student);

    /**
     * 更新密码
     * @param student
     * @return 受影响的记录条数
     */
    @Update("update user set pwd = #{pwd} where userId = #{userId}")
    int updatePwd(User student);


    @Options(useGeneratedKeys = true,keyProperty = "userId")
    @Insert("insert into user(userName,tel,email,pwd,cardId,sex,role) values " +
            "(#{userName},#{tel},#{email},#{pwd},#{cardId},#{sex},#{role})")
    int add(User student);
}
