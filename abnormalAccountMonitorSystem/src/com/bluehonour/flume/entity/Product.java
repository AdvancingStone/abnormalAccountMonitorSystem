package com.bluehonour.flume.entity;

public class Product {
    private String time;    //数据统计时间
    private String userAccount; //宽带账户
    private String userIP;  //用户以太网IP
    private String qqid;    //QQ号
    private String natIP;   //内网ip
    private String cookieValue; //cookie值
    private String devName; //设备名称
    private String osName;  //操作系统名称


    public Product(){

    }

    public Product(String time, String userAccount, String userIP, String qqid, String natIP, String cookieValue, String devName, String osName) {
        this.time = time;
        this.userAccount = userAccount;
        this.userIP = userIP;
        this.qqid = qqid;
        this.natIP = natIP;
        this.cookieValue = cookieValue;
        this.devName = devName;
        this.osName = osName;
    }

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

    @Override
    public String toString() {
        return time+"\t"+userAccount+"\t"+userIP+"\t"+qqid+"\t"+natIP+"\t"+cookieValue+"\t"+devName+"\t"+osName;
    }
}
