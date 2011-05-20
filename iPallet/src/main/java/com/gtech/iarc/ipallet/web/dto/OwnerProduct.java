package com.gtech.iarc.ipallet.web.dto;

import com.gtech.iarc.base.model.core.InfoTransferObject;

public class OwnerProduct implements InfoTransferObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8110167619400630285L;
	private String ownerfullname;
 
	private String product;
	private String uom;
	private int qty;
	private int safeqty;
	private int alarmqty;
	private int warnqty;
	private int hlqty;
	private boolean warning;
	private boolean highlight;
	private boolean alarm;
	private int threshold;
	private int tolerance;
	
	public String getOwnerfullname() {
		return ownerfullname;
	}
	public void setOwnerfullname(String ownerfullname) {
		this.ownerfullname = ownerfullname;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getSafeqty() {
		return safeqty;
	}
	public void setSafeqty(int safeqty) {
		this.safeqty = safeqty;
	}
	public int getAlarmqty() {
		return alarmqty;
	}
	public void setAlarmqty(int alarmqty) {
		this.alarmqty = alarmqty;
	}
	public int getWarnqty() {
		return warnqty;
	}
	public void setWarnqty(int warnqty) {
		this.warnqty = warnqty;
	}
	public int getHlqty() {
		return hlqty;
	}
	public void setHlqty(int hlqty) {
		this.hlqty = hlqty;
	}
	public boolean isWarning() {
		return warning;
	}
	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public boolean isAlarm() {
		return alarm;
	}
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getTolerance() {
		return tolerance;
	}
	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}
	
	
}
