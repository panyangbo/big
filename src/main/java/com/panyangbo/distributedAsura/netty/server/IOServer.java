package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.distributedAsura.netty.AbstractChannelInitializer;
import com.panyangbo.distributedAsura.netty.ChannelHandlerFactory;
import com.panyangbo.distributedAsura.netty.DefaultChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;

/**
 * 默认服务端实现
 *  <pre>
 * 应用只需要覆盖 {@link IOServer#configure()}，调整网络配置则覆盖 {@link IOServer#options(ServerBootstrap)}，并提供 {@link ChannelHandlerFactory} 的实现类即可。
 * </pre>
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 13:57
 * @Description IOServer
 * @version 4.0.0
 */

public class IOServer extends AbstractServerConfigure {
    private ServerOptionsConfigure optionsConfigure;
    protected AbstractChannelInitializer serverChannelInitializer;
    private boolean useSingleton = false;
    private boolean enableTimeoutHandler = false;
    private boolean enableIdleHandler = true;
    private int timeout = 90;
    private int readIdleTime = 10;
    private int writeIdleTime = 20;
    private int bothIdleTime = 30;

    public IOServer() {

    }

    public IOServer( AbstractChannelInitializer serverChannelInitializer) {
        this.serverChannelInitializer = serverChannelInitializer;
    }

    public void configure() {
        serverChannelInitializer.setUseSingleton(useSingleton);
        serverChannelInitializer.setBothIdleTime(bothIdleTime);
        serverChannelInitializer.setEnableIdleHandler(enableIdleHandler);
        serverChannelInitializer.setEnableTimeoutHandler(enableTimeoutHandler);
        serverChannelInitializer.setReadIdleTime(readIdleTime);
        serverChannelInitializer.setTimeout(timeout);
        serverChannelInitializer.setWriteIdleTime(writeIdleTime);
        setChannelInitializer(serverChannelInitializer);
    }

    @Override
    protected void options(ServerBootstrap bootstrap) {
        // Should set pooled areas [java
        // -Dio.netty.allocator.numDirectArenas=...
        // -Dio.netty.allocator.numHeapArenas=...]
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        if (optionsConfigure != null) {
            optionsConfigure.options(bootstrap);
        }
    }


    /**
     * @see {@link DefaultChannelInitializer#setUseSingleton(boolean)}
     * @param useSingleton
     */
    public void setUseSingleton(boolean useSingleton) {
        this.useSingleton = useSingleton;
    }

    /**
     * @see {@link AbstractChannelInitializer#setEnableTimeoutHandler(boolean)}
     * @param enableTimeoutHandler
     */
    public void setEnableTimeoutHandler(boolean enableTimeoutHandler) {
        this.enableTimeoutHandler = enableTimeoutHandler;
    }

    /**
     * @see {@link AbstractChannelInitializer#setEnableIdleHandler(boolean)}
     * @param enableIdleHandler
     */
    public void setEnableIdleHandler(boolean enableIdleHandler) {
        this.enableIdleHandler = enableIdleHandler;
    }

    /**
     * @see {@link AbstractChannelInitializer#setTimeout(int)}
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @see {@link AbstractChannelInitializer#setReadIdleTime(int)}
     * @param readIdleTime
     */
    public void setReadIdleTime(int readIdleTime) {
        this.readIdleTime = readIdleTime;
    }

    /**
     * @see {@link AbstractChannelInitializer#setWriteIdleTime(int)}
     * @param writeIdleTime
     */
    public void setWriteIdleTime(int writeIdleTime) {
        this.writeIdleTime = writeIdleTime;
    }

    /**
     * @see {@link AbstractChannelInitializer#setBothIdleTime(int)}
     * @param bothIdleTime
     */
    public void setBothIdleTime(int bothIdleTime) {
        this.bothIdleTime = bothIdleTime;
    }

    public void setOptionsConfigure(ServerOptionsConfigure optionsConfigure) {
        this.optionsConfigure = optionsConfigure;
    }

}