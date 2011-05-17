package com.gtech.iarc.ipallet.service.base;

import com.gtech.iarc.base.persistence.BaseRepository;

public abstract class BaseService {
	protected BaseRepository baseRepository;

	public void setBaseRepository(BaseRepository baseRepository) {
		this.baseRepository = baseRepository;
	}
	
}
