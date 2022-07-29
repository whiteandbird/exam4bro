package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.User;

public interface StudentService {

    IPage<User> findAll(Page<User> page);

    User findById(Integer studentId);

    int deleteById(Integer studentId);

    int update(User student);

    int updatePwd(User student);
    int add(User student);
}
