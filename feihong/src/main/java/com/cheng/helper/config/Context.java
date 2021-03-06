package com.cheng.helper.config;

import com.cheng.helper.dto.UserDTO;

public class Context {

   
    private static ThreadLocal<UserDTO>  USER               = new ThreadLocal<UserDTO>();

    public static void setUser(UserDTO user) {
    	USER.set(user);
    }

    public static UserDTO getUser() {
        return USER.get();
    }
    
    public static void remove(){
    	USER.remove();
    }

   
}
