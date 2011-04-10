package com.gtech.iarc.ipallet.web.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.gtech.iarc.ipallet.service.core.UOMService;
import com.gtech.iarc.ipallet.web.dto.UOMCategory;
import com.innovations.webtop.web.rest.RestfulResource;

public class UOMCatRestService extends RestfulResource {
	private UOMService uomService;
	
	public Representation getUOMCategories() {
		return get();
	}
	
	@Override
	public Representation get() {
		List<UOMCategory> tmp = new ArrayList<UOMCategory>();
		
		for(String uomCat : uomService.getUOMCategoryList()){
			UOMCategory uCat = new UOMCategory();
			uCat.setKey(uomCat);
			uCat.setLabel(uomCat);
			tmp.add(uCat);			
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("total", tmp.size());
		response.put("data", tmp);
		String jasonString = JSONObject.fromObject(response).toString();
		
		return new StringRepresentation(jasonString);
	}
	
	public void setUomService(UOMService uomService) {
		this.uomService = uomService;
	}


}

