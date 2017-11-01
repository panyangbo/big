package com.panyangbo.distributedAsura.netty;


import com.panyangbo.distributedAsura.netty.blocking.BlockingRead;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 业务处理handler工厂接口
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/29 15:58
 * @Description ChannelHandlerFactory
 * @version 4.0.0
 */
public interface  ChannelHandlerFactory {
    /**
     * 指示当前的 {@link ChannelInboundHandler} 是否开启阻塞读取模式
     * @see {@link BlockingRead#read()}
     * @return
     */
    public boolean isBlockingRead();
    /**
     * 阻塞读取的超时时间，单位毫秒
     * @return
     */
    public int getTimeout();
    /**
     * 创建新业务句柄实例
     * @return
     */
    public ChannelInboundHandler newInstance();
    /**
     * 创建解码器句柄
     * <pre>
     * 继承 {@link ByteToMessageDecoder} 的解码器不允许共享，所以在 {@link ChannelPipeline} 初始化时不是单例模式。
     * </pre>
     * @return
     */

    /**创建编码器句柄
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/6 10:50
     * @Description ChannelHandlerFactory
     * @version 4.0.0
     */
    public ObjectEncoder newObjectEncoder();

    /**创建解码器句柄
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/6 10:51
     * @Description ChannelHandlerFactory
     * @version 4.0.0
     */
    public ObjectDecoder newObjectDecoder();

    /**
     * 心跳处理句柄
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/6 16:49
     * @Description ChannelHandlerFactoryAdapter
     * @version 4.0.0
     */
    public ChannelHandlerAdapter newHeartBeatHandler();

    /**
     * 登陆认证句柄
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/7 9:47
     * @Description ChannelHandlerFactory
     * @version 4.0.0
     */
    public ChannelHandlerAdapter newLoginAuthHandler();
}
