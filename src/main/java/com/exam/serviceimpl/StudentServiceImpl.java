package com.exam.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.User;
import com.exam.mapper.UserMapper;
import com.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public IPage<User> findAll(Page<User> page) {
        return userMapper.findAll(page);
    }

    @Override
    public User findById(Integer studentId) {
        return userMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer studentId) {
        return userMapper.deleteById(studentId);
    }

    @Override
    public int update(User student) {
        return userMapper.update(student);
    }

    @Override
    public int updatePwd(User student) {
        return userMapper.updatePwd(student);
    }

    @Override
    public int add(User student) {
        return userMapper.add(student);
    }
}
