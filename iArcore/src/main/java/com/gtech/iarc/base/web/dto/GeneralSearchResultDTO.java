package com.gtech.iarc.base.web.dto;

import java.io.Serializable;

public abstract class GeneralSearchResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096557575783446303L;

	protected int firstPosition;

	protected int maxResult;

	protected boolean pagingNeed;

	protected int totalResultSize;
	
	
	public int getTotalResultSize() {
		return totalResultSize;
	}

	public void setTotalResultSize(int totalResultSize) {
		this.totalResultSize = totalResultSize;
	}

	public int getFirstPosition() {
		return firstPosition;
	}

	public void setFirstPosition(int firstPosition) {
		this.firstPosition = firstPosition;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public boolean isPagingNeed() {
		return pagingNeed;
	}

	public void setPagingNeed(boolean pagingNeed) {
		this.pagingNeed = pagingNeed;
	}
	
	

}
