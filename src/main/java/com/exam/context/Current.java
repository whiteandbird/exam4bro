package com.exam.context;

import com.exam.entity.ContextUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangdy
 * @date 2022/4/28 15:08
 */
@Slf4j
public class Current {
    private static ThreadLocal<ContextUser> userContext = new ThreadLocal<>();

    public static void setUserContext(ContextUser contextUser){
        userContext.set(contextUser);
    }

    public static ContextUser user(){
        return userContext.get();
    }

    public static void clear(){
        userContext.remove();
    }
}
