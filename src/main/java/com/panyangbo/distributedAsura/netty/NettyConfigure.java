package com.panyangbo.distributedAsura.netty;

import io.netty.channel.ChannelInitializer;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 10:53
 * @Description NettyConfigure
 * @version 4.0.0
 */
public interface NettyConfigure {
    /**
     * 用于配置 {@link ChannelInitializer}
     */
    public void configure();
    /**
     * 启动服务
     * @throws Exception
     */
    public void init() throws Exception;
    /**
     * 关闭服务
     */
    public void destroy();
}
