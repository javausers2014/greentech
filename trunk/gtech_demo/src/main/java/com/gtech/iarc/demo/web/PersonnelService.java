package com.gtech.iarc.demo.web;

import java.util.List;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.gtech.iarc.demo.models.Personnel;
import com.gtech.iarc.demo.util.PersonnelFactory;

public class PersonnelService {	
	public List<Personnel> getPersonnels() {
		WebContext f = WebContextFactory.get();
		return PersonnelFactory.getPersonnel();		
	}
	
	public List<Personnel> createPersonnel(List<Personnel> ps) {
		PersonnelFactory.addAllPersonnel(ps);
		
		return PersonnelFactory.getPersonnel();
	}
	
	public List<Personnel> deletePersonnel(List<Personnel> ps) {
		PersonnelFactory.deleteAllPersonnel(ps);
		
		return PersonnelFactory.getPersonnel();
	}
	
	public List<Personnel> updatePersonnel(List<Personnel> ps) {
		PersonnelFactory.updateAllPersonnel(ps);
		
		return PersonnelFactory.getPersonnel();
	}
}
