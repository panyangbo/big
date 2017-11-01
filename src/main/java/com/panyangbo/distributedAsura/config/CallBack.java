package com.panyangbo.distributedAsura.config;

import io.netty.channel.Channel;

/**
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/28 10:16
 * @Description CallBack
 * @version 4.0.0
 */

public abstract class CallBack<R> {

    public abstract R invoke(Channel channel);
}

