package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.distributedAsura.netty.BlockingReadHandlerAdapter;
import com.panyangbo.distributedAsura.netty.ChannelHandlerFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/30
 */

@ChannelHandler.Sharable
public class IOClientHandler extends BlockingReadHandlerAdapter {
    private final Log log = LogFactory.getLog(getClass());

    public IOClientHandler() {

    }

    public IOClientHandler(ChannelHandlerFactory channelHandlerFactory) {
        super(channelHandlerFactory);
    }

    @Override
    protected void doRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        /*
        do somthing
         */
        System.out.println(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("Fired user event :"+ evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error(cause.getMessage(), cause);
        ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
    }
}
