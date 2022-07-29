package com.exam.exceptions;

import lombok.Data;

/**
 * @author wangdy
 * @date 2022/4/29 23:13
 */
@Data
public class AuthException extends RuntimeException{
    private String msg;

    public AuthException(String msg){
        super(msg);
        this.msg = msg;
    }

    public AuthException(){

    }
}
