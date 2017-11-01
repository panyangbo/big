package com.panyangbo.distributedAsura.netty.client.pool;

import com.panyangbo.distributedAsura.netty.client.NettyClient;
import io.netty.channel.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 *
 * 默认的连接工厂实现
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:19
 * @Description DefaultConnectionPoolFactory
 * @version 4.0.0
 */

public class DefaultConnectionPoolFactory implements ConnectionPoolFactory {
    private final Log log = LogFactory.getLog(getClass());
    private final NettyClient nettyClient;
    private GenericObjectPoolConfig defaultPoolConfig = new GenericObjectPoolConfig();

    public DefaultConnectionPoolFactory(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    public GenericObjectPool<Channel> buildPool(String host, int port) {
        return buildPool(host, port, null);
    }

    public GenericObjectPool<Channel> buildPool(String host, int port, GenericObjectPoolConfig config) {
        if (config == null) {
            config = defaultPoolConfig;
        }
        GenericObjectPool<Channel> pool = new GenericObjectPool<Channel>(new ChannelPooledObjectFactory(nettyClient, host, port), config);
        log.info(String.format("Create connection pool with properties -> [Host=%1$s,Port=%2$d]", host, port));
        return pool;
    }
}
