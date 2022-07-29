package com.exam.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Login {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Integer type;

}
