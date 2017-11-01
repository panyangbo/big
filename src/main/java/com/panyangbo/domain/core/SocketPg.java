package com.panyangbo.domain.core;


import java.io.Serializable;

/**
 * 心跳消息
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/6 14:40
 * @Description HeartBeat
 * @version 4.0.0
 */
public class SocketPg implements Serializable{

    private static final long serialVersionUID = -8061265547433847971L;

    public Header header;

    public Object body;

    public SocketPg() {
    }

    public SocketPg(String serverID, HeadType type, String SessionID, Object body) {
        this.header = new Header(serverID,type,SessionID);
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SocketPg{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
