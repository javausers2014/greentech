package com.gtech.iarc.ipallet.web.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.gtech.iarc.ipallet.service.core.UOMService;
import com.innovations.webtop.web.rest.RestfulResource;

public class UOMCatRestService extends RestfulResource {
	private UOMService uomService;
	
	public Representation getUOMCategories() {
		return get();
	}
	
	@Override
	public Representation get() {
		List<String> uomCatList = uomService.getUOMCategoryList();
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("message", "Data loaded");
		response.put("total", uomCatList.size());
		response.put("data", uomCatList);
		
		return new StringRepresentation(JSONObject.fromObject(response).toString());
	}
	
	public void setUomService(UOMService uomService) {
		this.uomService = uomService;
	}


}

