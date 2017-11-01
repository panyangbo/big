package com.panyangbo.distributedAsura.netty;


import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * EventLoopGroupFactory
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 9:55
 * @Description EventLoopGroupFactory
 * @version 4.0.0
 */
public class EventLoopGroupFactory {
    private static final Log log = LogFactory.getLog(EventLoopGroupFactory.class);

    /**
     * 创建 {@link NioEventLoopGroup}
     * @param size
     * @return
     */
    public static NioEventLoopGroup newNioLoopGroup(int size) {
        NioEventLoopGroup group = null;
        if (size <= 0) {
            group = new NioEventLoopGroup();
        } else {
            group = new NioEventLoopGroup(size);
        }
        log.info(String.format("Create new NioEventLoopGroup with %1$d threads", group.executorCount()));
        return group;
    }

    /**
     * 创建 {@link OioEventLoopGroup}
     * @param maxChannel 线程池能处理的最大 {@link Channel} 数量
     * @return
     */
    public static OioEventLoopGroup newOioLoopGroup(int maxChannel) {
        OioEventLoopGroup group = null;
        if (maxChannel <= 0) {
            maxChannel = 1;
            group = new OioEventLoopGroup(maxChannel);
        } else {
            group = new OioEventLoopGroup(maxChannel);
        }
        log.info(String.format("Create new OioEventLoopGroup with %1$d max channels",maxChannel));
        return group;
    }

}
