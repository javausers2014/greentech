package com.gtech.iarc.ipallet.web.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.gtech.iarc.ipallet.model.tp.TradingParterner;
import com.gtech.iarc.ipallet.service.tp.TPService;
import com.innovations.webtop.web.rest.RestfulResource;

public class OwnerProductRestService extends RestfulResource {

	public Representation getOwnerProducts() {
		return get();
	}
	
	@Override
	public Representation get() {
		Representation entity = getRequestEntity();
		String content = IOUtils.toString(entity.getStream());
		
		String ownerCode = (String)JSONObject.fromObject(content).get("data");
		
		
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("total", tmp.size());
		response.put("data", tmp);
		String jasonString = JSONObject.fromObject(response).toString();
		
		return new StringRepresentation(jasonString);
	}
	
	private 
}

