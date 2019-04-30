package com.cheng.helper.config;

import com.cheng.helper.dto.UserDTO;

public class GlobalInfo {
	
	public UserDTO getUser(){
		return Context.getUser();
	}
	
	public boolean  hasRole(String role){
		return role.equals(Context.getUser().getRole());
	}
	

}
