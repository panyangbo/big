package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.distributedAsura.netty.ChannelHandlerFactoryAdapter;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

import java.util.List;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/29 16:01
 * @Description ServerHandlerFactory
 * @version 4.0.0
 */


public class ServerHandlerFactory extends ChannelHandlerFactoryAdapter {

    private String serverid;

    private List<String> whiteList;

    private ConnetStateUtil connetStateUtil;
    public ServerHandlerFactory() {
    }

    public ServerHandlerFactory(String serverid, List<String> whiteList, ConnetStateUtil connetStateUtil) {
        this.serverid = serverid;
        this.whiteList = whiteList;
        this.connetStateUtil = connetStateUtil;
    }

    public ServerHandlerFactory(String serverid, List<String> whiteList) {
        this.serverid = serverid;
        this.whiteList = whiteList;
        this.connetStateUtil = new ConnetStateUtil();
    }

    public ChannelHandlerAdapter newHeartBeatHandler() {
        return new HeartBeatRespHandler(serverid);
    }

    public ObjectDecoder newObjectDecoder() {
        return new ObjectDecoder(this.maxDataLength, ClassResolvers
                .weakCachingConcurrentResolver(this
                        .getClass()
                        .getClassLoader()));
    }

    public ChannelHandlerAdapter newLoginAuthHandler() {
        return new LoginAuthRespHandler(whiteList,serverid,connetStateUtil);
    }

    @Override
    public ChannelInboundHandler newInstance() {
        return new IOServerHandler(this);
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
