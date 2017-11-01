package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.distributedAsura.netty.BlockingReadHandlerAdapter;
import com.panyangbo.distributedAsura.netty.ChannelHandlerFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 默认服务端handler
 * @author 潘洋波[panybest1991@gmail.com]
 * @Date 2017/6/30 13:56
 * @Description IOServerHandler
 * @version 4.0.0
 */
@ChannelHandler.Sharable
public class IOServerHandler extends BlockingReadHandlerAdapter {
    private final Log log = LogFactory.getLog(getClass());

    public IOServerHandler() {

    }

    public IOServerHandler(ChannelHandlerFactory channelHandlerFactory) {
        super(channelHandlerFactory);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.debug(String.format("New connection from %1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.debug(String.format("Connection from %1$s:%2$d closed", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
    }

    @Override
    protected void doRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        byte[] content = (byte[]) msg;
        boolean wrote = false;
        while (!wrote) {
            while (ctx.channel().isWritable()) {
                ctx.writeAndFlush(content);
                wrote = true;
                break;
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.debug("Fired user event :"+ evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error(cause.getMessage(), cause);
        ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
    }

}
