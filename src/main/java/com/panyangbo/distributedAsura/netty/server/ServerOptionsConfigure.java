package com.panyangbo.distributedAsura.netty.server;


import io.netty.bootstrap.ServerBootstrap;

/**
 * 提供一个便捷的方式调整网络配置
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 10:54
 * @Description ServerOptionsConfigure
 * @version 4.0.0
 */
public interface ServerOptionsConfigure {
    /**
     * 修改网络配置
     * @param bootstrap
     */
    public void options(ServerBootstrap bootstrap);
}
