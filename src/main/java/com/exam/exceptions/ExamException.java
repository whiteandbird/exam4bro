package com.exam.exceptions;

import lombok.Data;

/**
 * @author wangdy
 * @date 2022/4/29 22:09
 */
@Data
public class ExamException extends RuntimeException{
    private String msg;

    public ExamException(String msg){
        super(msg);
        this.msg = msg;
    }

    public ExamException(){}
}
