package com.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangdy
 * @date 2022/4/28 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContextUser {
    private Integer userId;
    private String userName;
    private Integer type;

}
