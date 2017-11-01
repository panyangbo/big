package com.panyangbo.domain.core;

import java.io.Serializable;
import java.util.ResourceBundle;


/**
 * 报文头
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/6 14:57
 * @Description Header
 * @version 4.0.0
 */
public class Header implements Serializable {

    private static final long serialVersionUID = 2040363303233170209L;

    public final int bigCode;

    public final int asuraCode;

    public String serverId;

    public HeadType type;

    public String sessionid;


    public Header(String serverId,HeadType type,String sessionid) {
        ResourceBundle bundle = ResourceBundle.getBundle("asura");
        this.bigCode = Integer.parseInt(bundle.getString("big.code"));
        this.asuraCode = Integer.parseInt(bundle.getString("asura.Code"));
        this.serverId = serverId;
        this.type = type;
        this.sessionid = sessionid;
    }

    public int getBigCode() {
        return bigCode;
    }

    public int getAsuraCode() {
        return asuraCode;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public HeadType getType() {
        return type;
    }

    public void setType(HeadType type) {
        this.type = type;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String toString() {
        return "Header{" +
                "bigCode=" + bigCode +
                ", asuraCode=" + asuraCode +
                ", serverId='" + serverId + '\'' +
                ", type='" + type + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }
}
