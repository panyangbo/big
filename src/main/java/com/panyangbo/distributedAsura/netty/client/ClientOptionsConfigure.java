package com.panyangbo.distributedAsura.netty.client;

import io.netty.bootstrap.Bootstrap;

/**
 * 提供一个便捷的方式调整网络配置
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:09
 * @Description ClientOptionsConfigure
 * @version 4.0.0
 */

public interface ClientOptionsConfigure {
    public void options(Bootstrap bootstrap);
}
