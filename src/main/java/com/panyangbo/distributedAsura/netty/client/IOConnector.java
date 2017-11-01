package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.distributedAsura.netty.AbstractChannelInitializer;
import com.panyangbo.distributedAsura.netty.ChannelHandlerFactory;
import com.panyangbo.distributedAsura.netty.DefaultChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;

/**
 * 默认客户端
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 14:12
 * @Description IOConnector
 * @version 4.0.0
 */

public class IOConnector extends AbstractClientConfigure {
    private ClientOptionsConfigure optionsConfigure;
    private ChannelHandlerFactory handlerFactory;
    private boolean useSingleton = false;
    private boolean enableTimeoutHandler = false;
    private boolean enableIdleHandler = true;
    private int timeout = 90;
    private int readIdleTime = 30;
    private int writeIdleTime = 0;
    private int bothIdleTime = 0;

    public IOConnector() {

    }

    public IOConnector(ChannelHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public void configure() {
        if (handlerFactory == null) {
            throw new NullPointerException("ChannelHandlerFactory must not be null");
        }
        DefaultChannelInitializer clientChannelInitializer = new DefaultChannelInitializer(
                handlerFactory);
        clientChannelInitializer.setUseSingleton(useSingleton);
        clientChannelInitializer.setBothIdleTime(bothIdleTime);
        clientChannelInitializer.setEnableIdleHandler(enableIdleHandler);
        clientChannelInitializer.setEnableTimeoutHandler(enableTimeoutHandler);
        clientChannelInitializer.setReadIdleTime(readIdleTime);
        clientChannelInitializer.setTimeout(timeout);
        clientChannelInitializer.setWriteIdleTime(writeIdleTime);
        setChannelInitializer(clientChannelInitializer);
    }

    @Override
    protected void options(Bootstrap bootstrap) {
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        if (optionsConfigure != null) {
            optionsConfigure.options(bootstrap);
        }
    }

    public void setHandlerFactory(ChannelHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
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

    public void setOptionsConfigure(ClientOptionsConfigure optionsConfigure) {
        this.optionsConfigure = optionsConfigure;
    }

}
