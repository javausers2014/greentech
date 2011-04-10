package com.gtech.iarc.ipallet.web.dwrproxy;

import java.util.ArrayList;
import java.util.List;

import com.gtech.iarc.base.model.personalinfo.Personnel;
import com.gtech.iarc.base.service.userbio.PersonnelBioService;
import com.gtech.iarc.ipallet.web.dto.StaffInfo;

public class RemoteStaffService {
	
	private PersonnelBioService personnelBioService;
	
	public void setPersonnelBioService(PersonnelBioService personnelBioService) {
		this.personnelBioService = personnelBioService;
	}



	public List<StaffInfo> getAllStaff(){
		List<Personnel> tmp = personnelBioService.getAllStaff();
		List<StaffInfo> rs = new ArrayList<StaffInfo>();
		for(Personnel pl : tmp){
			rs.add(new StaffInfo(pl));
		}
		return rs;
	}
	
	
}
