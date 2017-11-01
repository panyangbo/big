package com.panyangbo.distributedAsura.netty;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**.
 * {@link ChannelInitializer} 的默认实现
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:05
 * @Description DefaultChannelInitializer
 * @version 4.0.0
 */

public class DefaultChannelInitializer extends
        AbstractChannelInitializer {
    private ChannelInboundHandler ioHandler;


    public DefaultChannelInitializer(ChannelHandlerFactory handlerFactory) {
        super(handlerFactory);
    }

    @Override
    public void doInitChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new ObjectEncoder());
        if (useSingleton) {
            if (ioHandler == null) {
                ioHandler = handlerFactory.newInstance();
            }
            p.addLast(ioHandler);
        }else{
            p.addLast(handlerFactory.newInstance());
        }
    }



}