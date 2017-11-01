package com.panyangbo.distributedAsura.netty;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/29
 */


import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;

public abstract class ChannelHandlerFactoryAdapter implements
        ChannelHandlerFactory {
    protected int maxDataLength = 1024*1024;
    protected boolean blockingRead = false;
    protected int timeout = Integer.MAX_VALUE;

    public abstract ChannelInboundHandler newInstance();

    /**编码器
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/6 16:46
     * @Description ChannelHandlerFactoryAdapter
     * @version 4.0.0
     */
    public ObjectEncoder newObjectEncoder() {
        return new ObjectEncoder();
    }


    /**设置包大小
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/6 11:17
     * @Description ChannelHandlerFactoryAdapter
     * @version 4.0.0
     */
    public void setMaxDataLength(int maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public boolean isBlockingRead() {
        return blockingRead;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setBlockingRead(boolean blockingRead) {
        this.blockingRead = blockingRead;
    }


}
