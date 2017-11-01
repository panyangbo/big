package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.distributedAsura.netty.EventLoopGroupFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 客户端抽象类
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/7/4 11:09
 * @Description AbstractClientConfigure
 * @version 4.0.0
 */
public abstract class AbstractClientConfigure implements NettyClient {
    private final Log log = LogFactory.getLog(getClass());
    protected Bootstrap bootstrap;
    protected NioEventLoopGroup bossGroup;
    protected ChannelInitializer<? extends Channel> channelInitializer;

    private boolean bossGroupSetted = false;
    private AtomicBoolean isStarted = new AtomicBoolean(false);
    private AtomicBoolean isShutdown = new AtomicBoolean(false);
    private int bossGroupSize = 1;

    /**
     * 配置Client options
     * @param bootstrap
     */
    protected abstract void options(Bootstrap bootstrap);

    /**初始化
     * @author 潘洋波[panybest1991@gmail.com]
     * @Date 2017/7/5 11:10
     * @Description AbstractClientConfigure
     * @version 4.0.0
     */
    public void init() throws Exception {
        if (isStarted.get()) {
            throw new IllegalStateException("Netty distributedAsura bootstrap already started!");
        }
        //load channelInitializer
        configure();
        if (channelInitializer == null) {
            throw new IllegalStateException("ChannelInitializer is null, unset in configure() ?");
        }
        if (!bossGroupSetted) {
            bossGroup = EventLoopGroupFactory.newNioLoopGroup(bossGroupSize);
        }
        bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);
        //config option
        options(bootstrap);
        isStarted.set(true);
        isShutdown.set(false);
        log.info(String.format("Netty distributedAsura started with properties -> [BossGroupSize=%1$d]", bossGroup.executorCount()));
    }

    public Channel connect(String host, int port) throws Exception {
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        return connect(socketAddress);
    }

    public Channel connect(SocketAddress socketAddress) throws Exception {
        if (!isStarted.get()) {
            throw new IllegalStateException("Netty distributedAsura bootstrap is not start!");
        }
        ChannelFuture channelFuture = bootstrap.connect(socketAddress);
        final CountDownLatch downLatch = new CountDownLatch(1);
        channelFuture.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture future)
                    throws Exception {
                downLatch.countDown();
            }});
        downLatch.await();
        if (!channelFuture.isSuccess()) {
            throw new RuntimeException("Connect to " + socketAddress.toString() + " fail");
        }
        return channelFuture.channel();
    }



    public void destroy() {
        if (isShutdown.get()) {
            return;
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        isStarted.set(false);
        isShutdown.set(true);
        bossGroupSetted = false;
        log.info("Netty distributedAsura bootstrap is going shutdown!");
    }

    public NioEventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(NioEventLoopGroup bossGroup) {
        if (bossGroup != null) {
            bossGroupSetted = true;
            this.bossGroup = bossGroup;
        }
    }

    public void setBossGroupSize(int bossGroupSize) {
        this.bossGroupSize = bossGroupSize;
    }

    protected void setChannelInitializer(
            ChannelInitializer<? extends Channel> channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    public boolean getIsStarted() {
        return isStarted.get();
    }

    public boolean getIsShutdown() {
        return isShutdown.get();
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

}
