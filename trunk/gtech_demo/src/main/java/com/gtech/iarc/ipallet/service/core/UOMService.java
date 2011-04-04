package com.gtech.iarc.ipallet.service.core;

import java.util.List;

import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.persistence.exception.DuplicatedDomainDataException;
import com.gtech.iarc.ipallet.model.core.CoreUOM;
import com.gtech.iarc.ipallet.model.core.BizConstantCode;
@SuppressWarnings("unchecked")
public class UOMService {
	private BaseDAO baseDAO;
	
	public BizConstantCode createNewUOM(BizConstantCode newUOM){
		baseDAO.save(newUOM);
		return newUOM;
	}
	
	public List<CoreUOM> getUOMbyCategory(String UOMCategory){
		List<CoreUOM> rs = baseDAO.find("from CoreUom where uomCategory=?", new String[]{UOMCategory});
		return rs;
	}
	
	public CoreUOM getUOM(String uomCode){
		List<CoreUOM> rs = baseDAO.find("from CoreUom where uomCode=?", new String[]{uomCode});
		if(rs.size()>1){
			throw new DuplicatedDomainDataException("Duplicated UOM with code: "+uomCode);
		}
		
		return rs.get(0);
	}
	
	
}
