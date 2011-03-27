package com.gtech.iarc.demo.web;

import java.util.List;

import com.gtech.iarc.base.models.personalinfo.Personnel;
import com.gtech.iarc.base.service.userbio.PersonnelBioService;

public class PersonnelBioServiceProxy {	
	
	private PersonnelBioService personnelBioService;
	
	
	public void setPersonnelBioService(PersonnelBioService personnelBioService) {
		this.personnelBioService = personnelBioService;
	}

	public List<Personnel> getPersonnels() {
		
		return personnelBioService.searchByName(null);		
	}
	
	public List<Personnel> createPersonnel(List<Personnel> ps) {
		personnelBioService.addAllPersonnel(ps);		
		return this.getPersonnels();	
	}
	
	public List<Personnel> deletePersonnel(List<Personnel> ps) {
		personnelBioService.deleteAllPersonnel(ps);
		
		return this.getPersonnels();
	}
	
	public List<Personnel> updatePersonnel(List<Personnel> ps) {
		personnelBioService.updateAllPersonnel(ps);
		
		return this.getPersonnels();
	}
}
