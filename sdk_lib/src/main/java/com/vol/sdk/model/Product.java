package com.vol.sdk.model;

public class Product {
	private String portalid;
	private String pid;
	private String name;
	private String parentid;
	private String spid;
	private String usefullife;
	private String mtype;
	private String note;
	private String ptype;
	private String fee;
	private String oemtype;
	private String isshare;
	private String starttime;
	private String stoptime;
	private String costfee;
	private String isorder;
	
	
	public String getPortalid() {
		return portalid;
	}
	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getUsefullife() {
		return usefullife;
	}
	public void setUsefullife(String usefullife) {
		this.usefullife = usefullife;
	}
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getOemtype() {
		return oemtype;
	}
	public void setOemtype(String oemtype) {
		this.oemtype = oemtype;
	}
	public String getIsshare() {
		return isshare;
	}
	public void setIsshare(String isshare) {
		this.isshare = isshare;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getStoptime() {
		return stoptime;
	}
	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	public String getCostfee() {
		return costfee;
	}
	public void setCostfee(String costfee) {
		this.costfee = costfee;
	}
	public String getIsorder() {
		return isorder;
	}
	public void setIsorder(String isorder) {
		this.isorder = isorder;
	}
	@Override
	public String toString() {
		return "Product [portalid=" + portalid + ", pid=" + pid + ", name="
				+ name + ", parentid=" + parentid + ", spid=" + spid
				+ ", usefullife=" + usefullife + ", mtype=" + mtype + ", note="
				+ note + ", ptype=" + ptype + ", fee=" + fee + ", oemtype="
				+ oemtype + ", isshare=" + isshare + ", starttime=" + starttime
				+ ", stoptime=" + stoptime + ", costfee=" + costfee
				+ ", isorder=" + isorder + "]";
	}
	
	
}
