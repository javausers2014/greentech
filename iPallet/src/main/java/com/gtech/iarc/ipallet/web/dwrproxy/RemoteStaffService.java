package com.gtech.iarc.ipallet.web.dwrproxy;

import java.util.ArrayList;
import java.util.List;

import com.gtech.iarc.base.model.personalinfo.Personnel;
import com.gtech.iarc.base.model.personalinfo.PersonnelSearchDTO;
import com.gtech.iarc.base.service.userbio.PersonnelBioService;
import com.gtech.iarc.base.web.dwr.DWRPagingReaderResponse;
import com.gtech.iarc.ipallet.web.dto.StaffInfo;

public class RemoteStaffService {
	
	private PersonnelBioService personnelBioService;
	
	public void setPersonnelBioService(PersonnelBioService personnelBioService) {
		this.personnelBioService = personnelBioService;
	}



	public DWRPagingReaderResponse<StaffInfo> getAllStaff(String staffNo, String nameLike,String emailLike,int startAt,
			int maxResult) {
		PersonnelSearchDTO inSearch = new PersonnelSearchDTO();
		inSearch.setPagingNeed(true);
		inSearch.setStaffNo(staffNo);
		inSearch.setFullName(nameLike);
		inSearch.setEmail(emailLike);
		inSearch.setFirstPosition(startAt);
		inSearch.setMaxResult(maxResult);

		List<Personnel> tmp = personnelBioService.searchStaff(inSearch);
		List<StaffInfo> rs = new ArrayList<StaffInfo>();
		for (Personnel pl : tmp) {
			rs.add(new StaffInfo(pl));
		}

		DWRPagingReaderResponse<StaffInfo> dwrRs = new DWRPagingReaderResponse(
				rs, inSearch.getTotalResultSize());
		return dwrRs;
	}	
	
}
