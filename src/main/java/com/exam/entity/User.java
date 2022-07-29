package com.exam.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String userName;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String sex;

    private Integer role;
}