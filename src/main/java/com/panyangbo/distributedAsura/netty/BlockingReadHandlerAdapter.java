package com.panyangbo.distributedAsura.netty;

import com.panyangbo.distributedAsura.netty.blocking.BlockingRead;
import com.panyangbo.distributedAsura.netty.blocking.DefaultBlockingRead;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 阻塞读取
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/29 14:32
 * @Description BlockingReadHandlerAdapter
 * @version 4.0.0
 */
public abstract class BlockingReadHandlerAdapter extends ChannelInboundHandlerAdapter{
    private final Log log = LogFactory.getLog(getClass());
    private ChannelHandlerFactory channelHandlerFactory;

    public BlockingReadHandlerAdapter() {

    }

    public BlockingReadHandlerAdapter(ChannelHandlerFactory channelHandlerFactory) {
        this.channelHandlerFactory = channelHandlerFactory;
//        this.autoRelease = true;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        if (channelHandlerFactory == null) {
            throw new NullPointerException("ChannelHandlerFactory is null, you show set it in your InboundHandler");
        }
        if (channelHandlerFactory.isBlockingRead()) {
            Attribute<BlockingRead> r = ctx.channel().attr(BlockingRead.CHANNEL_BLOCKING_READ_KEY);
            r.set(new DefaultBlockingRead(ctx, channelHandlerFactory.getTimeout()));
            log.warn("Blocking read is enabled, do not use it in server handler.");
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (channelHandlerFactory.isBlockingRead()) {
            Attribute<BlockingRead> r = ctx.channel().attr(BlockingRead.CHANNEL_BLOCKING_READ_KEY);
            r.get().clear();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (channelHandlerFactory.isBlockingRead()) {
            Attribute<BlockingRead> r = ctx.channel().attr(BlockingRead.CHANNEL_BLOCKING_READ_KEY);
            if (r.get() == null) {
                throw new NullPointerException("ChannelHandlerFactory is not allow blocking read or channelRegistered was overrided ?");
            }
            ((DefaultBlockingRead)r.get()).put(msg);
        }
        doRead(ctx, msg);
    }

    protected abstract void doRead(ChannelHandlerContext ctx, Object msg) throws Exception;

    public ChannelHandlerFactory getChannelHandlerFactory() {
        return channelHandlerFactory;
    }

    public void setChannelHandlerFactory(ChannelHandlerFactory channelHandlerFactory) {
        this.channelHandlerFactory = channelHandlerFactory;
    }
}
