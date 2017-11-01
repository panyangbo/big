package com.panyangbo.distributedAsura.netty;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * channelInitializer模板
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 9:49
 * @Description AbstractChannelInitializer
 * @version 4.0.0
 */
public abstract class AbstractChannelInitializer extends ChannelInitializer<SocketChannel> {
    private boolean enableTimeoutHandler = false;
    private boolean enableIdleHandler = true;
    protected int timeout = 90;
    protected int readIdleTime = 10;
    protected int writeIdleTime = 20;
    protected int bothIdleTime = 30;
    protected ChannelHandlerFactory handlerFactory;

    protected boolean useSingleton = true;

    private ObjectEncoder objectEncoder;
    private ChannelHandlerAdapter heartBeatHandler;
    private ChannelHandlerAdapter loginAuthHandler;

    public AbstractChannelInitializer(ChannelHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if(enableTimeoutHandler) p.addLast(new ReadTimeoutHandler(timeout));
        if(enableIdleHandler) p.addLast(new IdleStateHandler(readIdleTime, writeIdleTime,bothIdleTime));
        p.addLast(handlerFactory.newObjectDecoder());
        if (useSingleton) {
            if (objectEncoder == null){
                objectEncoder = handlerFactory.newObjectEncoder();
            }
            if (heartBeatHandler == null){
                heartBeatHandler = handlerFactory.newHeartBeatHandler();
            }
            if (loginAuthHandler == null ){
                loginAuthHandler = handlerFactory.newLoginAuthHandler();
            }
            p.addLast(objectEncoder, loginAuthHandler,heartBeatHandler);
        }else{
            p.addLast(handlerFactory.newObjectEncoder(),handlerFactory.newLoginAuthHandler(),handlerFactory.newHeartBeatHandler());
        }
        doInitChannel(ch);
    }

    /**
     * 在配置业务 Handler 之前进行超时和空闲的 Handler 的注册
     * @param ch
     * @throws Exception
     */
    public abstract void doInitChannel(SocketChannel ch) throws Exception;

    /**
     * 设置超时时间
     * @param timeout 单位秒
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 读超时时间，单位秒，小于等于0的值无效
     * @param readIdleTime
     */
    public void setReadIdleTime(int readIdleTime) {
        this.readIdleTime = readIdleTime;
    }

    /**
     * 写超时时间，单位秒，小于等于0的值无效
     * @param writeIdleTime
     */
    public void setWriteIdleTime(int writeIdleTime) {
        this.writeIdleTime = writeIdleTime;
    }

    /**
     * 共同的超时时间，单位秒，小于等于0的值无效
     * @param bothIdleTime
     */
    public void setBothIdleTime(int bothIdleTime) {
        this.bothIdleTime = bothIdleTime;
    }

    /**
     * 是否启用超时 Handler
     * <pre>注意：设置超时句柄后，超时触发时会关闭连接</pre>
     * @param enableTimeoutHandler
     */
    public void setEnableTimeoutHandler(boolean enableTimeoutHandler) {
        this.enableTimeoutHandler = enableTimeoutHandler;
    }

    /**
     * 是否启用空闲 Handler
     * @param enableIdleHandler
     */
    public void setEnableIdleHandler(boolean enableIdleHandler) {
        this.enableIdleHandler = enableIdleHandler;
    }

    /**
     * 所有句柄是否使用单例模式，单例模式有助与减少GC，但是 DecoderHandler 的默认实现是不支持单例模式的
     * @param useSingleton
     */
    public void setUseSingleton(boolean useSingleton) {
        this.useSingleton = useSingleton;
    }

    public void setHandlerFactory(ChannelHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

}
