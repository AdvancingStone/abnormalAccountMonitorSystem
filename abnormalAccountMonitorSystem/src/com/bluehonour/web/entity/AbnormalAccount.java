package com.bluehonour.web.entity;

public class AbnormalAccount {
    private String time;
    private String userAccount;
    private String userIP;
    private String qqid;
    private String natIP;
    private String cookieValue;
    private String devName;
    private String osName;
    private Integer num;

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getQqid() {
		return qqid;
	}
	public void setQqid(String qqid) {
		this.qqid = qqid;
	}
	public String getNatIP() {
		return natIP;
	}
	public void setNatIP(String natIP) {
		this.natIP = natIP;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
    
	public AbnormalAccount(String time, Integer num) {
		super();
		this.time = time;
		this.num = num;
	}
	public AbnormalAccount() {
		super();
	}
	@Override
	public String toString() {
		return "AbnormalAccount [time=" + time + ", num=" + num + "]";
	}
	
	
}
