package com.panyangbo.distributedAsura.netty.client.pool;

import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 连接工厂的定义
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:15
 * @Description ConnectionPoolFactory
 * @version 4.0.0
 */
public interface  ConnectionPoolFactory {
    /**
     * 根据默认的配置创建连接往 host 和 port 的连接池
     * @param host
     * @param port
     * @return
     */
    public GenericObjectPool<Channel> buildPool(String host, int port);
    /**
     * 使用指定的配置创建连接池
     * @param host
     * @param port
     * @param config
     * @return
     */
    public GenericObjectPool<Channel> buildPool(String host, int port, GenericObjectPoolConfig config);
}
