package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.distributedAsura.netty.NettyConfigure;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/30
 */

public interface NettyServer extends NettyConfigure {
    /**
     * 监听端口
     * @param port
     * @return 返回与端口映射的 {@link Channel} ，与端口映射
     * @throws Exception
     */
    public Map<Integer, Channel> bind(int... port) throws Exception;

    /**
     * 获取 Netty 服务端 {@link ServerBootstrap}
     * @return
     */
    public ServerBootstrap getBootstrap();
}