package com.exam.service;

import com.exam.entity.Admin;
import com.exam.entity.Login;
import com.exam.entity.User;
import com.exam.entity.Teacher;

public interface LoginService {
    User userLogin(Login login);
}
