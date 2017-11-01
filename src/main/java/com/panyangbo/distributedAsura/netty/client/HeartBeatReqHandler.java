package com.panyangbo.distributedAsura.netty.client;

import com.panyangbo.domain.core.HeadType;
import com.panyangbo.domain.core.SocketPg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ScheduledFuture;

/**
 * 心跳响应处理器
 *
 * @author panyangbo panyangbo
 * @create 2017/7/6
 */
@ChannelHandler.Sharable
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private final Log log = LogFactory.getLog(getClass());

    private String serverid;

    private volatile ScheduledFuture<?> heartBeat;
    public HeartBeatReqHandler(String serverid) {
        this.serverid = serverid;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        SocketPg socketPg = (SocketPg) msg;
        if (socketPg.getHeader() != null && socketPg.getHeader().getType() == HeadType.LOGIN_AUTH_RESP){
            SocketPg heartBeat = new SocketPg(serverid, HeadType.HEAT_BEAT_REQ,"-1",null);
            ctx.writeAndFlush(heartBeat);
            log.debug(String.format("Heart beat to%1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
        }else if (socketPg.getHeader() != null && socketPg.getHeader().getType() == HeadType.HEAT_BEAT_RESP){
            log.debug(String.format("Heart beat from%1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
           SocketPg heartBeat = new SocketPg(serverid,HeadType.HEAT_BEAT_REQ,"-1",null);
            log.debug(String.format("Heart beat to%1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
            ctx.writeAndFlush(heartBeat);
        }
    }
}
