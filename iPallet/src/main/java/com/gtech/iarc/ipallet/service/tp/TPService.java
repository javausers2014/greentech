package com.gtech.iarc.ipallet.service.tp;

import java.util.List;

import com.gtech.iarc.ipallet.model.tp.TradingParterner;
import com.gtech.iarc.ipallet.service.base.BaseService;

public class TPService extends BaseService {

	public List<TradingParterner> getTPByType(String tpType) {
		List<TradingParterner> rs = baseRepository.find(
				"from TradingParterner where type=?", new String[] { tpType });
		return rs;
	}
}
