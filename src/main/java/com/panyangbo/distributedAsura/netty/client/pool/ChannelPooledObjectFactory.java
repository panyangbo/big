package com.panyangbo.distributedAsura.netty.client.pool;


import com.panyangbo.distributedAsura.netty.client.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:16
 * @Description ChannelPooledObjectFactory
 * @version 4.0.0
 */
public class ChannelPooledObjectFactory implements PooledObjectFactory<Channel> {
    private final Log log = LogFactory.getLog(getClass());
    private final NettyClient nettyClient;
    private final String host;
    private final int port;

    public ChannelPooledObjectFactory(NettyClient nettyClient, String host, int port) {
        this.nettyClient = nettyClient;
        this.host = host;
        this.port = port;
    }

    public PooledObject<Channel> makeObject() throws Exception {
        Channel channel = nettyClient.connect(host, port);
        return new DefaultPooledObject<Channel>(channel);
    }

    public void destroyObject(PooledObject<Channel> p) throws Exception {
        p.getObject().close().addListener(ChannelFutureListener.CLOSE);
    }

    public boolean validateObject(PooledObject<Channel> p) {
        boolean active = p.getObject().isActive();
        log.debug( "channel is active" + active);
        return active;
    }

    public void activateObject(PooledObject<Channel> p) throws Exception {
        // do nothing
    }

    public void passivateObject(PooledObject<Channel> p) throws Exception {
        // do nothing
    }

}