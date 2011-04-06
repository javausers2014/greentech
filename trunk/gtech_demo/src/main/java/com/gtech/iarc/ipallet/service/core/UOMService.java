package com.gtech.iarc.ipallet.service.core;

import java.util.ArrayList;
import java.util.List;

import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.persistence.exception.DuplicatedDomainDataException;
import com.gtech.iarc.ipallet.model.core.CoreUOM;
import com.gtech.iarc.ipallet.model.core.BizConstantCode;
@SuppressWarnings("unchecked")
public class UOMService {
	private BaseDAO baseDAO;
	
	public List<String> getUOMCategoryList(){
		return new ArrayList<String>(){
			private static final long serialVersionUID = 3703282933339442501L;
				{
					add(BizConstantCode.UOM_CATEGORY_LENGTH);
					add(BizConstantCode.UOM_CATEGORY_PACKAGE);
					add(BizConstantCode.UOM_CATEGORY_VOLUMN);
					add(BizConstantCode.UOM_CATEGORY_WEIGHT);
				}			
		};
	}
	
	public BizConstantCode createNewUOM(BizConstantCode newUOM){
		baseDAO.save(newUOM);
		return newUOM;
	}
	
	public List<CoreUOM> getUOMList(){
		List<CoreUOM> rs = baseDAO.find("from CoreUOM");
		return rs;
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

	public boolean updateUOM(CoreUOM coreUOM) {
		baseDAO.save(coreUOM);
		return true;
	}

	public boolean deleteUOM(long uomId) {
		baseDAO.delete(CoreUOM.class, uomId);
		return true;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	
}
