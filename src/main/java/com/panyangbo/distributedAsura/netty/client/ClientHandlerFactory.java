package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.distributedAsura.netty.ChannelHandlerFactoryAdapter;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/30
 */

public class ClientHandlerFactory extends ChannelHandlerFactoryAdapter {

    private String serverid;
    private String serverType;

    public ClientHandlerFactory(String serverid, String serverType) {
        this.serverid = serverid;
        this.serverType = serverType;
    }

    public ChannelHandlerAdapter newHeartBeatHandler() {
        return new HeartBeatReqHandler(serverid);
    }

    public ChannelHandlerAdapter newLoginAuthHandler() {
        return new LoginAuthReqHandler(serverid,serverType);
    }

    public ObjectDecoder newObjectDecoder() {
        return new ObjectDecoder(this.maxDataLength, ClassResolvers
                .cacheDisabled(this.getClass()
                        .getClassLoader()));
    }

    @Override
    public ChannelInboundHandler newInstance() {
        return new IOClientHandler(this);
    }

}