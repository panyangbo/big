package com.panyangbo.distributedAsura.config;

/**
 * @author panyangbo panyangbo
 * @create 2017/7/7
 */

public class AsuraConfig {

    private String serverid;
    private String serverType;
    private boolean blockedRead;

    public AsuraConfig(String serverid, String serverType) {
        this(serverid,serverType,false);
    }

    public AsuraConfig(String serverid, String serverType, boolean blockedRead) {
        this.serverid = serverid;
        this.serverType = serverType;
        this.blockedRead = blockedRead;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public boolean isBlockedRead() {
        return blockedRead;
    }

    public void setBlockedRead(boolean blockedRead) {
        this.blockedRead = blockedRead;
    }
}
