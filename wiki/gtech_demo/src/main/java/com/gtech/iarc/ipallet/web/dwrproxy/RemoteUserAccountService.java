package com.gtech.iarc.ipallet.web.dwrproxy;

import java.util.ArrayList;
import java.util.List;

import com.gtech.iarc.base.model.user.UserAccount;
import com.gtech.iarc.base.service.userbio.UserAccountService;
import com.gtech.iarc.ipallet.web.dwrproxy.dto.UserInfo;

public class RemoteUserAccountService {
	
	private UserAccountService userAccountService;

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	
	public List<UserInfo> getUsers(){
		List<UserAccount> tmp = userAccountService.getAllUsers();
		List<UserInfo> rs = new ArrayList<UserInfo>();
		for(UserAccount ua : tmp){
			rs.add(new UserInfo(ua));
		}
		return rs;
	}
	
	
}
