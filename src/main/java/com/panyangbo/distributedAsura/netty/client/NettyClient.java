package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.distributedAsura.netty.NettyConfigure;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * 客户端接口定义
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:07
 * @Description NettyClient
 * @version 4.0.0
 */

public interface NettyClient extends NettyConfigure {
    /**
     * 建立新的连接
     * @param host
     * @param port
     * @return
     * @throws Exception
     */
    public Channel connect(String host, int port) throws Exception;
    /**
     *
     * @param socketAddress
     * @return
     * @throws Exception
     */
    public Channel connect(SocketAddress socketAddress) throws Exception;

    /**
     * 获取Netty {@link Bootstrap}
     * @return
     */
    public Bootstrap getBootstrap();
}
