package com.panyangbo.distributedAsura.netty.server;

import com.panyangbo.domain.core.HeadType;
import com.panyangbo.domain.core.SocketPg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 心跳响应处理器
 *
 * @author panyangbo panyangbo
 * @create 2017/7/6
 */
@ChannelHandler.Sharable
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    private final Log log = LogFactory.getLog(getClass());

    private String serverid;

    public HeartBeatRespHandler(String serverid) {
        this.serverid = serverid;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketPg sp = (SocketPg) msg;
        if (sp.getHeader() != null && sp.getHeader().getType() == HeadType.HEAT_BEAT_REQ){
            SocketChannel channel = (SocketChannel) ctx.channel();
            log.debug(String.format("Heart beat from%1$s:%2$d", channel.remoteAddress().getAddress(), channel.remoteAddress().getPort()));
            SocketPg heartBeat = new SocketPg(serverid, HeadType.HEAT_BEAT_RESP,"-1",null);
            ctx.writeAndFlush(heartBeat);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

}
