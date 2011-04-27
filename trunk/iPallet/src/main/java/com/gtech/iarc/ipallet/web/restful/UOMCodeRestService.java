package com.gtech.iarc.ipallet.web.restful;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.restlet.representation.Representation;

import com.gtech.iarc.ipallet.model.core.CoreUOM;
import com.gtech.iarc.ipallet.service.core.UOMService;
import com.innovations.webtop.web.rest.RestfulResource;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;

public class UOMCodeRestService extends RestfulResource {
	private UOMService uomService;
	
	public Representation getUOMCodes() {
		return get();
	}
	
	@Override
	public Representation get() {
		List<CoreUOM> uomList = uomService.getUOMList();
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("message", "Data loaded");
		response.put("total", uomList.size());
		response.put("data", uomList);
		
		return new StringRepresentation(JSONObject.fromObject(response).toString());
	}
		
	@Override
	protected Representation put(Representation entity) {
		try {
			String content = IOUtils.toString(entity.getStream());
			JSONObject data = JSONObject.fromObject(content).getJSONObject("data");
			CoreUOM c = (CoreUOM)JSONObject.toBean(data, CoreUOM.class);
			
			boolean success = uomService.updateUOM(c);
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("success", success);
			response.put("data", c);
			
			return new StringRepresentation(JSONObject.fromObject(response).toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StringRepresentation("");		
	}
	
	@Override
	public Representation post(Representation entity) {
		try {			
			String content = IOUtils.toString(entity.getStream());
			JSONObject data = JSONObject.fromObject(content).getJSONObject("data");
			CoreUOM newUOM = (CoreUOM)JSONObject.toBean(data, CoreUOM.class);
			if (newUOM.getUomCategory()==null || newUOM.getUomCategory().trim().length()==0){
				return new StringRepresentation("");
			}
			Map<String, Object> response = new HashMap<String, Object>();
			if (newUOM.getId() == 0) {
				CoreUOM addedUOM = (CoreUOM) uomService.createNewUOM(newUOM);
				response.put("data", addedUOM);
			}
			response.put("success", newUOM.getId() != 0);
			
			return new StringRepresentation(JSONObject.fromObject(response).toString());			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StringRepresentation("");
	}
	
	@Override
	protected Representation delete() throws ResourceException {
		try {
			Representation entity = getRequestEntity();
			String content = IOUtils.toString(entity.getStream());
			boolean success = false;
			
			String uomId = (String)JSONObject.fromObject(content).get("data");
			if(uomId!=null && uomId.trim().length()>0){
				success = uomService.deleteUOM(Long.getLong(uomId).longValue());
			}
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("success", success);
			
			return new StringRepresentation(JSONObject.fromObject(response).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new StringRepresentation("");
	}

	public void setUomService(UOMService uomService) {
		this.uomService = uomService;
	}


}

