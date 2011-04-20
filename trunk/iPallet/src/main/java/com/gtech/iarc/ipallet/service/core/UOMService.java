package com.gtech.iarc.ipallet.service.core;

import java.util.ArrayList;
import java.util.List;

import com.gtech.iarc.base.persistence.BaseDAO;
import com.gtech.iarc.base.persistence.BaseRepository;
import com.gtech.iarc.base.persistence.exception.DuplicatedDomainDataException;
import com.gtech.iarc.ipallet.model.core.CoreUOM;
import com.gtech.iarc.ipallet.model.core.BizConstantCode;

@SuppressWarnings("unchecked")
public class UOMService {
	private BaseRepository baseRepository;
	
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
	
	public BizConstantCode createNewUOM(CoreUOM newUOM){
		baseRepository.save(newUOM);
		return newUOM;
	}
	
	public List<CoreUOM> getUOMList(){
		List<CoreUOM> rs = baseRepository.find("from CoreUOM");
		return rs;
	}
	
	public List<CoreUOM> getUOMbyCategory(String UOMCategory){
		List<CoreUOM> rs = baseRepository.find("from CoreUom where uomCategory=?", new String[]{UOMCategory});
		return rs;
	}
	
	public CoreUOM getUOM(String uomCode){
		List<CoreUOM> rs = baseRepository.find("from CoreUom where uomCode=?", new String[]{uomCode});
		if(rs.size()>1){
			throw new DuplicatedDomainDataException("Duplicated UOM with code: "+uomCode);
		}
		
		return rs.get(0);
	}

	public boolean updateUOM(CoreUOM coreUOM) {
		baseRepository.save(coreUOM);
		return true;
	}

	public boolean deleteUOM(long uomId) {
		baseRepository.delete(CoreUOM.class, uomId);
		return true;
	}

	public void setBaseRepository(BaseRepository baseRepository) {
		this.baseRepository = baseRepository;
	}
	
}
