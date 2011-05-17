package com.gtech.iarc.ipallet.web.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.gtech.iarc.ipallet.model.tp.TradingParterner;
import com.gtech.iarc.ipallet.service.tp.TPService;
import com.innovations.webtop.web.rest.RestfulResource;

public class OwnerRestService extends RestfulResource {
	private TPService tpService;
	
	public Representation getOwners() {
		return get();
	}
	
	@Override
	public Representation get() {
		List<TradingParterner> tmp = tpService.getTPByType(TradingParterner.TP_TYPE_OWNER);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("total", tmp.size());
		response.put("data", tmp);
		String jasonString = JSONObject.fromObject(response).toString();
		
		return new StringRepresentation(jasonString);
	}

	public void setTpService(TPService tpService) {
		this.tpService = tpService;
	}


}

